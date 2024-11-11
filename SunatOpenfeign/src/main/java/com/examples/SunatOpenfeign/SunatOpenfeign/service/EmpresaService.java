package com.examples.SunatOpenfeign.SunatOpenfeign.service;

import com.examples.SunatOpenfeign.SunatOpenfeign.aggregates.response.ResponseSunat;
import com.examples.SunatOpenfeign.SunatOpenfeign.entity.EmpresaEntity;

import java.io.IOException;

public interface EmpresaService {

    EmpresaEntity getInfoEmpresa(String ruc);
}
