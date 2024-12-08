package com.lastnyam.lastnyam_server.domain.post.repository;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.domain.PostStatus;
import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodPostRepository extends JpaRepository<FoodPost, Long> {

    List<FoodPost> findAllByStoreAndStatusNot(Store savedUser, PostStatus status);
    List<FoodPost> findAllByStatus(PostStatus status);

    @Modifying
    @Query("UPDATE FoodPost f SET f.count = f.count - :amount " +
            "WHERE f.id = :id AND f.count >= :amount AND f.status = 'AVAILABLE'")
    int decreaseStock(@Param("id") Long id, @Param("amount") int amount);
}