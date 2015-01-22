package com.softserve.edu.dto;

import com.softserve.edu.model.Hosting;
import com.softserve.edu.model.User;

public class HostingDto {

    private String country;
    private String region;
    private String city;
    private String address;
    private String firstAndLastName;
    private Integer hostingId;
    private Integer userId;

    public HostingDto(final Hosting hosting) {
        this.country = hosting.getCountry();
        this.region = hosting.getRegion();
        this.city = hosting.getCity();
        this.address = hosting.getAddress();
        this.firstAndLastName = hosting.getOwner().getFirstName() + " " + hosting.getOwner().getLastName();
        this.hostingId = hosting.getHostingId();
        this.userId = hosting.getOwner().getUserId();
    }

    public HostingDto(final Hosting hosting, final User user) {
        this.country = hosting.getCountry();
        this.region = hosting.getRegion();
        this.city = hosting.getCity();
        this.address = hosting.getAddress();
        this.firstAndLastName = user.getFirstName() + " " + user.getLastName();
        this.hostingId = hosting.getHostingId();
        this.userId = user.getUserId();
    }

    public Integer getHostingId() {
        return hostingId;
    }

    public void setHostingId(Integer hostingId) {
        this.hostingId = hostingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public String getFirstAndLastName() {
        return firstAndLastName;
    }

    public void setFirstAndLastName(String firstAndLastName) {
        this.firstAndLastName = firstAndLastName;
    }
}
