package com.server.datn.server.controller;


import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.company.CompanyRequest;
import com.server.datn.server.common.dto.company.OfficeRequest;
import com.server.datn.server.common.dto.company.SeatMapRequest;
import com.server.datn.server.common.dto.company.SeatRequest;
import com.server.datn.server.helper.CompanyHelper;
import com.server.datn.server.helper.OfficeHelper;
import com.server.datn.server.helper.SeatMapHepler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyHelper companyHelper;
    private final OfficeHelper officeHelper;
    private final SeatMapHepler seatMapHepler;

    public CompanyController(CompanyHelper companyHelper, OfficeHelper officeHelper, SeatMapHepler seatMapHepler) {
        this.companyHelper = companyHelper;
        this.officeHelper = officeHelper;
        this.seatMapHepler = seatMapHepler;
    }

    @PostMapping("/fixed-company")
    public ResponseEntity<?> createCompany(@ModelAttribute CompanyRequest companyRequest) {
        Response response = companyHelper.createCompany(companyRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-company/{id}")
    public ResponseEntity<?> updateCompany(@ModelAttribute CompanyRequest companyRequest, @PathVariable String id) {
        Response response = companyHelper.updateCompany(companyRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-company")
    public ResponseEntity<?> findCompany(@RequestParam Map<String, String> map) {
        Response response = companyHelper.findCompany(map);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/fixed-office")
    public ResponseEntity<?> createOffice(@RequestBody OfficeRequest officeRequest) {
        Response response = officeHelper.createOffice(officeRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-office/{id}")
    public ResponseEntity<?> updateOffice(@RequestBody OfficeRequest officeRequest,@PathVariable String id) {
        Response response = officeHelper.updateOffice(officeRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-office/{id}")
    public ResponseEntity<?> deleteOffice(@PathVariable String id) {
        Response response = officeHelper.deleteOffice(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


    @GetMapping("/educed-office")
    public ResponseEntity<?> findOffice(@RequestParam Map<String, String> map,
                                        @RequestParam(defaultValue = "true") boolean isPaging,
                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "20") Integer pageSize) {
        Pageable pageable = isPaging ? PageRequest.of(pageNo, pageSize) : Pageable.unpaged();

        Response response = officeHelper.findOffice(map, pageable, isPaging);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-office/{id}")
    public ResponseEntity<?> findOfficeById(@PathVariable String id) {
        Response response = officeHelper.findOfficeById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/fixed-seatmap")
    public ResponseEntity<?> createSeatMap(@RequestBody SeatMapRequest seatMapRequest) {
        Response response = seatMapHepler.createSeatMap(seatMapRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-seatmap/{id}")
    public ResponseEntity<?> createSeatMap(@RequestBody SeatMapRequest seatMapRequest,@PathVariable String id) {
        Response response = seatMapHepler.updateSeatMap(seatMapRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-seatmap/{id}")
    public ResponseEntity<?> deleteSeatMap(@PathVariable String id) {
        Response response = seatMapHepler.deleteSeatMap(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


    @GetMapping("/educed-seatmap")
    public ResponseEntity<?> findSeatMap(@RequestParam Map<String, String> map,
                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "20") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Response response = seatMapHepler.findSeatMap(map, pageable);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-seatmap/{id}")
    public ResponseEntity<?> findSeatMapById(@PathVariable String id) {
        Response response = seatMapHepler.findSeatMapById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-seat/{id}")
    public ResponseEntity<?> updateSeat(@RequestBody SeatRequest seatRequest, @PathVariable String id) {
        Response response = seatMapHepler.updateSeat(seatRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/fixed-attachment")
    public ResponseEntity<?> createFile(@ModelAttribute MultipartFile file) {
        Response response = seatMapHepler.createFile(file);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }
}
