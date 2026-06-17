package com.adarsh.salarylens.repository;

import com.adarsh.salarylens.entity.SalaryData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaryDataRepository extends JpaRepository<SalaryData, Long> {

    List<SalaryData> findByUsername(String username);

    Page<SalaryData> findByUsername(String username, Pageable pageable);

    Optional<SalaryData> findByIdAndUsername(Long id, String username);

    List<SalaryData> findByUsernameAndLocation(String username, String location);

    List<SalaryData> findByUsernameAndExperienceYearsGreaterThanEqual(String username, Integer years);
}