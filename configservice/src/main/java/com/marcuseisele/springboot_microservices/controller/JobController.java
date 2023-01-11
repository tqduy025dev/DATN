package com.server.datn.server.controller;

import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.job.JobRequest;
import com.server.datn.server.helper.JobLevelHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {
    private final JobLevelHelper helper;

    public JobController(JobLevelHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/fixed-level")
    public ResponseEntity<?> createLevel(@RequestBody JobRequest jobRequest){
        Response response = helper.createJobLevel(jobRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-level/{id}")
    public ResponseEntity<?> updateLevel(@RequestBody JobRequest jobRequest,@PathVariable String id){
        Response response = helper.updateJobLevel(jobRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-level/{id}")
    public ResponseEntity<?> deleteLevel(@PathVariable String id){
        Response response = helper.deleteLevel(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping( "/educed-level")
    public ResponseEntity<?> findJobLevel(@RequestParam Map<String, String> map,
                                          @RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "20") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Response response = helper.findJobLevel(map, pageable);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping( "/educed-level/{id}")
    public ResponseEntity<?> findJobLevel(@PathVariable String id){
        Response response = helper.findJobLevelById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }


    @PostMapping("/fixed-title")
    public ResponseEntity<?> createJobTitle(@RequestBody JobRequest jobRequest){
        Response response = helper.createJobTitle(jobRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/fixed-title/{id}")
    public ResponseEntity<?> updateJobTitle(@RequestBody JobRequest jobRequest,@PathVariable String id){
        Response response = helper.updateJobTitle(jobRequest, id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-title/{id}")
    public ResponseEntity<?> deleteJobTitle(@PathVariable String id){
        Response response = helper.deleteJobTitle(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping( "/educed-title")
    public ResponseEntity<?> findJobTitle(@RequestParam Map<String, String> map,
                                            @RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "20") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Response response = helper.findJobTitle(map, pageable);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping( "/educed-title/{id}")
    public ResponseEntity<?> findJobTitle(@PathVariable String id){
        Response response = helper.findJobTitleById(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }



}
