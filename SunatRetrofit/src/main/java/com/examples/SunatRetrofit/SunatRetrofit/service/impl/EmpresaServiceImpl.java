package com.examples.SunatRetrofit.SunatRetrofit.service.impl;

import com.examples.SunatRetrofit.SunatRetrofit.aggregates.constants.Constants;
import com.examples.SunatRetrofit.SunatRetrofit.aggregates.response.ResponseSunat;
import com.examples.SunatRetrofit.SunatRetrofit.redis.RedisService;
import com.examples.SunatRetrofit.SunatRetrofit.retrofit.ClientSunatService;
import com.examples.SunatRetrofit.SunatRetrofit.retrofit.impl.ClientSunatServiceImpl;
import com.examples.SunatRetrofit.SunatRetrofit.service.EmpresaService;
import com.examples.SunatRetrofit.SunatRetrofit.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {


    private final RedisService redisService;
    //instancia de retrofit que pueda usar
    ClientSunatService sunatServiceRetrofit = ClientSunatServiceImpl.getRetrofit().create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;


    @Override
    public ResponseSunat getInfoEmpresa(String ruc) throws IOException {
        ResponseSunat responseSunat = new ResponseSunat();
        //logica del diagrama
        //recupero la informacion de Redis
        String sunatRedisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_SUNAT+ruc);
        //validando que la info exista
        if(Objects.nonNull(sunatRedisInfo)){
            responseSunat = Util.convertirDesdeString(sunatRedisInfo,ResponseSunat.class);
        }else{
            Response<ResponseSunat> executeSunat = prepareSunatRetrofit(ruc).execute();
            if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
                //recupero el body, porque tiene la info
                responseSunat = executeSunat.body();
                String dataParaRedis = Util.convertirAString(responseSunat);
                redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataParaRedis,Constants.REDIS_TTL);
            }

        }
        return responseSunat;
    }

    //metodo que prepara el cliente retrofit
    private Call<ResponseSunat> prepareSunatRetrofit(String ruc){
        String tokenComplete = "Bearer "+token;
        return sunatServiceRetrofit.getInfoSunat(tokenComplete,ruc);
    }


}
