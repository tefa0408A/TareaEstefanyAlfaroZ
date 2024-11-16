package com.examples.SunatRetrofit.SunatRetrofit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    //OBJECTmAPPER = PARA PODER LEER LOS MENSAJES le pertenece a jaxxon
    private static final ObjectMapper OBJECT_MAPPER= new ObjectMapper();
    public static <T> String convertirAString(T objeto) throws JsonProcessingException{
        return OBJECT_MAPPER.writeValueAsString(objeto);
    }

    public static <T> T convertirDesdeString(String json, Class<T> tipoClase) throws JsonProcessingException{
        return OBJECT_MAPPER.readValue(json,tipoClase);
    }
}
