package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.CompanyRequest;
import com.server.datn.server.entity.company.Company;

import java.io.IOException;

public interface CompanyService {
    Company createCompany(CompanyRequest companyRequest) throws IOException;
    BaseResult updateCompany(CompanyRequest companyRequest, String id);
    Company findCompanyByCode(String code);
}
