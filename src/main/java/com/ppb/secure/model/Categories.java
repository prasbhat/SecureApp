package com.ppb.secure.model;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@DynamicInsert
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int systemCategoriesId;

    private String categoryName;
    private String description;
    @Column(columnDefinition = "int default 0")
    private int appCount;

    public int getSystemCategoriesId() {
        return systemCategoriesId;
    }

    public void setSystemCategoriesId(int systemCategoriesId) {
        this.systemCategoriesId = systemCategoriesId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppCount() {
        return appCount;
    }

    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "systemCategoriesId=" + systemCategoriesId +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", appCount=" + appCount +
                '}';
    }
}
