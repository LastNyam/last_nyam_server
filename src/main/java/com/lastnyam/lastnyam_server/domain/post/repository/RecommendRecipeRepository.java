package com.lastnyam.lastnyam_server.domain.post.repository;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.domain.RecipeAuthor;
import com.lastnyam.lastnyam_server.domain.post.domain.RecommendRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendRecipeRepository extends JpaRepository<RecommendRecipe, Long> {

    List<RecommendRecipe> findAllByFoodPost(FoodPost savedFoodPost);

    Optional<RecommendRecipe> findByFoodPostAndAuthor(FoodPost savedFoodPost, RecipeAuthor recipeAuthor);
}
