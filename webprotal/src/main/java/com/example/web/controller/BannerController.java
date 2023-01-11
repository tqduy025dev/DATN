package com.example.web.controller;

import com.example.web.business.BannerHelper;
import com.example.web.model.bo.BannerBO;
import com.example.web.model.bo.IdolBO;
import com.example.web.model.entity.Banner;
import com.example.web.model.entity.Idol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("apibanner")
public class BannerController {
    @Autowired
    BannerHelper helper;

    @PostMapping(value = "/insertbanner")
    public ResponseEntity<Long> insertBanner(@RequestBody BannerBO banner) {
        Long result = null;
        try {
            result = helper.insertBanner(banner);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/updatebanner")
    public ResponseEntity<Long> updateBanner(@RequestBody BannerBO banner) {
        Long result = null;
        try {
            result = helper.updateBanner(banner);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/deletebanner")
    public ResponseEntity<Long> deleteBanner(Long id) {
        Long result = null;
        try {
            result = helper.deletedBanner(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getbannerbyid")
    public ResponseEntity<Banner> getBannerById(Long id) {
        Banner result = null;
        try {
            result = helper.getBannerById(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getallbanner")
    public ResponseEntity<List<Banner>> getAllBanner() {
        List<Banner> result = new ArrayList<>();
        try {
            result = helper.getAllBanner();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
}
