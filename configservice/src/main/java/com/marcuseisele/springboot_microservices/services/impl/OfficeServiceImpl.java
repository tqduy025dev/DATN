package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.OfficeRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.company.Company;
import com.server.datn.server.entity.company.Office;
import com.server.datn.server.repositories.OfficeRepository;
import com.server.datn.server.services.CompanyService;
import com.server.datn.server.services.OfficeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepository officeRepository;
    private final CompanyService companyService;


    public OfficeServiceImpl(OfficeRepository officeRepository, CompanyService companyService) {
        this.officeRepository = officeRepository;
        this.companyService = companyService;
    }

    @Override
    public Office createOffice(OfficeRequest officeRequest) {
        String companyCode = ResponseResultUtils.getCompanyCode();
        Company company = companyService.findCompanyByCode(companyCode);
        Office office = (Office) AppUtils.converToEntities(officeRequest, Office.class);
        office.setCompany(company);
        office.setCreateBy(AppUtils.getCurrentUserId());
        return officeRepository.save(office);
    }

    @Override
    public Page<Office> findOfficeByFields(Map<String, String> map, Pageable pageable) {
        String officeName = map.get(FIELD_OFFICE_NAME);
        if(Objects.nonNull(officeName)){
            officeName = AppUtils.removeAccent(officeName);
            return officeRepository.findOfficesByOfficeNameContains(officeName, pageable);
        }
        return officeRepository.findAll(pageable);
    }

    @Override
    public Office findOfficeById(String id) {
        return officeRepository.findById(id).orElse(null);
    }

    @Override
    public BaseResult updateOffice(OfficeRequest officeRequest, String id) {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = officeRepository.existsById(id);
        if(exists){
            Office officeOld = officeRepository.getReferenceById(id);
            final String code = officeOld.getCode();
            AppUtils.copyNonNullProperties(officeRequest, officeOld);

            officeOld.setCode(code);
            officeOld.setLastUpdated(TimeUtils.getTimestampNow());
            Office office = officeRepository.save(officeOld);
            result.setResult(office);
            status = STATUS_U0;
        }
        result.setStatus(status);
        return result;
    }

    @Override
    public String deleteOffice(String id) {
        String status = STATUS_D2;
        boolean exists = officeRepository.existsById(id);
        if (exists){
            Office office = officeRepository.getReferenceById(id);
            if(STATUS_DELETE.equals(office.getStatus())){
                status = STATUS_D1;
            }else {
                office.setStatus(STATUS_DELETE);
                officeRepository.save(office);
                status = STATUS_D0;
            }
        }
        return status;
    }

    @Override
    public void updateOffice(Office... offices) {
        List<Office> officeList = Arrays.stream(offices).collect(Collectors.toList());
        officeRepository.saveAll(officeList);
    }
}
