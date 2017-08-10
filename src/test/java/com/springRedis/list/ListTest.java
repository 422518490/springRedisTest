package com.springRedis.list;

import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/2
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ListTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testList(){
        //在变量左边添加元素值
        redisTemplate.opsForList().leftPush("list","a");
        redisTemplate.opsForList().leftPush("list","b");
        redisTemplate.opsForList().leftPush("list","c");

        //获取集合指定位置的值
        String listValue = redisTemplate.opsForList().index("list",1) + "";
        System.out.println("通过index(K key, long index)方法获取指定位置的值:" + listValue);

        //获取集合中的所有值
        List<Object> list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过range(K key, long start, long end)方法获取指定范围的集合值:"+list);

        //把最后一个参数值放到指定集合的第一个出现中间参数的前面，如果中间参数值存在的话
        redisTemplate.opsForList().leftPush("list","a","n");
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过leftPush(K key, V pivot, V value)方法把值放到指定参数值前面:" + list);

        //向左边批量添加参数元素
        redisTemplate.opsForList().leftPushAll("list","w","x","y");
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过leftPushAll(K key, V... values)方法批量添加元素:" + list);

        //以集合的方式向左边批量添加元素
        List newList = new ArrayList();
        newList.add("o");
        newList.add("p");
        newList.add("q");
        redisTemplate.opsForList().leftPushAll("list",newList);
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过leftPushAll(K key, Collection<V> values)方法以集合的方式批量添加元素:" + list);

        //如果存在集合则添加元素
        redisTemplate.opsForList().leftPushIfPresent("presentList","o");
        list =  redisTemplate.opsForList().range("presentList",0,-1);
        System.out.println("通过leftPushIfPresent(K key, V value)方法向已存在的集合添加元素:" + list);

        //向集合最右边添加元素
        redisTemplate.opsForList().rightPush("list","w");
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过rightPush(K key, V value)方法向最右边添加元素:" + list);

        //向集合中第一次出现第二个参数变量元素的右边添加第三个参数变量的元素值
        redisTemplate.opsForList().rightPush("list","w","r");
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过rightPush(K key, V pivot, V value)方法向最右边添加元素:" + list);

        //向右边批量添加元素
        redisTemplate.opsForList().rightPushAll("list","j","k");
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过rightPushAll(K key, V... values)方法向最右边批量添加元素:" + list);

        //以集合方式向右边添加元素
        newList.clear();
        newList.add("g");
        newList.add("h");
        redisTemplate.opsForList().rightPushAll("list",newList);
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过rightPushAll(K key, Collection<V> values)方法向最右边以集合方式批量添加元素:" + list);

        //向已存在的集合中添加元素
        redisTemplate.opsForList().rightPushIfPresent("presentList","d");
        list =  redisTemplate.opsForList().range("presentList",0,-1);
        System.out.println("通过rightPushIfPresent(K key, V value)方法已存在的集合向最右边添加元素:" + list);

        long listLength = redisTemplate.opsForList().size("list");
        System.out.println("通过size(K key)方法获取集合list的长度为:" + listLength);

        //移除集合中的左边第一个元素
        Object popValue = redisTemplate.opsForList().leftPop("list");
        System.out.print("通过leftPop(K key)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println(",剩余的元素是:" + list);

        //移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
        popValue = redisTemplate.opsForList().leftPop("presentList",1, TimeUnit.SECONDS);
        System.out.print("通过leftPop(K key, long timeout, TimeUnit unit)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("presentList",0,-1);
        System.out.println(",剩余的元素是:" + list);

        //移除集合中右边的元素
        popValue = redisTemplate.opsForList().rightPop("list");
        System.out.print("通过rightPop(K key)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println(",剩余的元素是:" + list);

        //移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
        popValue = redisTemplate.opsForList().rightPop("presentList",1, TimeUnit.SECONDS);
        System.out.print("通过rightPop(K key, long timeout, TimeUnit unit)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("presentList",0,-1);
        System.out.println(",剩余的元素是:" + list);

        //移除集合中右边的元素，同时在左边加入一个元素
        popValue = redisTemplate.opsForList().rightPopAndLeftPush("list","12");
        System.out.print("通过rightPopAndLeftPush(K sourceKey, K destinationKey)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println(",剩余的元素是:" + list);

        //这里不能采用批量添加的方式，否则会不能得到相应的结果
        redisTemplate.opsForList().leftPush("presentList","10");
        redisTemplate.opsForList().leftPush("presentList","12");
        System.out.println("presentList:" + redisTemplate.opsForList().range("presentList",0,-1));
        //移除集合中右边的元素在等待的时间里，同时在左边添加元素，如果超过等待的时间仍没有元素则退出
        popValue = redisTemplate.opsForList().rightPopAndLeftPush("presentList","13",1,TimeUnit.SECONDS);
        System.out.println("通过rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit)方法移除的元素是:" + popValue);
        list =  redisTemplate.opsForList().range("presentList",0,-1);
        System.out.print(",剩余的元素是:" + list);

        //在集合的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增，超过集合下标+1则会报错
        redisTemplate.opsForList().set("presentList",3,"15");
        list =  redisTemplate.opsForList().range("presentList",0,-1);
        System.out.print("通过set(K key, long index, V value)方法在指定位置添加元素后:" + list);

        //从存储在键中的列表中删除等于值的元素的第一个计数事件。
        //计数参数以下列方式影响操作：
        //count> 0：删除等于从头到尾移动的值的元素。
        //count <0：删除等于从尾到头移动的值的元素。
        //count = 0：删除等于value的所有元素。
        long removeCount = redisTemplate.opsForList().remove("list",0,"w");
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过remove(K key, long count, Object value)方法移除元素数量:" + removeCount);
        System.out.println(",剩余的元素:" + list);

        //截取集合元素长度，保留长度内的数据
        redisTemplate.opsForList().trim("list",0,5);
        list =  redisTemplate.opsForList().range("list",0,-1);
        System.out.println("通过trim(K key, long start, long end)方法截取后剩余元素:" + list);
    }

}
