package com.ppb.secure.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int systemPersonalInfoId;

    private String username;
    private String password;
    private String applicationName;
    private String urlAddress;
    private String shortName;
    private String description;
    private String createdBy;
    private Timestamp creationDate;
    private String applicationType;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "systemCategoriesId")
    private Categories categories;

    public int getSystemPersonalInfoId() {
        return systemPersonalInfoId;
    }

    public void setSystemPersonalInfoId(int systemPersonalInfoId) {
        this.systemPersonalInfoId = systemPersonalInfoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "systemPersonalInfoId=" + systemPersonalInfoId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", urlAddress='" + urlAddress + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", applicationType='" + applicationType + '\'' +
                '}';
    }
}
