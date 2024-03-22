package com.zoe.wiki.controller;


import com.zoe.wiki.domain.Test;
import com.zoe.wiki.service.TestService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
public class TestController {

  //增加一个自定义的配置项示例
  //可以添加默认值， 如果没有配置会采用默认值
  @Value("${test.hello: TEST}")
  private String testHello;

  @Resource
  private TestService testService;

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
}
