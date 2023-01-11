package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.OfficeRequest;
import com.server.datn.server.entity.company.Office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OfficeService {
    Office createOffice(OfficeRequest officeRequest);
    Page<Office> findOfficeByFields(Map<String, String> map, Pageable pageable);
    Office findOfficeById(String id);
    BaseResult updateOffice(OfficeRequest officeRequest, String id);
    String deleteOffice(String id);
    void updateOffice(Office... office);
}
