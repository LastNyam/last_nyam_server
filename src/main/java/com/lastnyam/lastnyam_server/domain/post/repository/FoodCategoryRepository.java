package com.lastnyam.lastnyam_server.domain.post.repository;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    Optional<FoodCategory> findByName(String name);
}
