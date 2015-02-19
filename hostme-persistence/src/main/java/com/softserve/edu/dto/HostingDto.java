package com.softserve.edu.dto;

import com.softserve.edu.model.Hosting;
import com.softserve.edu.model.User;

public class HostingDto {

    private String link;
    private String name;
    private String country;
    private String region;
    private String city;
    private String address;
    private String firstAndLastName;
    private Integer hostingId;
    private Integer userId;
    private boolean family;
    private boolean children;
    private boolean pets;
    private boolean smoking;
    
    public HostingDto(final Hosting hosting) {
        country = hosting.getCountry();
        region = hosting.getRegion();
        city = hosting.getCity();
        address = hosting.getAddress();
        firstAndLastName = hosting.getOwner().getFirstName() + " " + hosting.getOwner().getLastName();
        hostingId = hosting.getHostingId();
        link = "hosting?hostingId=" + hostingId;
        name = address;
        userId = hosting.getOwner().getUserId();
        family = hosting.getFamily();
        children = hosting.getChildren();
        pets = hosting.getPets();
        smoking = hosting.getSmoking();
    }

    public HostingDto(final Hosting hosting, final User user) {
        country = hosting.getCountry();
        region = hosting.getRegion();
        city = hosting.getCity();
        address = hosting.getAddress();
        firstAndLastName = user.getFirstName() + " " + user.getLastName();
        hostingId = hosting.getHostingId();
        userId = user.getUserId();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isFamily() {
        return family;
    }

    public void setFamily(boolean family) {
        this.family = family;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public boolean isPets() {
        return pets;
    }

    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }
}
