package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.CompanyRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.entity.company.Company;
import com.server.datn.server.entity.files.SystemFile;
import com.server.datn.server.repositories.CompanyRepository;
import com.server.datn.server.services.CloudService;
import com.server.datn.server.services.CompanyService;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.server.datn.server.common.constants.AppConstants.STATUS_U0;
import static com.server.datn.server.common.constants.AppConstants.STATUS_U2;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CloudService cloudService;

    public CompanyServiceImpl(CompanyRepository companyRepository, CloudService cloudService) {
        this.companyRepository = companyRepository;
        this.cloudService = cloudService;
    }

    @Override
    public Company createCompany(CompanyRequest companyRequest) throws IOException {
        Company company = (Company) AppUtils.converToEntities(companyRequest, Company.class);
        String url = cloudService.uploadToCloud(companyRequest.getLogo());
        SystemFile logo = new SystemFile();
        logo.setType(companyRequest.getLogo().getContentType());
        logo.setData(url);
        logo.setCreateBy(AppUtils.getCurrentUserId());
        company.setLogo(logo);
        return companyRepository.save(company);
    }

    @Override
    public BaseResult updateCompany(CompanyRequest companyRequest, String id) {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = companyRepository.existsById(id);
        if(exists){
            Company companyOld = companyRepository.getReferenceById(id);
            final String code = ResponseResultUtils.getCompanyCode();
            AppUtils.copyNonNullProperties(companyRequest, companyOld);
            companyOld.setCode(code);
            Company company = companyRepository.save(companyOld);
            result.setResult(company);
            status = STATUS_U0;
        }
        result.setStatus(status);
        return result;
    }

    @Override
    public Company findCompanyByCode(String code) {
        return companyRepository.findCompanyByCode(code);
    }
}
