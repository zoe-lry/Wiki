package com.zoe.wiki.controller;


import com.zoe.wiki.domain.Test;
import com.zoe.wiki.service.TestService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
public class TestController {

  private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

  //增加一个自定义的配置项示例
  //可以添加默认值， 如果没有配置会采用默认值
  @Value("${test.hello: TEST}")
  private String testHello;

  @Resource
  private TestService testService;
  @Resource
  private RedisTemplate redisTemplate;
  /**
   * HTTP请求总共有八种，常用的四种请求：GET, POST, PUT, DELETE
   *
   */
//   @RequestMapping 表示支持四种请求
//   @GetMapping
//   @PostMapping
//   @DeleteMapping
//   @PutMapping
//   @RequestMapping(value = "/user/1", method = RequestMethod.GET)
  @RequestMapping("/hello")
  public String hello() {
    return "Hello World " + testHello;
  }

  @PostMapping("/hello/post")
  public String helloPost(String name) {
    return "Hello World! Post " + name;
  }

  @GetMapping("/test/list")
  public List<Test> list() {
    return testService.list();
  }

  @RequestMapping("/redis/set/{key}/{value}")
  public String set(@PathVariable Long key, @PathVariable String value) {
    redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
    LOG.info("key: {}, value: {}", key, value);
    return "success";
  }

  @RequestMapping("/redis/get/{key}")
  public Object get(@PathVariable Long key) {
    Object object = redisTemplate.opsForValue().get(key);
    LOG.info("key: {}, value: {}", key, object);
    return object;
  }
}
