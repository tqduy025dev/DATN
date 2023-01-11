package com.server.datn.server.repositories;

import com.server.datn.server.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findCompanyByCode(String code);
}
