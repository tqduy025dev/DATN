package com.server.datn.server.services.impl;

import com.server.datn.server.common.constants.QueryConstants;
import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.SeatMapRequest;
import com.server.datn.server.common.dto.company.SeatRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.company.Office;
import com.server.datn.server.entity.files.SystemFile;
import com.server.datn.server.entity.map.Seat;
import com.server.datn.server.entity.map.SeatMaps;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.repositories.SeatMapRepository;
import com.server.datn.server.services.EmployeeService;
import com.server.datn.server.services.FileService;
import com.server.datn.server.services.OfficeService;
import com.server.datn.server.services.SeatMapService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class SeatMapServiceImpl implements SeatMapService {
    private final SeatMapRepository seatMapRepository;
    private final OfficeService officeService;
    private final FileService fileService;
    private final EmployeeService employeeService;
    private final EntityManager entityManager;


    public SeatMapServiceImpl(SeatMapRepository seatMapRepository, OfficeService officeService, FileService fileService, EmployeeService employeeService, EntityManager entityManager) {
        this.seatMapRepository = seatMapRepository;
        this.officeService = officeService;
        this.fileService = fileService;
        this.employeeService = employeeService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public SeatMaps createSeatMap(SeatMapRequest seatMapRequest) throws Exception {
        SystemFile systemFile = fileService.findFileById(seatMapRequest.getImageId());
        Office office = officeService.findOfficeById(seatMapRequest.getOfficeId());
        SeatMaps seatMaps = (SeatMaps) AppUtils.converToEntities(seatMapRequest, SeatMaps.class);

        List<Seat> seats = getSeats(seatMapRequest);
        seatMaps.setSeats(seats);
        seatMaps.setImage(systemFile);
        seatMaps.setOffice(office);
        seatMaps.setCreateBy(AppUtils.getCurrentUserId());
        if(Objects.nonNull(office)){
            office.setHaveSeatMap(true);
            office.setLastUpdated(TimeUtils.getTimestampNow());
            officeService.updateOffice(office);
        }
        return seatMapRepository.save(seatMaps);
    }

    @Override
    public BaseResult updateSeatMap(SeatMapRequest seatMapRequest, String id) throws Exception {
        BaseResult result = new BaseResult();
        String status = STATUS_U2;
        boolean exists = seatMapRepository.existsById(id);
        if (exists){
            SeatMaps seatMapsOld = seatMapRepository.getReferenceById(id);
            AppUtils.copyNonNullProperties(seatMapRequest, seatMapsOld);
            if(Objects.nonNull(seatMapRequest.getImageId()) &&
                    !seatMapRequest.getImageId().equals(seatMapsOld.getImage().getFileId())){
                SystemFile systemFile = fileService.findFileById(seatMapRequest.getImageId());
                seatMapsOld.setImage(systemFile);
            }
            List<Seat> seats = getSeats(seatMapRequest);
            seatMapsOld.setSeats(seats);
            SeatMaps seatMaps = seatMapRepository.save(seatMapsOld);

            result.setResult(seatMaps);
            status = STATUS_U0;
        }
        result.setStatus(status);
        return result;
    }

    @Override
    public String deleteSeatMap(String id) {
        String status = STATUS_D2;
        boolean exists = seatMapRepository.existsById(id);
        if (exists){
            SeatMaps seatMaps = seatMapRepository.getReferenceById(id);
            if(STATUS_DELETE.equals(seatMaps.getStatus())){
                status = STATUS_D1;
            }else {
                seatMaps.setStatus(STATUS_DELETE);
                seatMapRepository.save(seatMaps);
                status = STATUS_D0;
            }
        }
        return status;
    }


    @Override
    public SeatMaps findSeatMapByfields(Map<String, String> map) {
        String officeId = map.get(FIELD_OFFICE_ID);
        if(StringUtils.hasLength(officeId)){
            return seatMapRepository.findSeatMapsByOfficeOfficeId(officeId);
        }
        String employeeId = map.get(FIELD_EMPLOYEE_ID);
        if(StringUtils.hasLength(employeeId)){
            return seatMapRepository.findSeatMapsByEmployeeId(employeeId);
        }
        return null;
    }

    @Override
    public SeatMaps findSeatMapById(String id) {
        return seatMapRepository.findById(id).orElse(null);
    }

    @Override
    public Page<?> findAllSeatMap(Pageable pageable) {
        return seatMapRepository.findAll(pageable);
    }

    private List<Seat> getSeats(SeatMapRequest seatMapRequest) {
        List<Seat> seats = new ArrayList<>();
        if(!CollectionUtils.isEmpty(seatMapRequest.getSeats())){
            for(SeatRequest seatRequest : seatMapRequest.getSeats()){
                Seat seat = new Seat();
                seat.setStyles(seatRequest.getStyles());
                if(Objects.nonNull(seatRequest.getEmployeeId())){
                    Employee employee = employeeService.findEmployeeById(seatRequest.getEmployeeId());
                    if(Objects.nonNull(employee)){
                        seat.setEmployee(employee);
                    }
                }
                seats.add(seat);
            }
        }
        return seats;
    }
}
