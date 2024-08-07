package com.JFSEI.Storage_Management_System.domain.repositories;

import com.JFSEI.Storage_Management_System.domain.entities.OutputRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputRecordRepository extends JpaRepository<OutputRecord,Long> {
}
