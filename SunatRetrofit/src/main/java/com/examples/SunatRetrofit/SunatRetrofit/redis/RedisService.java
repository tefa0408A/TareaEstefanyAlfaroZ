package com.examples.SunatRetrofit.SunatRetrofit.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
//permite crear un constructor para la inyeccion
//@RequiredArgsConstructor - generar un constructor que incluye todos los atributos definidos como final
//@NoArgsConstructor - genera un constructor sin argumentos
//@AllArgsConstructor- genera un constructor que incluye todos los campos de la clase
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveInRedis(String key, String value, int exp){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key, exp, TimeUnit.MINUTES);
    }

    public String getDataFromRedis(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
