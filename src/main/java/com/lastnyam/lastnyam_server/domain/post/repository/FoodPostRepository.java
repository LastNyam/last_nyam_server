package com.lastnyam.lastnyam_server.domain.post.repository;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.domain.PostStatus;
import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodPostRepository extends JpaRepository<FoodPost, Long> {

    List<FoodPost> findAllByStore(Store savedUser);
    List<FoodPost> findAllByStatus(PostStatus status);
}