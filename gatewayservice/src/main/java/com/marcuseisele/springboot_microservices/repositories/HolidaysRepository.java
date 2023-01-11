package com.server.datn.server.repositories;

import com.server.datn.server.entity.manager.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidaysRepository extends JpaRepository<Holidays, String> {
}
