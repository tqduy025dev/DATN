package com.example.web.business;

import com.example.web.model.bo.CategoryBO;
import com.example.web.model.entity.Category;
import com.example.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CategoryHelper {

    @Autowired
    private CategoryRepository repository;

    public Category getCateByName(String name) {
        var result = repository.findCateByName(name);
        return result;
    }

    public long insertCategory(CategoryBO bo) {
        Category cate = new Category();
        cate.setCategory_name(bo.getCategory_name());
        var date = new Date();
        cate.setCreateddate(new Timestamp(date.getTime()));

        var result = repository.saveAndFlush(cate);
        return result != null ? result.getCategory_id() : -1;
    }

    public long deletedCategory(Long id) {
        repository.deleteById(id);
        return 1;
    }

    public long updateCategory(CategoryBO bo) {
        var date = new Date();
        var tmp = new Timestamp(date.getTime());
        var rs = repository.updateCategory(bo.getCategory_name(), tmp, bo.getCategory_id());
        return rs;
    }

    public Category getCategoryById(Long id) {
        var result = repository.findCateById(id);
        return result != null ? result : null;
    }

    public List<Category> getAllCate() {
        var result = repository.findAll();
        return result != null ? result : null;
    }
}
