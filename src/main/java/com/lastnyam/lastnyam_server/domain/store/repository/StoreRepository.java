package com.lastnyam.lastnyam_server.domain.store.repository;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
//    Optional<Store> findByBusinessNumber(String businessNumber);

    @Modifying
    @Query("UPDATE Store s SET s.temperature = s.temperature + :delta WHERE s.id = :storeId")
    int updateTemperature(@Param("storeId") Long storeId, @Param("delta") double delta);
}