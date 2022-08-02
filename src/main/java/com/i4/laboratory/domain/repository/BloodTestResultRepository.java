package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.BloodTestResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BloodTestResultRepository extends ReactiveCrudRepository<BloodTestResult, Long>, CustomBloodTestResultRepository {
}
