package com.examples.SunatRetrofit.SunatRetrofit.service;


import com.examples.SunatRetrofit.SunatRetrofit.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface EmpresaService {

    ResponseSunat getInfoEmpresa(String ruc) throws IOException;
}
