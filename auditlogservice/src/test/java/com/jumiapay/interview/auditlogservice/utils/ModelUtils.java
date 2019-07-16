package com.jumiapay.interview.auditlogservice.utils;

import com.jumiapay.interview.auditlogservice.models.Auditlog;
import com.jumiapay.interview.auditlogservice.models.AuditlogType;
import com.jumiapay.interview.auditlogservice.models.Role;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

public class ModelUtils {

    Date date = new Date();

    @PostConstruct
    public void init() {
        date.setMonth(Calendar.APRIL);
    }

    public static Auditlog getOperationAuditlogInstance() {
        Auditlog auditlog = new Auditlog();
        auditlog.setUserId("10009");
        auditlog.setRole(Role.OPERATION);
        auditlog.setAction("FINANCIAL TRANSACTION");
        auditlog.setDetail("Purchase of Items");
        auditlog.setType(AuditlogType.TRANSACTION);
        auditlog.setEntityId("1");
        auditlog.setEntityName("10001PURCHASE");
        auditlog.setDateCreated(new Date());

        return auditlog;
    }

    public static Auditlog getCustomerAuditlogInstance() {
        Auditlog auditlog = new Auditlog();
        auditlog.setUserId("10009");
        auditlog.setRole(Role.CUSTOMER);
        auditlog.setAction("TRANSACTION ERROR");
        auditlog.setDetail("Account debited wrongly");
        auditlog.setType(AuditlogType.COMPLAINT);
        auditlog.setEntityId("1");
        auditlog.setEntityName("10001PURCHASE");
        auditlog.setDateCreated(new Date());

        return auditlog;
    }

    public static Auditlog getCustomerServiceAuditlogInstance() {
        Auditlog auditlog = new Auditlog();
        auditlog.setUserId("10009");
        auditlog.setRole(Role.CUSTOMER_SERVICE);
        auditlog.setAction("ACCOUNT UNLOCK");
        auditlog.setDetail("Unlock Customer Account");
        auditlog.setType(AuditlogType.REQUEST);
        auditlog.setEntityId("1");
        auditlog.setEntityName("10001PURCHASE");
        auditlog.setDateCreated(new Date());

        return auditlog;
    }
}
