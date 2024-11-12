package com.examples.SunatRetrofit.SunatRetrofit.service.impl;

import com.examples.SunatRetrofit.SunatRetrofit.aggregates.constants.Constants;
import com.examples.SunatRetrofit.SunatRetrofit.aggregates.response.ResponseSunat;
import com.examples.SunatRetrofit.SunatRetrofit.entity.EmpresaEntity;
import com.examples.SunatRetrofit.SunatRetrofit.redis.RedisService;
import com.examples.SunatRetrofit.SunatRetrofit.repository.EmpresaRepository;
import com.examples.SunatRetrofit.SunatRetrofit.retrofit.ClientSunatService;
import com.examples.SunatRetrofit.SunatRetrofit.retrofit.impl.ClientSunatServiceImpl;
import com.examples.SunatRetrofit.SunatRetrofit.service.EmpresaService;
import com.examples.SunatRetrofit.SunatRetrofit.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.sql.Timestamp;
import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {


    private final RedisService redisService;
    //private final ClientSunat clientSunat;
    private final EmpresaRepository empresaRepository;

    ClientSunatService sunatServiceRetrofit = ClientSunatServiceImpl.getRetrofit().create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(RedisService redisService, /*ClientSunat clientSunat,*/ EmpresaRepository empresaRepository) {
        this.redisService = redisService;
        //this.clientSunat = clientSunat;
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
            //datosSunat = executionSunat(ruc);
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

    //metodo que prepara el cliente retrofit
    private Call<ResponseSunat> prepareSunatRetrofit(String ruc){
        String tokenComplete = "Bearer "+token;
        return sunatServiceRetrofit.getInfoSunat(tokenComplete,ruc);
    }

    //metodo que ejecute el client OpenFeign de Reniec
    private ResponseSunat executionReniec(String ruc){
        String tokenOk = Constants.BEARER+token;
        return clientSunat.getPersonaReniec(ruc,tokenOk);
    }

}
