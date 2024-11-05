package com.rediscache.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component

public class RedisService {
    @Autowired(required = true)
    private RedisTemplate<String ,Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    public  void setKey(String key,Object object,Long ttl) throws JsonProcessingException {
        String valueToBeSaved = objectMapper.writeValueAsString(object);
        redisTemplate.opsForValue().set(key,valueToBeSaved,ttl, TimeUnit.SECONDS );
     }
     public <T>T getValueByKey(String key,Class<T> type){
         Object value= redisTemplate.opsForValue().get(key);
         try {
            return value!=null ? objectMapper.readValue(value.toString(), type) :null;
         }catch (Exception e){
             e.printStackTrace();
             return null;
         }

     }
     public  boolean deleteFromRedisCache(String key){
        return  Boolean.TRUE.equals(redisTemplate.delete(key));
     }
     public long deleteMultiFromRedisCache(List<String> keys){
         Long delete = redisTemplate.delete(keys);
          return delete==null? 0:delete;
     }
}
