package com.lastnyam.lastnyam_server.domain.reservation.service;

import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.repository.FoodPostRepository;
import com.lastnyam.lastnyam_server.domain.reservation.domain.Reservation;
import com.lastnyam.lastnyam_server.domain.reservation.domain.ReservationStatus;
import com.lastnyam.lastnyam_server.domain.reservation.dto.request.ReservationRequest;
import com.lastnyam.lastnyam_server.domain.reservation.dto.response.ReservationInfoByOwner;
import com.lastnyam.lastnyam_server.domain.reservation.dto.response.ReservationInfoByUser;
import com.lastnyam.lastnyam_server.domain.reservation.repository.ReservationRepository;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final FoodPostRepository foodPostRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void reservationFood(ReservationRequest request, Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        FoodPost savedPost = foodPostRepository.findById(request.getFoodId())
                .orElseThrow(() -> new ServiceException(ExceptionCode.FOOD_POST_NOT_FOUND));

        int updatedRows = foodPostRepository.decreaseStock(request.getFoodId(), request.getAmount());

        if (updatedRows == 0) {
            throw new ServiceException(ExceptionCode.FOOD_NOT_ENOUGH_OR_NOT_AVAILABLE);
        }

        // FoodPost를 다시 가져와줘야 하지만, 추가 사용이 없으므로 리프레시 로직 추가 X

        Reservation newReservation = Reservation.builder()
                .user(savedUser)
                .foodPost(savedPost)
                .number(request.getAmount())
                .build();

        reservationRepository.save(newReservation);
        
        // TODO. 사장용 앱에 알림
    }

    @Transactional(readOnly = true)
    public List<ReservationInfoByUser> getReservationListByUser(Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        List<Reservation> reservations = reservationRepository.findAllByUser(savedUser);

        return reservations.stream()
                .map(r -> ReservationInfoByUser.builder()
                        .reservationId(r.getId())
                        .storeId(r.getFoodPost().getStore().getId())
                        .storeName(r.getFoodPost().getStore().getName())
                        .foodId(r.getFoodPost().getId())
                        .foodImage(r.getFoodPost().getImage())
                        .status(r.getStatus())
                        .foodName(r.getFoodPost().getFoodName())
                        .number(r.getNumber())
                        .price(r.getNumber()*r.getFoodPost().getDiscountPrice())
                        .reservationDate(r.getAcceptTime())
                        .build())
                .toList();

    }

    @Transactional(readOnly = true)
    public List<ReservationInfoByOwner> getReservationListByOwner(Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        List<Reservation> reservations = reservationRepository.findAllByFoodPost_Store(savedUser.getStore());

        return reservations.stream()
                .map(r -> ReservationInfoByOwner.builder()
                        .reservationId(r.getId())
                        .userNickname(r.getUser().getNickname())
                        .userImage(r.getUser().getProfileImage())
                        .status(r.getStatus())
                        .foodName(r.getFoodPost().getFoodName())
                        .number(r.getNumber())
                        .price(r.getNumber()*r.getFoodPost().getDiscountPrice())
                        .deadLine(r.getFoodPost().getReservationTimeLimit())
                        .reservationDate(r.getAcceptTime())
                        .build())
                .toList();
    }

    @Transactional
    public void reservationAccept(Long reservationId, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Reservation savedReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.RESERVATION_NOT_FOUND));

        if (!savedReservation.getFoodPost().getStore().equals(savedUser.getStore())) {
            throw new ServiceException(ExceptionCode.UN_AUTHENTICATION);
        }

        try {
            savedReservation.setAcceptTime(LocalDateTime.now());
            savedReservation.setStatus(ReservationStatus.RESERVATION);
        } catch (OptimisticLockException | EntityNotFoundException e) {
            throw new ServiceException(ExceptionCode.CONCURRENT_UPDATE);
        }

        // TODO. 사용자에 알림
    }

    @Transactional
    public void reservationCancel(Long reservationId, String cancelMessage, Long userId) {
        log.info("reservation cancel request(Owner): userId-{}, cancelMsg-{}", userId, cancelMessage);
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Reservation savedReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.RESERVATION_NOT_FOUND));

        if (!savedReservation.getFoodPost().getStore().equals(savedUser.getStore())) {
            throw new ServiceException(ExceptionCode.UN_AUTHENTICATION);
        }

        try {
            savedReservation.setStatus(ReservationStatus.CANCEL);
            savedReservation.setCancellationReason(cancelMessage);
        } catch (OptimisticLockException | EntityNotFoundException e) {
            throw new ServiceException(ExceptionCode.CONCURRENT_UPDATE);
        }

        foodPostRepository.increaseStock(savedReservation.getFoodPost().getId(), savedReservation.getNumber());

        // TODO. 사용자에 알림
    }

    @Transactional
    public void reservationCancelByUser(Long reservationId, Long userId) {
        log.info("reservation cancel request(User): userId-{}", userId);

        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.RESERVATION_NOT_FOUND));

        if (!reservation.getUser().equals(savedUser)) {
            throw new ServiceException(ExceptionCode.UN_AUTHENTICATION);
        }

        int modifiedRow = reservationRepository.deleteByIdAndStatus(reservationId, ReservationStatus.BEFORE_ACCEPT);

        if (modifiedRow == 0) {
            throw new ServiceException(ExceptionCode.CONCURRENT_UPDATE_OWNER);
        }

        foodPostRepository.increaseStock(reservation.getFoodPost().getId(), reservation.getNumber());
    }

    @Transactional
    public void updateReservationStatus(Long reservationId, ReservationStatus status, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.RESERVATION_NOT_FOUND));

        reservation.setStatus(status);
    }
}
