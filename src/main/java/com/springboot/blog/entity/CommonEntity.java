package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonEntity {
    @Column(name = "CREATED_DATE", nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy="Unknown";
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE",insertable = false)
    private Date lastUpdatedDate;

    @PrePersist
    public void createdTimeStamps(){
        createdDate=new Date();
        lastUpdatedDate=createdDate;
    }

    @PreUpdate
    public void updatedTimeStamps(){
        lastUpdatedDate=new Date();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}