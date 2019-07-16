package com.jumiapay.interview.auditlogservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumiapay.interview.auditlogservice.models.Auditlog;
import com.jumiapay.interview.auditlogservice.services.AuditlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Listener implements IListener {

    @Autowired
    private AuditlogService auditlogService;
    @Autowired
    private ObjectMapper om;

    private static Logger logger = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onListen(Object object) throws IOException, ClassNotFoundException {

        if (!object.getClass().isAssignableFrom(String.class)) {
            logger.error("Invalid Object");
        }


        try {
            Auditlog auditlog = om.readValue((String) object, Auditlog.class);
            auditlogService.save(auditlog);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
