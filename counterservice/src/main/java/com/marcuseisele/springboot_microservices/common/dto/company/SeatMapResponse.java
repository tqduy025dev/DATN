package com.server.datn.server.common.dto.company;

import com.server.datn.server.common.dto.base.FileResponse;

import java.util.List;

public class SeatMapResponse {
    private String seatMapsId;
    private String name;
    private String status;
    private String createdTime;
    private String lastUpdated;
    private FileResponse image;
    private String createBy;
    private OfficeResponse office;
    private List<SeatResponse> seats;

    public String getSeatMapsId() {
        return seatMapsId;
    }

    public void setSeatMapsId(String seatMapsId) {
        this.seatMapsId = seatMapsId;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public FileResponse getImage() {
        return image;
    }

    public void setImage(FileResponse image) {
        this.image = image;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public List<SeatResponse> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatResponse> seats) {
        this.seats = seats;
    }

    public OfficeResponse getOffice() {
        return office;
    }

    public void setOffice(OfficeResponse office) {
        this.office = office;
    }
}
