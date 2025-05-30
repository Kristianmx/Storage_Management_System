package com.JFSEI.Storage_Management_System.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "check_in")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Check_in {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime entryDate;

    @Column(nullable = false)
    private  Integer incomingQuantity;

    @Column(nullable = false)
    private String delivery;

    @Column(nullable = false)
    private Boolean status;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkInventory_id",referencedColumnName = "id")
    private Inventory inventory;
}
