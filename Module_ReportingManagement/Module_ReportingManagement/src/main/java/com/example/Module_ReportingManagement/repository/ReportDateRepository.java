package com.example.Module_ReportingManagement.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Module_ReportingManagement.entities.ReportDate;
import com.example.Module_ReportingManagement.entities.ReportProduct;

@Repository
public interface ReportDateRepository extends JpaRepository<ReportDate, Integer> {

	Optional<ReportDate> findByTime(Date time);
}
