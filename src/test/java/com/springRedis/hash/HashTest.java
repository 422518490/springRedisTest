package com.springRedis.hash;

import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/2
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class HashTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testHash(){
        //新增hashMap值
        redisTemplate.opsForHash().put("hashValue","map1","map1-1");
        redisTemplate.opsForHash().put("hashValue","map2","map2-2");

        //获取指定变量中的hashMap值
        List<Object> hashList = redisTemplate.opsForHash().values("hashValue");
        System.out.println("通过values(H key)方法获取变量中的hashMap值:" + hashList);

        //获取变量中的键值对
        Map<Object,Object> map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过entries(H key)方法获取变量中的键值对:" + map);

        //获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null
        Object mapValue = redisTemplate.opsForHash().get("hashValue","map1");
        System.out.println("通过get(H key, Object hashKey)方法获取map键的值:" + mapValue);

        //判断变量中是否有指定的map键
        boolean hashKeyBoolean = redisTemplate.opsForHash().hasKey("hashValue","map3");
        System.out.println("通过hasKey(H key, Object hashKey)方法判断变量中是否存在map键:" + hashKeyBoolean);

        //获取变量中的键
        Set<Object> keySet = redisTemplate.opsForHash().keys("hashValue");
        System.out.println("通过keys(H key)方法获取变量中的键:" + keySet);

        //获取变量的长度
        long hashLength = redisTemplate.opsForHash().size("hashValue");
        System.out.println("通过size(H key)方法获取变量的长度:" + hashLength);

        //使变量中的键以值的大小进行自增长
        double hashIncDouble = redisTemplate.opsForHash().increment("hashInc","map1",3);
        System.out.println("通过increment(H key, HK hashKey, double delta)方法使变量中的键以值的大小进行自增长:" + hashIncDouble);

        //使变量中的键以值的大小进行自增长
        long hashIncLong = redisTemplate.opsForHash().increment("hashInc","map2",6);
        System.out.println("通过increment(H key, HK hashKey, long delta)方法使变量中的键以值的大小进行自增长:" + hashIncLong);

        //以集合的方式获取变量中的值
        List<Object> list = new ArrayList<Object>();
        list.add("map1");
        list.add("map2");
        List mapValueList = redisTemplate.opsForHash().multiGet("hashValue",list);
        System.out.println("通过multiGet(H key, Collection<HK> hashKeys)方法以集合的方式获取变量中的值:"+mapValueList);

        //以map集合的形式添加键值对
        Map newMap = new HashMap();
        newMap.put("map3","map3-3");
        newMap.put("map5","map5-5");
        redisTemplate.opsForHash().putAll("hashValue",newMap);
        map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过putAll(H key, Map<? extends HK,? extends HV> m)方法以map集合的形式添加键值对:" + map);

        //如果变量值存在，在变量中可以添加不存在的的键值对，如果变量不存在，则新增一个变量，同时将键值对添加到该变量
        redisTemplate.opsForHash().putIfAbsent("hashValue","map6","map6-6");
        map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过putIfAbsent(H key, HK hashKey, HV value)方法添加不存在于变量中的键值对:" + map);

        //匹配获取键值对
        //ScanOptions.NONE为获取全部键值对
        //ScanOptions.scanOptions().match("map1").build()匹配获取键位map1的键值对,不能模糊匹配
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("hashValue",ScanOptions.scanOptions().match("map1").build());
        //Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("hashValue",ScanOptions.NONE);
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            System.out.println("通过scan(H key, ScanOptions options)方法获取匹配键值对:" + entry.getKey() + "---->" + entry.getValue());
        }

        //删除变量中的键值对，可以传入多个参数，删除多个键值对
        redisTemplate.opsForHash().delete("hashValue","map1","map2");
        map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过delete(H key, Object... hashKeys)方法删除变量中的键值对后剩余的:" + map);

    }

}
