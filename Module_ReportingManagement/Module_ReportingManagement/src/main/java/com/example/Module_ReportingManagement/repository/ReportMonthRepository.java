package com.example.Module_ReportingManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Module_ReportingManagement.entities.ReportMonth;

@Repository
public interface ReportMonthRepository extends JpaRepository<ReportMonth, Integer>  {

	Optional<ReportMonth> findByMonth(int month);
}
