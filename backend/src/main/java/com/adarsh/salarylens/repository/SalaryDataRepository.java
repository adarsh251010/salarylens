package com.adarsh.salarylens.repository;

import com.adarsh.salarylens.entity.SalaryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryDataRepository extends JpaRepository<SalaryData, Long> {
    List<SalaryData> findByLocation(String location);
    List<SalaryData> findByExperienceYearsGreaterThanEqual(Integer years);
}
