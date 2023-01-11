package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.SeatMapRequest;
import com.server.datn.server.entity.map.SeatMaps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SeatMapService {
    SeatMaps createSeatMap(SeatMapRequest seatMapRequest) throws Exception;
    BaseResult updateSeatMap(SeatMapRequest seatMapRequest, String id) throws Exception;
    String deleteSeatMap(String id);
    SeatMaps findSeatMapByfields(Map<String, String> map);
    SeatMaps findSeatMapById(String id);
    Page<?> findAllSeatMap(Pageable pageable);
}
