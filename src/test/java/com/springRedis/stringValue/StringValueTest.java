package com.springRedis.stringValue;

import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/1
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class StringValueTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testStringValue() throws InterruptedException {
        //新增一个字符串类型的值
        redisTemplate.opsForValue().set("stringValue","bbb");

        //获取stringValue字符串
        String stringValue = redisTemplate.opsForValue().get("stringValue")+"";
        System.out.println("通过get(Object key)方法获取set(K key, V value)方法新增的字符串值:" + stringValue);

        //在原有的值基础上新增字符串到末尾
        redisTemplate.opsForValue().append("stringValue","aaa");
        String stringValueAppend = redisTemplate.opsForValue().get("stringValue")+"";
        System.out.println("通过append(K key, String value)方法修改后的字符串:"+stringValueAppend);

        //截取字符串，从开始下标位置开始到结束下标的位置(包含结束下标)的字符串
        String cutString = redisTemplate.opsForValue().get("stringValue",0,3);
        System.out.println("通过get(K key, long start, long end)方法获取截取的字符串:"+cutString);

        //获取原来的值并重新赋新值
        String oldAndNewStringValue = redisTemplate.opsForValue().getAndSet("stringValue","ccc")+"";
        System.out.print("通过getAndSet(K key, V value)方法获取原来的" + oldAndNewStringValue + ",");
        String newStringValue = redisTemplate.opsForValue().get("stringValue")+"";
        System.out.println("修改过后的值:"+newStringValue);

        //key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
        redisTemplate.opsForValue().setBit("stringValue",1,false);
        newStringValue = redisTemplate.opsForValue().get("stringValue")+"";
        System.out.println("通过setBit(K key,long offset,boolean value)方法修改过后的值:"+newStringValue);

        //判断指定的位置ASCII码的bit位是否为1
        boolean bitBoolean = redisTemplate.opsForValue().getBit("stringValue",1);
        System.out.println("通过getBit(K key,long offset)方法判断指定bit位的值是:" + bitBoolean);

        //再次获取操作对象进行其他操作
        RedisOperations redisOperations = redisTemplate.opsForValue().getOperations();
        System.out.println(redisOperations.opsForValue().get("stringValue"));

        //获取指定字符串的长度
        Long stringValueLength = redisTemplate.opsForValue().size("stringValue");
        System.out.println("通过size(K key)方法获取字符串的长度:"+stringValueLength);

        //以增量的方式存储在变量中
        double stringValueDouble = redisTemplate.opsForValue().increment("doubleValue",5);
        //stringValueDouble = redisTemplate.opsForValue().increment("intValue",2);
        System.out.println("通过increment(K key, double delta)方法以增量方式存储double值:" + stringValueDouble);

        //以增量的方式存储在变量中
        double stringValueLong = redisTemplate.opsForValue().increment("longValue",6);
        //stringValueDouble = redisTemplate.opsForValue().increment("intValue",2);
        System.out.println("通过increment(K key, long delta)方法以增量方式存储long值:" + stringValueLong);

        //如果值不存在则新增,存在则不改变已经有的值
        boolean absentBoolean = redisTemplate.opsForValue().setIfAbsent("absentValue","fff");
        System.out.println("通过setIfAbsent(K key, V value)方法判断变量值absentValue是否存在:" + absentBoolean);
        if(absentBoolean){
            String absentValue = redisTemplate.opsForValue().get("absentValue")+"";
            System.out.print(",不存在，则新增后的值是:"+absentValue);
            boolean existBoolean = redisTemplate.opsForValue().setIfAbsent("absentValue","eee");
            System.out.print(",再次调用setIfAbsent(K key, V value)判断absentValue是否存在并重新赋值:" + existBoolean);
            if(!existBoolean){
                absentValue = redisTemplate.opsForValue().get("absentValue")+"";
                System.out.print("如果存在,则重新赋值后的absentValue变量的值是:" + absentValue);
            }
        }

        //设置变量值的过期时间
        redisTemplate.opsForValue().set("timeOutValue","timeOut",5,TimeUnit.SECONDS);
        String timeOutValue = redisTemplate.opsForValue().get("timeOutValue")+"";
        System.out.println("通过set(K key, V value, long timeout, TimeUnit unit)方法设置过期时间，过期之前获取的数据:"+timeOutValue);
        Thread.sleep(5*1000);
        timeOutValue = redisTemplate.opsForValue().get("timeOutValue")+"";
        System.out.print(",等待10s过后，获取的值:"+timeOutValue);

        //覆盖从指定位置开始的值
        redisTemplate.opsForValue().set("absentValue","dd",1);
        String overrideString = redisTemplate.opsForValue().get("absentValue")+"";
        System.out.println("通过set(K key, V value, long offset)方法覆盖部分的值:"+overrideString);

        //设置map集合到redis
        Map valueMap = new HashMap();
        valueMap.put("valueMap1","map1");
        valueMap.put("valueMap2","map2");
        valueMap.put("valueMap3","map3");
        redisTemplate.opsForValue().multiSet(valueMap);
        //根据List集合取出对应的map的value值
        List paraList = new ArrayList();
        paraList.add("valueMap1");
        paraList.add("valueMap2");
        paraList.add("valueMap3");
        List<String> valueList = redisTemplate.opsForValue().multiGet(paraList);
        for (String value : valueList){
            System.out.println("通过multiGet(Collection<K> keys)方法获取map值:" + value);
        }
        //如果对应的map集合名称不存在，则添加，如果存在则不做修改
        redisTemplate.opsForValue().multiSetIfAbsent(valueMap);
    }

}
