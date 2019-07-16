package com.jumiapay.interview.auditlogservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumiapay.interview.auditlogservice.exceptions.BadRequestException;
import com.jumiapay.interview.auditlogservice.models.Auditlog;
import com.jumiapay.interview.auditlogservice.models.Result;
import com.jumiapay.interview.auditlogservice.services.AuditlogService;
import com.jumiapay.interview.auditlogservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("api/rest/v1/com/jumia/pay/auditlog")
public class DefaultController {

    @Autowired
    private AuditlogService auditlogService;
    @Autowired
    private ObjectMapper om;

    @GetMapping(value = "/findbyuser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Result<Auditlog> findByUser(@RequestHeader("UserId") String userId, @RequestHeader("PageNumber") int pageNumber, @RequestHeader("PageSize") int pageSize) {

        if (AppUtils.isNullOrEmpty(userId)) {
            throw new BadRequestException("Invalid Request");
        }

        return auditlogService.retrieveByUser(userId,pageNumber,pageSize);
    }

    @GetMapping(value = "/findrangebyuser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Result<Auditlog> findRangeByUser(@RequestHeader("UserId") String userId, @RequestHeader("PageNumber") int pageNumber,
                                          @RequestHeader("PageSize") int pageSize, @RequestHeader("From") String from,
                                          @RequestHeader("To") String to) throws IOException {

        if (AppUtils.isNullOrEmpty(userId)) {
            throw new BadRequestException("Invalid Request");
        }

        Date dateFrom = om.convertValue(from,Date.class);
        Date dateTo = om.convertValue(to,Date.class);

        return auditlogService.retrieveRangeByUser(userId,pageNumber,pageSize,dateFrom,dateTo);
    }

    @GetMapping(value = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Result<Auditlog> findAll(@RequestHeader("PageNumber") int pageNumber, @RequestHeader("PageSize") int pageSize) {
        return auditlogService.retrieveAll(pageNumber, pageSize);
    }

    @GetMapping(value = "/findallbyrange", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Result<Auditlog> findAllByRange(@RequestHeader("PageNumber") int pageNumber, @RequestHeader("PageSize") int pageSize,
                                         @RequestHeader("From") String from, @RequestHeader("To") String to) {

        Date dateFrom = om.convertValue(from,Date.class);
        Date dateTo = om.convertValue(to,Date.class);

        return auditlogService.retrieveAllByRange(pageNumber,pageSize,dateFrom,dateTo);
    }


}
