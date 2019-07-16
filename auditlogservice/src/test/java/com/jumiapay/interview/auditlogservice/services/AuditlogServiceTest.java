package com.jumiapay.interview.auditlogservice.services;

import com.jumiapay.interview.auditlogservice.models.Auditlog;
import com.jumiapay.interview.auditlogservice.models.Result;
import com.jumiapay.interview.auditlogservice.repository.impl.AuditlogRepository;
import com.jumiapay.interview.auditlogservice.utils.ModelUtils;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuditlogServiceTest {


    @MockBean
    private AuditlogRepository repository;

    private final String USER_ID = "10009";

    @Before
    public void setup() {

        List<Auditlog> list = new ArrayList<>();
        list.add(ModelUtils.getCustomerAuditlogInstance());
        list.add(ModelUtils.getOperationAuditlogInstance());
        list.add(ModelUtils.getCustomerServiceAuditlogInstance());

        Mockito.when(repository.retrieveAllByUser(USER_ID,0,10))
                .thenReturn(new Result<Auditlog>(list,list.size(),0,10));

        Mockito.when(repository.retrieveAll(0,10))
                .thenReturn(new Result<>(list,list.size(),0,10));
    }

    @Test
    public void shouldSucceedRetrievingUserDocument() {

        try {
            Result<Auditlog> result = repository.retrieveAllByUser(USER_ID,0,10);
                if (result != null) {
                List<Auditlog> list = result.getList();
                Assert.assertFalse(list.isEmpty());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldSucceedRetrievingUserDocumentByRange() {
        Date date = ModelUtils.getCustomerAuditlogInstance().getDateCreated();

        try {
            Result<Auditlog> result = repository.retrieveAllByUser(USER_ID,0,10,date,date);
            if (result != null) {
                List<Auditlog> list = result.getList();
                Assert.assertFalse(list.isEmpty());
                Auditlog auditlog = list.get(0);
                Assert.assertTrue(auditlog.getDateCreated() == date);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldSucceedRetrievingAllDocuments() {

        try {
            Result<Auditlog> result = repository.retrieveAll(0,10);
            if (result != null) {
                List<Auditlog> list = result.getList();
                Assert.assertFalse(list.isEmpty());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldSucceedRetrievingAllDocumentsByRange() {
        Date date = ModelUtils.getCustomerAuditlogInstance().getDateCreated();

        try {
            Result<Auditlog> result = repository.retrieveAll(0,10,date,date);
            if (result != null) {
                List<Auditlog> list = result.getList();
                Assert.assertFalse(list.isEmpty());
                Auditlog auditlog = list.get(0);
                Assert.assertTrue(auditlog.getDateCreated() == date);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }



}
