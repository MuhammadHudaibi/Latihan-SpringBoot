package com.enigmacamp.latihanspring.entity;

import com.enigmacamp.latihanspring.model.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama tidak boleh kosong")
    @Column(name = "names", nullable = false)
    private String name;

    @Column(name = "descriptions")
    private String description;

    @NotNull(message = "Harga tidak boleh kosong")
    @Column(name = "prices", nullable = false)
    private BigDecimal price;

    @NotNull(message = "Kuantiti tidak boleh kosong")
    @Column(name = "quantities", nullable = false)
    private Integer quantity;

    @NotNull(message = "Status diperlukan")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
