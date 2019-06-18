package com.gzr.springbootredis;

import com.gzr.springbootredis.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class SpringbootRedisApplicationTests implements Serializable{

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("gzr", "123456789");
        log.info("value:{}", valueOperations.get("gzr"));

        User user1 = new User();
        user1.setName("gzr1");

        User user2 = new User();
        user2.setName("gzr2");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        redisTemplate.opsForList().leftPushAll("newList1", userList);

        List<User> userList122 = redisTemplate.opsForList().range("newList1", 0, 10);
        System.out.println(userList122);


        User userTest = new User();
        userTest.setName("testName");
        redisTemplate.opsForValue().set("testName", userTest);
        User newTest = (User) redisTemplate.opsForValue().get("testName");
        System.out.println(userList122);

    }

    @Test
    public void testIncr() {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong("test", redisTemplate.getConnectionFactory());
        Long increment = redisAtomicLong.getAndIncrement();
        increment = redisAtomicLong.getAndIncrement();
        increment = redisAtomicLong.getAndIncrement();
    }

}
