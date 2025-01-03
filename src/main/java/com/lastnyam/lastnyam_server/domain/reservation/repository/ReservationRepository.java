package com.lastnyam.lastnyam_server.domain.reservation.repository;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.reservation.domain.Reservation;
import com.lastnyam.lastnyam_server.domain.reservation.domain.ReservationStatus;
import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.user = :user AND r.status = RECEIVED")
    int countByUserAndReceivedStatus(@Param("user") User user);

    List<Reservation> findAllByUser(User savedUser);

    List<Reservation> findAllByFoodPostAndStatus(FoodPost savedFoodPost, ReservationStatus reservationStatus);

    List<Reservation> findAllByFoodPost_Store(Store store);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.id = :reservationId AND r.status = :status")
    int deleteByIdAndStatus(@Param("reservationId") Long reservationId, @Param("status") ReservationStatus status);
}
