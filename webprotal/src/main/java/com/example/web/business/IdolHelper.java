package com.example.web.business;

import com.example.web.model.bo.IdolBO;
import com.example.web.model.entity.Idol;
import com.example.web.repository.IdolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class IdolHelper {

    @Autowired
    private IdolRepository repository;

    public Idol getIdolByName(String name) {
        var result = repository.findIdolByName(name);
        return result != null ? result : null;
    }

    public long insertIdol(IdolBO bo) {
        Idol idol = new Idol();
        idol.setIdol_name(bo.getIdol_name());
        var date = new Date();
        idol.setCreateddate( new Timestamp(date.getTime()));

        var result = repository.saveAndFlush(idol);
        return result != null ? result.getIdol_id() : -1;
    }

    public long deletedIdol(Long id){
        repository.deleteById(id);
        return 1;
    }

    public long updateIdol(IdolBO bo){
        var date = new Date();
        var tmp = new Timestamp(date.getTime());
        var rs = repository.updateIdol(bo.getIdol_name(),tmp,bo.getIdol_id());
        return rs ;
    }

    public Idol getIdolById(Long id) {
        var result = repository.findIdolById(id);
        return result != null ? result : null;
    }

    public List<Idol> getAllIdol() {
        var result = repository.findAll();
        return result != null ? result : null;
    }
}
