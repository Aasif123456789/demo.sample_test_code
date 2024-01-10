package com.williams_sample_test_code.data_access;

import com.williams_sample_test_code.model_entity.CompaniesRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRecordRepository extends JpaRepository<CompaniesRecords, Long> {
}
