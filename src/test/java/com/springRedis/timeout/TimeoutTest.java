package com.springRedis.timeout;

import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/4
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TimeoutTest{

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;



    @Test
    public void testTimeout(){
        int i = 1;
        while (true){

            redisTemplate.opsForSet().add("sv",i+"");
            i++;
        }
    }
    @Test
    public void getTimeoutTest(){
        while (true){
            System.out.println("timeOutValue1:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest2(){
        while (true){
            System.out.println("timeOutValue2:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest3(){
        while (true){
            System.out.println("timeOutValue3:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest4(){
        while (true){
            System.out.println("timeOutValue4:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest5(){
        while (true){
            System.out.println("timeOutValue5:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest6(){
        while (true){
            System.out.println("timeOutValue6:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest7(){
        while (true){
            System.out.println("timeOutValue7:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest8(){
        while (true){
            System.out.println("timeOutValue8:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest9(){
        while (true){
            System.out.println("timeOutValue9:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest10(){
        while (true){
            System.out.println("timeOutValue10:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest11(){
        while (true){
            System.out.println("timeOutValue11:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest12(){
        while (true){
            System.out.println("timeOutValue12:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest13(){
        while (true){
            System.out.println("timeOutValue13:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest14(){
        while (true){
            System.out.println("timeOutValue14:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest15(){
        while (true){
            System.out.println("timeOutValue15:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest16(){
        while (true){
            System.out.println("timeOutValue16:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest17(){
        while (true){
            System.out.println("timeOutValue17:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest18(){
        while (true){
            System.out.println("timeOutValue18:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest19(){
        while (true){
            System.out.println("timeOutValue19:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest20(){
        while (true){
            System.out.println("timeOutValue20:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest21(){
        while (true){
            System.out.println("timeOutValue21:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest22(){
        while (true){
            System.out.println("timeOutValue22:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest23(){
        while (true){
            System.out.println("timeOutValue23:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest24(){
        while (true){
            System.out.println("timeOutValue24:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

    @Test
    public void getTimeoutTest25(){
        while (true){
            System.out.println("timeOutValue25:" + redisTemplate.opsForSet().pop("sv"));
        }
    }

}
