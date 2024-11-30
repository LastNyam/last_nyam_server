package com.lastnyam.lastnyam_server.domain.store.repository;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByBusinessNumber(String businessNumber);
}