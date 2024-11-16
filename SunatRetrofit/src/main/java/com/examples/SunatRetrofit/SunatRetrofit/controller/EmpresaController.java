package com.examples.SunatRetrofit.SunatRetrofit.controller;

import com.examples.SunatRetrofit.SunatRetrofit.aggregates.response.ResponseSunat;
import com.examples.SunatRetrofit.SunatRetrofit.service.EmpresaService;
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

    @GetMapping("/sunat")
    public ResponseEntity<ResponseSunat> getInfoSunat(@RequestParam("ruc") String ruc) throws IOException {
        return new ResponseEntity<>(empresaService.getInfoEmpresa(ruc),HttpStatus.OK);
    }
}
