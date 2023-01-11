package com.server.datn.server.entity.map;

import com.server.datn.server.common.constants.AppConstants;
import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.company.Office;
import com.server.datn.server.entity.files.SystemFile;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class SeatMaps {
    @Id
    private String seatMapsId;
    private String name;
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemFile image;
    private Timestamp createdTime;
    @UpdateTimestamp
    private Timestamp lastUpdated;
    private String createBy;
    private String updateBy;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_maps_id")
    private List<Seat> seats;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId
    private Office office;

    public SeatMaps() {
        this.seatMapsId = KeyGenarator.getKey();
        this.createdTime = TimeUtils.getTimestampNow();
        this.status = AppConstants.STATUS_ACTIVE;
    }

    public String getSeatMapsId() {
        return seatMapsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SystemFile getImage() {
        return image;
    }

    public void setSeatMapsId(String seatMapsId) {
        this.seatMapsId = seatMapsId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setImage(SystemFile image) {
        this.image = image;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
