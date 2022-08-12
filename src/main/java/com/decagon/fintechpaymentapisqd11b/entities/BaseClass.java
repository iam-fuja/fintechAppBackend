package com.decagon.fintechpaymentapisqd11b.entities;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor

public class BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

