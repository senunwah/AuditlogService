package com.jumiapay.interview.auditlogservice.models;

import com.jumiapay.interview.auditlogservice.utils.AppUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Document(collection = "auditlog")
public class Auditlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String userId;
    private Role role;
    private String action;
    private String detail;
    private AuditlogType type;
    private String entityId;
    private String entityName;
    private Object auditObject;
    private Date dateCreated;

    public Auditlog() {

        if (AppUtils.isNullOrEmpty(id)) {
            this.id = UUID.randomUUID().toString();
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public AuditlogType getType() {
        return type;
    }

    public void setType(AuditlogType type) {
        this.type = type;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Object getAuditObject() {
        return auditObject;
    }

    public void setAuditObject(Object auditObject) {
        this.auditObject = auditObject;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
