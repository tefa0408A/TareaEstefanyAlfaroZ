package com.examples.SunatResttemplate.SunatResttemplate.service;

import com.examples.SunatResttemplate.SunatResttemplate.aggregates.response.ResponseSunat;
import com.examples.SunatResttemplate.SunatResttemplate.entity.EmpresaEntity;

import java.io.IOException;

public interface EmpresaService {

    ResponseSunat getInfoSunat(String ruc);
}
