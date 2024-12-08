package com.lastnyam.lastnyam_server.domain.reservation.service;

import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.repository.FoodPostRepository;
import com.lastnyam.lastnyam_server.domain.reservation.domain.Reservation;
import com.lastnyam.lastnyam_server.domain.reservation.dto.request.ReservationRequest;
import com.lastnyam.lastnyam_server.domain.reservation.dto.response.ReservationInfo;
import com.lastnyam.lastnyam_server.domain.reservation.repository.ReservationRepository;
import com.lastnyam.lastnyam_server.domain.store.repository.StoreRepository;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<ReservationInfo> getReservationList(Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        List<Reservation> reservations = reservationRepository.findAllByFoodPost_Store(savedUser.getStore());

        return reservations.stream()
                .map(r -> ReservationInfo.builder()
                        .reservationId(r.getId())
                        .userNickname(r.getUser().getNickname())
                        .userImage(r.getUser().getProfileImage())
                        .status(r.getStatus())
                        .foodName(r.getFoodPost().getFoodName())
                        .number(r.getNumber())
                        .price(r.getNumber()*r.getFoodPost().getDiscountPrice())
                        .reservationDate(r.getAcceptTime())
                        .build())
                .toList();
    }
}
