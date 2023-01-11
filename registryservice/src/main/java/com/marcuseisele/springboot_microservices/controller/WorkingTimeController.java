package com.server.datn.server.controller;

import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.manager.WorkingTimeRequest;
import com.server.datn.server.common.utils.ResponseResultUtils;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.helper.WorkingTimeHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.server.datn.server.common.constants.AppConstants.*;
import static com.server.datn.server.common.constants.MessageConstants.PARAMETER_VALIDATION_DATE;

@RestController
@RequestMapping("/api/timekeeping")
public class WorkingTimeController {
    private final WorkingTimeHelper helper;

    public WorkingTimeController(WorkingTimeHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@RequestBody WorkingTimeRequest check) {
        Response response = helper.checkIn(check);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@RequestBody WorkingTimeRequest check) {
        Response response = helper.checkOut(check);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/woking-hours")
    public ResponseEntity<?> findWorkingHours(WorkingTimeRequest check,
                                              @RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "20") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        boolean validateTime = TimeUtils.checkSameMonth(check.getFromDate(), check.getToDate(), DEFAULT_FORMAT_DATE);
        if(!validateTime){
            Response responseValidate = new Response();
            responseValidate.setResult(ResponseResultUtils.getResponseResult(PARAMETER_VALIDATION_DATE, FAIL_CODE));
            int status = responseValidate.getResult().getHttpStatus();
            return ResponseEntity.status(status).body(responseValidate);
        }

        Response response = helper.findCheckInCheckOut(check, pageable);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

}
