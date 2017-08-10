package com.springRedis.set;

import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/3
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SetTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testSet(){

        //向变量中添加值
        redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");

        //获取变量中的值
        Set set = redisTemplate.opsForSet().members("setValue");
        System.out.println("通过members(K key)方法获取变量中的元素值:" + set);

        //获取变量中值的长度
        long setLength = redisTemplate.opsForSet().size("setValue");
        System.out.println("通过size(K key)方法获取变量中元素值的长度:" + setLength);

        //随机获取变量中的元素
        Object randomMember = redisTemplate.opsForSet().randomMember("setValue");
        System.out.println("通过randomMember(K key)方法随机获取变量中的元素:" + randomMember);

        //随机获取变量中指定个数的元素
        List randomMembers = redisTemplate.opsForSet().randomMembers("setValue",2);
        System.out.println("通过randomMembers(K key, long count)方法随机获取变量中指定个数的元素:" + randomMembers);

        //检查给定的元素是否在变量中
        boolean isMember = redisTemplate.opsForSet().isMember("setValue","A");
        System.out.println("通过isMember(K key, Object o)方法检查给定的元素是否在变量中:" + isMember);

        //转移变量的元素值到目的变量
        boolean isMove = redisTemplate.opsForSet().move("setValue","A","destSetValue");
        if(isMove){
            set = redisTemplate.opsForSet().members("setValue");
            System.out.print("通过move(K key, V value, K destKey)方法转移变量的元素值到目的变量后的剩余元素:" + set);
            set = redisTemplate.opsForSet().members("destSetValue");
            System.out.println(",目的变量中的元素值:" + set);
        }

        //弹出变量中的元素
        Object popValue = redisTemplate.opsForSet().pop("setValue");
        System.out.print("通过pop(K key)方法弹出变量中的元素:" + popValue);
        set = redisTemplate.opsForSet().members("setValue");
        System.out.println(",剩余元素:" + set);

        //批量移除变量中的元素
        long removeCount = redisTemplate.opsForSet().remove("setValue","E","F","G");
        System.out.print("通过remove(K key, Object... values)方法移除变量中的元素个数:" + removeCount);
        set = redisTemplate.opsForSet().members("setValue");
        System.out.println(",剩余元素:" + set);

        //匹配获取键值对
        //ScanOptions.NONE为获取全部键值对
        //ScanOptions.scanOptions().match("C").build()匹配获取键位map1的键值对,不能模糊匹配
        //Cursor<Object> cursor = redisTemplate.opsForSet().scan("setValue", ScanOptions.NONE);
        Cursor<Object> cursor = redisTemplate.opsForSet().scan("setValue", ScanOptions.scanOptions().match("C").build());
        while (cursor.hasNext()){
            Object object = cursor.next();
            System.out.println("通过scan(K key, ScanOptions options)方法获取匹配的值:" + object);
        }

        redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");
        set = redisTemplate.opsForSet().members("setValue");
        System.out.println("现在的元素:" + set);

        //通过集合求差值
        List list = new ArrayList();
        list.add("destSetValue");
        Set differenceSet = redisTemplate.opsForSet().difference("setValue",list);
        System.out.println("通过difference(K key, Collection<K> otherKeys)方法获取变量中与给定集合中变量不一样的值:" + differenceSet);

        //通过给定的key求2个set变量的差值
        differenceSet = redisTemplate.opsForSet().difference("setValue","destSetValue");
        System.out.println("通过difference(K key, Collection<K> otherKeys)方法获取变量中与给定变量不一样的值:" + differenceSet);

        //将求出来的差值元素保存
        redisTemplate.opsForSet().differenceAndStore("setValue","destSetValue","storeSetValue");
        set = redisTemplate.opsForSet().members("storeSetValue");
        System.out.println("通过differenceAndStore(K key, K otherKey, K destKey)方法将求出来的差值元素保存:" + set);

        //将求出来的差值元素保存
        redisTemplate.opsForSet().differenceAndStore("setValue",list,"storeSetValue");
        set = redisTemplate.opsForSet().members("storeSetValue");
        System.out.println("通过differenceAndStore(K key, Collection<K> otherKeys, K destKey)方法将求出来的差值元素保存:" + set);

        //获取去重的随机元素
        set = redisTemplate.opsForSet().distinctRandomMembers("setValue",2);
        System.out.println("通过distinctRandomMembers(K key, long count)方法获取去重的随机元素:" + set);

        //获取2个变量中的交集
        set = redisTemplate.opsForSet().intersect("setValue","destSetValue");
        System.out.println("通过intersect(K key, K otherKey)方法获取交集元素:" + set);

        //获取多个变量之间的交集
        set = redisTemplate.opsForSet().intersect("setValue",list);
        System.out.println("通过intersect(K key, Collection<K> otherKeys)方法获取交集元素:" + set);

        //获取2个变量交集后保存到最后一个参数上
        redisTemplate.opsForSet().intersectAndStore("setValue","destSetValue","intersectValue");
        set = redisTemplate.opsForSet().members("intersectValue");
        System.out.println("通过intersectAndStore(K key, K otherKey, K destKey)方法将求出来的交集元素保存:" + set);

        //获取多个变量的交集并保存到最后一个参数上
        redisTemplate.opsForSet().intersectAndStore("setValue",list,"intersectListValue");
        set = redisTemplate.opsForSet().members("intersectListValue");
        System.out.println("通过intersectAndStore(K key, Collection<K> otherKeys, K destKey)方法将求出来的交集元素保存:" + set);

        //获取2个变量的合集
        set = redisTemplate.opsForSet().union("setValue","destSetValue");
        System.out.println("通过union(K key, K otherKey)方法获取2个变量的合集元素:" + set);

        //获取多个变量的合集
        set = redisTemplate.opsForSet().union("setValue",list);
        System.out.println("通过union(K key, Collection<K> otherKeys)方法获取多个变量的合集元素:" + set);

        //获取2个变量合集后保存到最后一个参数上
        redisTemplate.opsForSet().unionAndStore("setValue","destSetValue","unionValue");
        set = redisTemplate.opsForSet().members("unionValue");
        System.out.println("通过unionAndStore(K key, K otherKey, K destKey)方法将求出来的交集元素保存:" + set);

        //获取多个变量的合集并保存到最后一个参数上
        redisTemplate.opsForSet().unionAndStore("setValue",list,"unionListValue");
        set = redisTemplate.opsForSet().members("unionListValue");
        System.out.println("通过unionAndStore(K key, Collection<K> otherKeys, K destKey)方法将求出来的交集元素保存:" + set);
    }

}
