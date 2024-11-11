package com.examples.SunatResttemplate.SunatResttemplate.controller;

import com.examples.SunatResttemplate.SunatResttemplate.aggregates.response.ResponseSunat;
import com.examples.SunatResttemplate.SunatResttemplate.entity.EmpresaEntity;
import com.examples.SunatResttemplate.SunatResttemplate.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("sunat/{ruc}")
    public ResponseEntity<ResponseSunat> getInfoSunat(@PathVariable String ruc){
        return new ResponseEntity<>(empresaService.getInfoSunat(ruc),HttpStatus.OK);
    }
}
