package com.example.web.controller;

import com.example.web.business.CategoryHelper;
import com.example.web.model.bo.CategoryBO;
import com.example.web.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("apicategory")
public class CategoryController {
    @Autowired
    CategoryHelper helper;

    @PostMapping(value = "/insertcategory")
    public ResponseEntity<Long> insertCategory(@RequestBody CategoryBO category) {
        Long result = null;
        try {
            result = helper.insertCategory(category);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/updatecategory")
    public ResponseEntity<Long> updateIdol(@RequestBody CategoryBO category) {
        Long result = null;
        try {
            result = helper.updateCategory(category);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/deletecategory")
    public ResponseEntity<Long> deleteCategory(Long id) {
        Long result = null;
        try {
            result = helper.deletedCategory(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getcategorybyname")
    public ResponseEntity<Category> getCategoryByName(String name) {
        Category result = null;
        try {
            result = helper.getCateByName(name);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getcategorybyid")
    public ResponseEntity<Category> getCategoryById(Long id) {
        Category result = null;
        try {
            result = helper.getCategoryById(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping(value = "/getallcategory")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> result = new ArrayList<>();
        try {
            result = helper.getAllCate();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }
}
