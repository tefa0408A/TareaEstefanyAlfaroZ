package com.examples.SunatOpenfeign.SunatOpenfeign.controller;

import com.examples.SunatOpenfeign.SunatOpenfeign.aggregates.response.ResponseSunat;
import com.examples.SunatOpenfeign.SunatOpenfeign.entity.EmpresaEntity;
import com.examples.SunatOpenfeign.SunatOpenfeign.service.EmpresaService;
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
    public ResponseEntity<EmpresaEntity> getInfoSunat(@PathVariable String ruc) throws IOException {
        return new ResponseEntity<>(empresaService.getInfoEmpresa(ruc),HttpStatus.OK);
    }
}
