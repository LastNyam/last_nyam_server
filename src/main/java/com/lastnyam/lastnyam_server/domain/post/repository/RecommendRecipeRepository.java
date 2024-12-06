package com.lastnyam.lastnyam_server.domain.post.repository;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.domain.RecommendRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRecipeRepository extends JpaRepository<RecommendRecipe, Long> {

    List<RecommendRecipe> findAllByFoodPost(FoodPost savedFoodPost);
}
