package com.jumiapay.interview.auditlogservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumiapay.interview.auditlogservice.models.Auditlog;
import com.jumiapay.interview.auditlogservice.models.Result;
import com.jumiapay.interview.auditlogservice.services.AuditlogService;
import com.jumiapay.interview.auditlogservice.utils.ModelUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DefaultController.class)
public class DefaultControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuditlogService auditlogService;
    @Autowired
    private ObjectMapper om;

    private Date range;

    @Before
    public void setup() throws IOException {
        range = om.convertValue("2019-04-16T11:10:16.640+00:00",Date.class);

        List<Auditlog> list = new ArrayList<>();
        list.add(ModelUtils.getCustomerAuditlogInstance());
        list.add(ModelUtils.getOperationAuditlogInstance());
        list.add(ModelUtils.getCustomerServiceAuditlogInstance());

        given(auditlogService.retrieveByUser("10009",0,10))
                .willReturn(new Result<>(list,list.size(),0,10));

        given(auditlogService.retrieveRangeByUser("10009",0,10,range,range))
                .willReturn(new Result<>(list,list.size(),0,10));

        given(auditlogService.retrieveAll(0,10))
                .willReturn(new Result<>(list,list.size(),0,10));

        given(auditlogService.retrieveAllByRange(0,10,range,range))
                .willReturn(new Result<>(list,list.size(),0,10));
    }

    @Test
    public void shouldReturnJsonArrayWithOkStatus_whenFindingByUser() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("UserId","10009");
        headers.add("PageNumber","0");
        headers.add("PageSize","10");

        mvc.perform(MockMvcRequestBuilders
                .get("/api/rest/v1/com/jumia/pay/auditlog/findbyuser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("list",hasSize(3)));

    }

    @Test
    public void shouldReturnJsonArrayWithOkStatus_whenFindingRangeByUser() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("UserId","10009");
        headers.add("PageNumber","0");
        headers.add("PageSize","10");
        headers.add("From","2019-04-16T11:10:16.640+00:00");
        headers.add("To","2019-04-16T11:10:16.640+00:00");

        mvc.perform(MockMvcRequestBuilders
                .get("/api/rest/v1/com/jumia/pay/auditlog/findrangebyuser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("list",hasSize(3)));

    }

    @Test
    public void shouldReturnJsonArrayWithOkStatus_whenFindingAll() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("PageNumber","0");
        headers.add("PageSize","10");

        mvc.perform(MockMvcRequestBuilders
                .get("/api/rest/v1/com/jumia/pay/auditlog/findall")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("list",hasSize(3)));

    }

    @Test
    public void shouldReturnJsonArrayWithOkStatus_whenFindingAllByRange() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("PageNumber","0");
        headers.add("PageSize","10");
        headers.add("From","2019-04-16T11:10:16.640+00:00");
        headers.add("To","2019-04-16T11:10:16.640+00:00");

        mvc.perform(MockMvcRequestBuilders
                .get("/api/rest/v1/com/jumia/pay/auditlog/findallbyrange")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("list",hasSize(3)));

    }
}
