package com.example.web.controller;

import com.example.web.business.IdolHelper;
import com.example.web.model.bo.IdolBO;
import com.example.web.model.entity.Idol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("apidol")
public class IdolController {
    @Autowired
    IdolHelper helper;

    @PostMapping(value = "/insertidol")
    public ResponseEntity<Long> insertIdol(@RequestBody IdolBO idol) {
        Long result = null;
        try {
            result = helper.insertIdol(idol);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/updateidol")
    public ResponseEntity<Long> updateIdol(@RequestBody IdolBO idol) {
        Long result = null;
        try {
            result = helper.updateIdol(idol);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/deleteidol")
    public ResponseEntity<Long> deleteIdol(Long id) {
        Long result = null;
        try {
            result = helper.deletedIdol(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getidolbyname")
    public ResponseEntity<Idol> getIdolByName(String name) {
        Idol result = null;
        try {
            result = helper.getIdolByName(name);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getidolbyid")
    public ResponseEntity<Idol> getIdolById(Long id) {
        Idol result = null;
        try {
            result = helper.getIdolById(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getallidol")
    public ResponseEntity<List<Idol>> getAllIdol() {
        List<Idol> result = new ArrayList<>();
        try {
            result = helper.getAllIdol();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
}
