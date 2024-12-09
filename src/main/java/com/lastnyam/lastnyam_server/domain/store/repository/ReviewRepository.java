package com.lastnyam.lastnyam_server.domain.store.repository;

import com.lastnyam.lastnyam_server.domain.store.domain.Review;
import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByStore(Store store);
}
