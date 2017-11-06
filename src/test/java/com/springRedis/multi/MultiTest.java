package com.springRedis.multi;

import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/4
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MultiTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testMulti(){
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        redisTemplate.setEnableTransactionSupport(true);
        //在未提交之前是获取不到值得，同时再次循环报错
        while (true){
            redisTemplate.watch("multiTest");
            redisTemplate.multi();
            valueOperations.set("multiTest",1);
            valueOperations.increment("multiTest",2);
            Object o = valueOperations.get("multiTest");
            System.out.println(o);
            List list = redisTemplate.exec();
            o = valueOperations.get("multiTest");
            System.out.println(list);
            System.out.println(o);
        }

    }



}
