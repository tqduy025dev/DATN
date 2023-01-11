package com.server.datn.server.repositories;

import com.server.datn.server.entity.map.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,String> {
}
