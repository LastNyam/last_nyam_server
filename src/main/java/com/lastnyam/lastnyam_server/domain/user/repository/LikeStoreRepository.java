package com.lastnyam.lastnyam_server.domain.user.repository;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import com.lastnyam.lastnyam_server.domain.user.domain.LikeStore;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeStoreRepository  extends JpaRepository<LikeStore, Long> {

    Optional<LikeStore> findByUserAndStore(User user, Store savedStore);
}
