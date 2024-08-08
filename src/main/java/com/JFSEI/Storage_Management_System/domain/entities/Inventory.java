package com.JFSEI.Storage_Management_System.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "inventory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String reference;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private  Integer quantity;

    @OneToMany(fetch = FetchType.EAGER,
    mappedBy = "inventory",
    cascade = CascadeType.ALL)
    private List<Check_in> check_in;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "inventory",
            cascade = CascadeType.ALL)
    private List<OutputRecord> outputRecords;
}
