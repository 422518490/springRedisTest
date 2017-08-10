package com.springRedis.pipeline;


import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * @author liaoyubo
 * @version 1.0 2017/7/31
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class Pipeline {

    @Autowired
    private RedisTemplate<String,Object> redisTemplateObject;

    @Test
    public void testPipeLine(){
        redisTemplateObject.opsForValue().set("a",1);
        redisTemplateObject.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for (int i = 0;i < 10;i++){
                    redisConnection.incr("a".getBytes());
                }
                return null;
            }
        });
        System.out.println(redisTemplateObject.opsForValue().get("a"));
    }

}
