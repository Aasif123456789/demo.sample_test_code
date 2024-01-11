package com.williams_sample_test_code.repository;

import com.williams_sample_test_code.model.CompaniesRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRecordRepository extends JpaRepository<CompaniesRecords, Long> {
}
