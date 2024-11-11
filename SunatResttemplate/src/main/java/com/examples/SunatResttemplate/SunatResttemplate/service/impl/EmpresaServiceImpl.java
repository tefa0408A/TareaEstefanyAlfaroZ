package com.examples.SunatResttemplate.SunatResttemplate.service.impl;

import com.examples.SunatResttemplate.SunatResttemplate.aggregates.constants.Constants;
import com.examples.SunatResttemplate.SunatResttemplate.aggregates.response.ResponseSunat;
import com.examples.SunatResttemplate.SunatResttemplate.entity.EmpresaEntity;
import com.examples.SunatResttemplate.SunatResttemplate.redis.RedisService;
import com.examples.SunatResttemplate.SunatResttemplate.service.EmpresaService;
import com.examples.SunatResttemplate.SunatResttemplate.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final RestTemplate restTemplate;

    private final RedisService redisService;

    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(RestTemplate restTemplate, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    @Override
    public ResponseSunat getInfoSunat(String ruc) {
        ResponseSunat datosSunat = new ResponseSunat();
        String redisInfo = redisService.getDataFromRedis(ruc);
        if (Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo,ResponseSunat.class);
            return datosSunat;
        }else{
            datosSunat= executeRestTemplate(ruc);
            //registro en redis
            String dataForRedis = Util.convertirAString(datosSunat);
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis, Constants.REDIS_TTL);
            return datosSunat;
        }

    }


    private ResponseSunat executeRestTemplate(String ruc){
        String url = "https://api.apis.net.pe/v2/sunat/ruc/full?numero="+ruc;
        //generar el Client RestTempalte
        ResponseEntity<ResponseSunat> executeRestTemplate = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                ResponseSunat.class
        );

        if (executeRestTemplate.getStatusCode().equals(HttpStatus.OK)){
            return executeRestTemplate.getBody();
        }else {
            return null;
        }
    }

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+token);
        return headers;
    }
}
