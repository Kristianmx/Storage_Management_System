package com.JFSEI.Storage_Management_System.domain.repositories;

import com.JFSEI.Storage_Management_System.domain.entities.Check_in;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Check_inRepository extends JpaRepository<Check_in,Long> {
}
