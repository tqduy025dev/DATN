package com.server.datn.server.services;

import com.server.datn.server.common.dto.base.BaseResult;
import com.server.datn.server.common.dto.company.SeatRequest;

public interface SeatService {
    BaseResult updateSeat(SeatRequest seatRequest, String id);
}
