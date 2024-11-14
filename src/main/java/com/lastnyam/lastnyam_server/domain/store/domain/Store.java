package com.lastnyam.lastnyam_server.domain.store.domain;

import com.lastnyam.lastnyam_server.global.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Store extends AuditEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private double temperature;

    @Lob
    private byte[] image;

    // @Column(nullable = false)
    private String positionX;

    // @Column(nullable = false)
    private String positionY;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private String address;

}
