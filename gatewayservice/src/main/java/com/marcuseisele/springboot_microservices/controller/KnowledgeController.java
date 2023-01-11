package com.server.datn.server.controller;


import com.server.datn.server.common.dto.base.Response;
import com.server.datn.server.common.dto.company.CompanyRequest;
import com.server.datn.server.common.dto.employee.KnowledgeRequest;
import com.server.datn.server.helper.KnowledgeHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {
    private final KnowledgeHelper helper;

    public KnowledgeController(KnowledgeHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/fixed-category")
    public ResponseEntity<?> createCategory(@ModelAttribute KnowledgeRequest knowledgeRequest) {
        Response response = helper.createKnowledgeCategory(knowledgeRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-category")
    public ResponseEntity<?> findCategory(@RequestParam Map<String, String> map) {
        Response response = helper.findKnowledgeCategory(map);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/fixed-knowledge")
    public ResponseEntity<?> createKnowledge(@ModelAttribute KnowledgeRequest knowledgeRequest) {
        Response response = helper.createKnowledge(knowledgeRequest);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-knowledge")
    public ResponseEntity<?> findKnowledge(@RequestParam Map<String, String> map,
                                          @RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "20") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Response response = helper.findKnowledge(map, pageable);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/educed-knowledge/{id}")
    public ResponseEntity<?> findKnowledgeById(@PathVariable String id) {
        Response response = helper.findKnowledge(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-knowledge/{id}")
    public ResponseEntity<?> deleteKnowledgeById(@PathVariable String id) {
        Response response = helper.deleteKnowledge(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/fixed-category/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable String id) {
        Response response = helper.deleteCategory(id);
        int status = response.getResult().getHttpStatus();
        return ResponseEntity.status(status).body(response);
    }
}
