package com.i4.laboratory.domain.repository;

import com.i4.laboratory.domain.entities.BloodTest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BloodTestRepository extends ReactiveCrudRepository<BloodTest, Long>, CustomBloodTestRepository {
}
