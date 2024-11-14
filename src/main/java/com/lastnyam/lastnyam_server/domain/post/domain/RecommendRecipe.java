package com.lastnyam.lastnyam_server.domain.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RecommendRecipe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private FoodPost foodPost;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String recipe;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecipeAuthor author;

    @Lob
    private byte[] image;
}
