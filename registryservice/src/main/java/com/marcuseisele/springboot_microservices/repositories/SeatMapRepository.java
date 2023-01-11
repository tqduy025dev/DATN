package com.server.datn.server.repositories;

import com.server.datn.server.entity.map.SeatMaps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatMapRepository extends JpaRepository<SeatMaps, String> {
    SeatMaps findSeatMapsByOfficeOfficeId(String id);

    @Query(value = "select sm.* FROM seat_maps sm INNER JOIN seat s ON " +
            "sm.office_office_id = s.seat_maps_id WHERE s.employee_employee_id = ?1",
            nativeQuery = true)
    SeatMaps findSeatMapsByEmployeeId(String id);

}
