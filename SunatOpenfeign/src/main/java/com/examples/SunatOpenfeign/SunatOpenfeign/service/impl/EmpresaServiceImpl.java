package com.examples.SunatOpenfeign.SunatOpenfeign.service.impl;

import com.examples.SunatOpenfeign.SunatOpenfeign.aggregates.constants.Constants;
import com.examples.SunatOpenfeign.SunatOpenfeign.aggregates.response.ResponseSunat;
import com.examples.SunatOpenfeign.SunatOpenfeign.client.ClientSunat;
import com.examples.SunatOpenfeign.SunatOpenfeign.entity.EmpresaEntity;
import com.examples.SunatOpenfeign.SunatOpenfeign.redis.RedisService;
import com.examples.SunatOpenfeign.SunatOpenfeign.repository.EmpresaRepository;
import com.examples.SunatOpenfeign.SunatOpenfeign.service.EmpresaService;
import com.examples.SunatOpenfeign.SunatOpenfeign.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {


    private final RedisService redisService;
    private final ClientSunat clientSunat;
    private final EmpresaRepository empresaRepository;

    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(RedisService redisService, ClientSunat clientSunat, EmpresaRepository empresaRepository) {
        this.redisService = redisService;
        this.clientSunat = clientSunat;
        this.empresaRepository = empresaRepository;
    }


    @Override
    public EmpresaEntity getInfoEmpresa(String ruc){
        EmpresaEntity empresa = getEntityForFeing(ruc);
        if(Objects.nonNull(empresa)){
            return empresa;
        }else{
            return null;
        }

    }

    private EmpresaEntity getEntityForFeing(String ruc){
        EmpresaEntity empresaEntity = new EmpresaEntity();

        ResponseSunat datosSunat = new ResponseSunat();
        String redisInfo = redisService.getDataFromRedis(ruc);
        if (Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo,ResponseSunat.class);
        }else {
            datosSunat = executionSunat(ruc);
            String dataForRedis = Util.convertirAString(datosSunat);
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constants.REDIS_TTL);
        }

        if(Objects.nonNull(datosSunat)){
            empresaEntity.setNumero(datosSunat.getNumero());
            empresaEntity.setRazonSocial(datosSunat.getRazonSocial());
            empresaEntity.setTipoDocumento(datosSunat.getTipoDocumento());
            empresaEntity.setNumeroDocumento(datosSunat.getNumeroDocumento());
            empresaEntity.setEstado(datosSunat.getEstado());
            empresaEntity.setCondicion(datosSunat.getCondicion());
            empresaEntity.setDireccion(datosSunat.getDireccion());
            empresaEntity.setUbigeo(datosSunat.getUbigeo());
            empresaEntity.setViaTipo(datosSunat.getViaTipo());
            empresaEntity.setViaNombre(datosSunat.getViaNombre());
            empresaEntity.setZonaCodigo(datosSunat.getZonaCodigo());
            empresaEntity.setZonaTipo(datosSunat.getZonaTipo());
            empresaEntity.setNumero(datosSunat.getNumero());
            empresaEntity.setInterior(datosSunat.getInterior());
            empresaEntity.setLote(datosSunat.getLote());
            empresaEntity.setDpto(datosSunat.getDpto());
            empresaEntity.setManzana(datosSunat.getManzana());
            empresaEntity.setKilometro(datosSunat.getKilometro());
            empresaEntity.setDistrito(datosSunat.getDistrito());
            empresaEntity.setProvincia(datosSunat.getProvincia());
            empresaEntity.setDepartamento(datosSunat.getDepartamento());
            empresaEntity.setTipo(datosSunat.getTipo());
            empresaEntity.setActividadEconomica(datosSunat.getActividadEconomica());
            empresaEntity.setNumeroTrabajadores(datosSunat.getNumeroTrabajadores());
            empresaEntity.setTipoFacturacion(datosSunat.getTipoFacturacion());
            empresaEntity.setTipoContabilidad(datosSunat.getTipoContabilidad());
            empresaEntity.setComercioExterior(datosSunat.getComercioExterior());

            empresaEntity.setUserCreated(Constants.USER_CREATED);
            empresaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }
        return empresaEntity;
    }


    private ResponseSunat executionSunat(String ruc){
        String tokenOk = Constants.BEARER+token;
        return clientSunat.getPersonaSunat(ruc,tokenOk);
    }
}
