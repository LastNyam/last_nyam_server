package com.lastnyam.lastnyam_server.domain.post.repository;

import com.lastnyam.lastnyam_server.domain.post.domain.RecommendRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRecipeRepository extends JpaRepository<RecommendRecipe, Long> {
}
