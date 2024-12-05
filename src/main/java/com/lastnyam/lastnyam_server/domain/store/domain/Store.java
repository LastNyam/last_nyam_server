package com.lastnyam.lastnyam_server.domain.store.domain;

import com.lastnyam.lastnyam_server.global.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Store extends AuditEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private double temperature;

    @Setter
    @Lob
    private byte[] image;

    @Setter
    @Column(nullable = false)
    private String positionX;

    @Setter
    @Column(nullable = false)
    private String positionY;

    @Setter
    @Column(nullable = false)
    private String contactNumber;

    @Setter
    @Column(nullable = false)
    private String address;

    @Builder
    public Store(String name, String businessNumber, byte[] image, String positionX, String positionY, String contactNumber, String address) {
        this.name = name;
        this.businessNumber = businessNumber;
        this.temperature = 36.5;
        this.image = image;
        this.positionX = positionX;
        this.positionY = positionY;
        this.contactNumber = contactNumber;
        this.address = address;
    }
}
