package com.example.web.repository;

import com.example.web.model.entity.Banner;
import com.example.web.model.entity.Idol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Override
    <S extends Banner> S saveAndFlush(S entity);

    @Override
    void deleteById(Long id);

    @Query("SELECT u FROM Banner u WHERE u.banner_id = ?1")
    Banner findBannerById(long id);

    @Transactional
    @Modifying
    @Query("update Banner u set u.image = ?1, u.link = ?2, u.status = ?3, u.createddate = ?4 where u.banner_id = ?5")
    Integer updateBanner(String name, String link, boolean status, Timestamp date, long id);
}
