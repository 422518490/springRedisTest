package com.springRedis.zset;

import com.springRedis.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/3
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ZSetTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testZSet(){

        redisTemplate.opsForZSet().add("zSetValue","A",1);
        redisTemplate.opsForZSet().add("zSetValue","B",3);
        redisTemplate.opsForZSet().add("zSetValue","C",2);
        redisTemplate.opsForZSet().add("zSetValue","D",5);

        //获取指定区间的元素
        Set zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
        System.out.println("通过range(K key, long start, long end)方法获取指定区间的元素:" + zSetValue);

        //用于获取满足非score的排序取值
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        //range.gt("A");
        range.lt("D");
        zSetValue = redisTemplate.opsForZSet().rangeByLex("zSetValue", range);
        System.out.println("通过rangeByLex(K key, RedisZSetCommands.Range range)方法获取满足非score的排序取值元素:" + zSetValue);

        //获取从设置下标开始的设置长度的元素值
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.count(2);
        //起始下标为0
        limit.offset(1);
        zSetValue = redisTemplate.opsForZSet().rangeByLex("zSetValue", range,limit);
        System.out.println("通过rangeByLex(K key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit)方法获取满足非score的排序取值元素:" + zSetValue);

        //通过TypedTuple方式新增数据
        ZSetOperations.TypedTuple<Object> typedTuple1 = new DefaultTypedTuple<Object>("E",6.0);
        ZSetOperations.TypedTuple<Object> typedTuple2 = new DefaultTypedTuple<Object>("F",7.0);
        ZSetOperations.TypedTuple<Object> typedTuple3 = new DefaultTypedTuple<Object>("G",5.0);
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = new HashSet<ZSetOperations.TypedTuple<Object>>();
        typedTupleSet.add(typedTuple1);
        typedTupleSet.add(typedTuple2);
        typedTupleSet.add(typedTuple3);
        redisTemplate.opsForZSet().add("typedTupleSet",typedTupleSet);
        zSetValue = redisTemplate.opsForZSet().range("typedTupleSet",0,-1);
        System.out.println("通过add(K key, Set<ZSetOperations.TypedTuple<V>> tuples)方法添加元素:" + zSetValue);

        //根据设置的score获取区间值
        zSetValue = redisTemplate.opsForZSet().rangeByScore("zSetValue",1,2);
        System.out.println("通过rangeByScore(K key, double min, double max)方法根据设置的score获取区间值:" + zSetValue);

        //根据设置的score获取区间值从给定下标和给定长度获取最终值
        zSetValue = redisTemplate.opsForZSet().rangeByScore("zSetValue",1,5,1,3);
        System.out.println("通过rangeByScore(K key, double min, double max, long offset, long count)方法根据设置的score获取区间值:" + zSetValue);

        //获取RedisZSetCommands.Tuples的区间值
        typedTupleSet.clear();
        typedTupleSet = redisTemplate.opsForZSet().rangeWithScores("typedTupleSet",1,3);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = typedTupleSet.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Object value = typedTuple.getValue();
            double score = typedTuple.getScore();
            System.out.println("通过rangeWithScores(K key, long start, long end)方法获取RedisZSetCommands.Tuples的区间值:" + value + "---->" + score );
        }

        //获取RedisZSetCommands.Tuples的区间值通过分值
        typedTupleSet.clear();
        typedTupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores("typedTupleSet",5,8);
        iterator = typedTupleSet.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Object value = typedTuple.getValue();
            double score = typedTuple.getScore();
            System.out.println("通过rangeByScoreWithScores(K key, double min, double max)方法获取RedisZSetCommands.Tuples的区间值通过分值:" + value + "---->" + score );
        }

        //获取RedisZSetCommands.Tuples的区间值从给定下标和给定长度获取最终值通过分值
        typedTupleSet.clear();
        typedTupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores("typedTupleSet",5,8,1,1);
        iterator = typedTupleSet.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Object value = typedTuple.getValue();
            double score = typedTuple.getScore();
            System.out.println("通过rangeByScoreWithScores(K key, double min, double max, long offset, long count)方法获取RedisZSetCommands.Tuples的区间值从给定下标和给定长度获取最终值通过分值:" + value + "---->" + score );
        }

        //获取区间值的个数
        long count = redisTemplate.opsForZSet().count("zSetValue",1,5);
        System.out.println("通过count(K key, double min, double max)方法获取区间值的个数:" + count);

        //获取变量中元素的索引,下标开始位置为0
        long index = redisTemplate.opsForZSet().rank("zSetValue","B");
        System.out.println("通过rank(K key, Object o)方法获取变量中元素的索引:" + index);

        //匹配获取键值对
        //ScanOptions.NONE为获取全部键值对
        //ScanOptions.scanOptions().match("C").build()匹配获取键位map1的键值对,不能模糊匹配
        //Cursor<Object> cursor = redisTemplate.opsForSet().scan("setValue", ScanOptions.NONE);
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan("zSetValue", ScanOptions.NONE);
        while (cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = cursor.next();
            System.out.println("通过scan(K key, ScanOptions options)方法获取匹配元素:" + typedTuple.getValue() + "--->" + typedTuple.getScore());
        }

        //获取元素的分值
        double score = redisTemplate.opsForZSet().score("zSetValue","B");
        System.out.println("通过score(K key, Object o)方法获取元素的分值:" + score);

        //获取变量中元素的个数
        long zCard = redisTemplate.opsForZSet().zCard("zSetValue");
        System.out.println("通过zCard(K key)方法获取变量的长度:" + zCard);

        //修改变量中的元素的分值
        double incrementScore = redisTemplate.opsForZSet().incrementScore("zSetValue","C",5);
        System.out.print("通过incrementScore(K key, V value, double delta)方法修改变量中的元素的分值:" + incrementScore);
        score = redisTemplate.opsForZSet().score("zSetValue","C");
        System.out.print(",修改后获取元素的分值:" + score);
        zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
        System.out.println("，修改后排序的元素:" + zSetValue);

        //索引倒序排列元素
        zSetValue = redisTemplate.opsForZSet().reverseRange("zSetValue",0,-1);
        System.out.println("通过reverseRange(K key, long start, long end)方法倒序排列元素:" + zSetValue);

        //倒序排列指定分值区间元素
        zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue",1,5);
        System.out.println("通过reverseRangeByScore(K key, double min, double max)方法倒序排列指定分值区间元素:" + zSetValue);

        //倒序排列从给定下标和给定长度分值区间元素
        zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue",1,5,1,2);
        System.out.println("通过reverseRangeByScore(K key, double min, double max, long offset, long count)方法倒序排列从给定下标和给定长度分值区间元素:" + zSetValue);

        //倒序排序获取RedisZSetCommands.Tuples的分值区间值
        typedTupleSet.clear();
        typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("zSetValue",1,5);
        iterator = typedTupleSet.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Object value = typedTuple.getValue();
            double score1 = typedTuple.getScore();
            System.out.println("通过reverseRangeByScoreWithScores(K key, double min, double max)方法倒序排序获取RedisZSetCommands.Tuples的区间值:" + value + "---->" + score1 );
        }

        //倒序排序获取RedisZSetCommands.Tuples的从给定下标和给定长度分值区间值
        typedTupleSet.clear();
        typedTupleSet =redisTemplate.opsForZSet().reverseRangeByScoreWithScores("zSetValue",1,5,1,2);
        iterator = typedTupleSet.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Object value = typedTuple.getValue();
            double score1 = typedTuple.getScore();
            System.out.println("通过reverseRangeByScoreWithScores(K key, double min, double max, long offset, long count)方法倒序排序获取RedisZSetCommands.Tuples的从给定下标和给定长度区间值:" + value + "---->" + score1 );
        }

        //索引倒序排列区间值
        typedTupleSet.clear();
        typedTupleSet = redisTemplate.opsForZSet().reverseRangeWithScores("zSetValue",1,5);
        iterator = typedTupleSet.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Object value = typedTuple.getValue();
            double score1 = typedTuple.getScore();
            System.out.println("通过reverseRangeWithScores(K key, long start, long end)方法索引倒序排列区间值:" + value + "----->" + score1);
        }

        //获取倒序排列的索引值
        long reverseRank = redisTemplate.opsForZSet().reverseRank("zSetValue","B");
        System.out.println("通过reverseRank(K key, Object o)获取倒序排列的索引值:" + reverseRank);

        //获取2个变量的交集存放到第3个变量里面
        redisTemplate.opsForZSet().intersectAndStore("zSetValue","typedTupleSet","intersectSet");
        zSetValue = redisTemplate.opsForZSet().range("intersectSet",0,-1);
        System.out.println("通过intersectAndStore(K key, K otherKey, K destKey)方法获取2个变量的交集存放到第3个变量里面:" + zSetValue);

        //获取多个变量的交集存放到第3个变量里面
        List list = new ArrayList();
        list.add("typedTupleSet");
        redisTemplate.opsForZSet().intersectAndStore("zSetValue",list,"intersectListSet");
        zSetValue = redisTemplate.opsForZSet().range("intersectListSet",0,-1);
        System.out.println("通过intersectAndStore(K key, Collection<K> otherKeys, K destKey)方法获取多个变量的交集存放到第3个变量里面:" + zSetValue);

        //获取2个变量的合集存放到第3个变量里面
        redisTemplate.opsForZSet().unionAndStore("zSetValue","typedTupleSet","unionSet");
        zSetValue = redisTemplate.opsForZSet().range("unionSet",0,-1);
        System.out.println("通过unionAndStore(K key, K otherKey, K destKey)方法获取2个变量的交集存放到第3个变量里面:" + zSetValue);

        //获取多个变量的合集存放到第3个变量里面
        redisTemplate.opsForZSet().unionAndStore("zSetValue",list,"unionListSet");
        zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1);
        System.out.println("通过unionAndStore(K key, Collection<K> otherKeys, K destKey)方法获取多个变量的交集存放到第3个变量里面:" + zSetValue);

        //移除元素根据元素值
        long removeCount = redisTemplate.opsForZSet().remove("unionListSet","A","B");
        zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1);
        System.out.print("通过remove(K key, Object... values)方法移除元素的个数:" + removeCount);
        System.out.println(",移除后剩余的元素:" + zSetValue);

        //根据索引值移除元素
        removeCount = redisTemplate.opsForZSet().removeRange("unionListSet",3,5);
        zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1);
        System.out.print("通过removeRange(K key, long start, long end)方法移除元素的个数:" + removeCount);
        System.out.println(",移除后剩余的元素:" + zSetValue);

        //根据分值移除元素
        removeCount = redisTemplate.opsForZSet().removeRangeByScore("unionListSet",3,5);
        zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1);
        System.out.print("通过removeRangeByScore(K key, double min, double max)方法移除元素的个数:" + removeCount);
        System.out.println(",移除后剩余的元素:" + zSetValue);
    }

}
