package com.lastnyam.lastnyam_server.domain.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Enum 변경 등 리팩터링 필요할 듯,,,

@Getter
@NoArgsConstructor
@Entity
public class FoodCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
