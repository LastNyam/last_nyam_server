package com.lastnyam.lastnyam_server.domain.user.domain;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class LastLook {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "last_look_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FoodPost foodPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;
}
