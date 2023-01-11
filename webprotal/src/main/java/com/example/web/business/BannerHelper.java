package com.example.web.business;

import com.example.web.model.bo.BannerBO;
import com.example.web.model.entity.Banner;
import com.example.web.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class BannerHelper {
    @Autowired
    BannerRepository repository;

    public long insertBanner(BannerBO bo) {
        Banner banner = new Banner();
        banner.setImage(bo.getImage());
        banner.setLink(bo.getLink());
        banner.setStatus(bo.getStatus());
        var date = new Date();
        banner.setCreateddate(new Timestamp(date.getTime()));

        var result = repository.saveAndFlush(banner);
        return result != null ? result.getBanner_id() : -1;
    }

    public long deletedBanner(Long id) {
        repository.deleteById(id);
        return 1;
    }

    public long updateBanner(BannerBO bo) {
        var date = new Date();
        var tmp = new Timestamp(date.getTime());
        var rs = repository.updateBanner(bo.getImage(), bo.getLink(), bo.getStatus(), tmp, bo.getBanner_id());
        return rs;
    }

    public Banner getBannerById(Long id) {
        var result = repository.findBannerById(id);
        return result != null ? result : null;
    }

    public List<Banner> getAllBanner() {
        var result = repository.findAll();
        return result != null ? result : null;
    }
}
