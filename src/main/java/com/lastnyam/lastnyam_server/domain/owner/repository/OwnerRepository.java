package com.lastnyam.lastnyam_server.domain.owner.repository;

import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByPhoneNumber(String phoneNumber);
}