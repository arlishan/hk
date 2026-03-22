package com.example.leadbot.lead;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    Page<Lead> findByNameContainingIgnoreCaseOrCompanyContainingIgnoreCase(
            String name,
            String company,
            Pageable pageable
    );

    Page<Lead> findByLevel(String level, Pageable pageable);
}
