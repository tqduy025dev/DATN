package com.example.web.business;

import com.example.web.model.bo.ProductBO;
import com.example.web.model.entity.Category;
import com.example.web.model.entity.Idol;
import com.example.web.model.entity.Product;
import com.example.web.model.request.PagingRequest;
import com.example.web.model.response.PageResponse;
import com.example.web.model.response.ProductResponse;
import com.example.web.model.response.Response;
import com.example.web.model.response.Summary;
import com.example.web.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductHelper {

    @Autowired
    ProductRepository productRepository;

    public Response addProduct(ProductBO bo) {
        var rp = new Response();
        var product = new Product();
        if (!Objects.isNull(bo)) {
            // add product
            product.setProduct_name(bo.getProduct_name());
            product.setDescription(bo.getDescription());
            product.setLink(bo.getLink());
            product.setView_counter(0);
            product.setImage(bo.getImage());
            product.setView_counter(bo.getView_counter());
            if (bo.getCategory_id() > 0) {
                var cate = new Category();
                cate.setCategory_id(bo.getCategory_id());
                var set = new HashSet<Category>();
                set.add(cate);
                product.setFCategory(set);

                if (bo.getIdol_id() > 0) {
                    var idol = new Idol();
                    idol.setIdol_id(bo.getIdol_id());
                    product.setIdol(idol);
                } else {
                    return new Response((short) 500, "Khóa ngoại idol_id không tồn tại", "");
                }
            } else {
                return new Response((short) 500, "Khóa ngoại category_id không tồn tại", "");
            }
            productRepository.saveAndFlush(product);
            return new Response((short) 200, "Thành công", product);
        }
        return new Response((short) 500, "Thất bại", "");
    }

    public Response deleteProduct(Long id) {
        boolean exists = this.productRepository.existsById(id);
        if (exists) {
            this.productRepository.deleteById(id);
            return new Response((short) 200, "id: " + id + " Deleted", "");
        }
        return new Response((short) 404, "id: " + id + " does not exist", "");
    }

    public ProductBO getProductByNameAndId(Long id, String name) {
        Product product = productRepository.findByProduct_idAndProduct_name(id, name);
        if (Objects.nonNull(product)) {
            ProductBO productBO = new ProductBO();
            productBO.setProduct_name(product.getProduct_name());
            productBO.setLink(product.getLink());
            productBO.setImage(product.getImage());
            productBO.setDescription(product.getDescription());
            productBO.setView_counter(product.getView_counter());
            return productBO;
        }
        return null;
    }

    public PageResponse<ProductResponse> getProductByName(String nameProduct, PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(pagingRequest.getPageNumber() - 1, pagingRequest.getPageSize(), Sort.by("createddate").ascending());
        Page<Product> products = productRepository.findByProduct_name(nameProduct, pageable);
        List<ProductResponse> productResponses = mapProductToProductResponse(products.getContent());
        Summary summary = Summary.builder()
                .count(products.getNumberOfElements())
                .total(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .index(pagingRequest.getPageNumber()).build();
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setSummary(summary);
        pageResponse.setListResponse(productResponses);
        return pageResponse;
    }

    public PageResponse<ProductResponse> findAllProduct(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(pagingRequest.getPageNumber() - 1, pagingRequest.getPageSize(), Sort.by("createddate").ascending());
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = mapProductToProductResponse(products.getContent());
        Summary summary = Summary.builder()
                .count(products.getNumberOfElements())
                .total(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .index(pagingRequest.getPageNumber()).build();
        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setSummary(summary);
        pageResponse.setListResponse(productResponses);
        return pageResponse;
    }

    private List<ProductResponse> mapProductToProductResponse(List<Product> products) {
        List<ProductResponse> productResponses = new LinkedList<>();
        if (Objects.nonNull(products)) {
            for (Product product : products) {
                ProductResponse productResponse = new ProductResponse();
                BeanUtils.copyProperties(product, productResponse);
                productResponses.add(productResponse);
            }
        }
        return productResponses;
    }

}
