package com.server.datn.server.common.dto.company;

import java.util.List;

public class SeatMapRequest {
    private String name;
    private String imageId;
    private String officeId;
    private String status;
    private List<SeatRequest> seats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public List<SeatRequest> getSeats() {
        return seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSeats(List<SeatRequest> seats) {
        this.seats = seats;
    }
}
