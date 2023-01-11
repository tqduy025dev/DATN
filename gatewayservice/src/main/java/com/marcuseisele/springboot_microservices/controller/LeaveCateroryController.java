package com.server.datn.server.controller;


import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.company.TakeLeaveAction;
import com.server.datn.server.common.dto.company.TakeLeaveRequest;
import com.server.datn.server.entity.manager.LeaveCategory;
import com.server.datn.server.helper.LeaveCategoryHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/api/leave-category", "/api/take-leave"})
public class LeaveCateroryController {
    private final LeaveCategoryHelper helper;

    public LeaveCateroryController(LeaveCategoryHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/fixed-leave")
    public ResponseEntity<?> createLeaveCaterory(@RequestBody LeaveCategory leaveCategory) {
        Response response = helper.createLeaveCategory(leaveCategory);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-leave/{id}")
    public ResponseEntity<?> findLeaveCateroryById(@PathVariable String id) {
        Response response = helper.findLeaveCategoryById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-leave")
    public ResponseEntity<?> findAllLeaveCaterory() {
        Response response = helper.findAllLeaveCategory();
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


    @PostMapping("/fixed-take-leave")
    public ResponseEntity<?> createTakeLeave(@RequestBody TakeLeaveRequest takeLeaveRequest) {
        Response response = helper.createTakeLeave(takeLeaveRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-take-leave")
    public ResponseEntity<?> findTakeLeaveByReport(@RequestParam Map<String, String> map,
                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "20") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Response response = helper.findTakeLeave(map , pageable);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


    @PutMapping("/fixed-take-leave/{id}")
    public ResponseEntity<?> updateTakeLeaveById(@PathVariable String id, @RequestBody TakeLeaveAction action) {
        Response response = helper.updateTakeLeaveById(id, action.getAction());
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }
}
