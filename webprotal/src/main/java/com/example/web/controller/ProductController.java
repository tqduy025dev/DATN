package com.example.web.controller;

import com.example.web.business.ProductHelper;
import com.example.web.model.bo.ProductBO;
import com.example.web.model.request.PagingRequest;
import com.example.web.model.response.PageResponse;
import com.example.web.model.response.ProductResponse;
import com.example.web.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("new")
public class ProductController {

    @Autowired
    ProductHelper productHelper;

    @PostMapping(value = "/insertproduct")
    public ResponseEntity<Response> insertProduct(@RequestBody ProductBO product) {
        Response result;
        try {
            result = productHelper.addProduct(product);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{name}/{id}")
    public ResponseEntity<?> getProductByNameAndId(
            @PathVariable Long id,
            @PathVariable String name) {
        ProductBO result;
        try {
            result = productHelper.getProductByNameAndId(id, name);
            if(result == null)
                return ResponseEntity.status(404).body("Not Found");
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/deleteproduct")
    public ResponseEntity<Response> deleteProduct(@RequestParam(name = "id") Long id) {
        Response result;
        try {
            result = productHelper.deleteProduct(id);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/{name}")
    public ResponseEntity<PageResponse<ProductResponse>> getProductByName(
            @RequestBody PagingRequest pagingRequest,
            @PathVariable String name) {
        PageResponse<ProductResponse> result;
        try {
            result = productHelper.getProductByName(name, pagingRequest);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/{page}")
    public ResponseEntity<PageResponse<ProductResponse>> findAllProduct(
            @PathVariable Short page) {
        PageResponse<ProductResponse> result;
        PagingRequest pagingRequest =PagingRequest.builder().pageNumber(page).build();
        try {
            result = productHelper.findAllProduct(pagingRequest);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(result);
    }
}
