package com.JFSEI.Storage_Management_System.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "outputRecord")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutputRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime outputDate;

    @Column(nullable = false)
    private  Integer outputQuantity;

    @Column(nullable = false)
    private String delivered;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventoryOutput_id",referencedColumnName = "id")
    private Inventory inventory;
}
