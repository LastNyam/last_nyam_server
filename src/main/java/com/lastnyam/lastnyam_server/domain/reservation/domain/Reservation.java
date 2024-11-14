package com.lastnyam.lastnyam_server.domain.reservation.domain;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private FoodPost foodPost;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private String status;
}
