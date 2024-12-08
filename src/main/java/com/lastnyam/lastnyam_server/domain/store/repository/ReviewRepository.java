package com.lastnyam.lastnyam_server.domain.store.repository;

import com.lastnyam.lastnyam_server.domain.store.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
