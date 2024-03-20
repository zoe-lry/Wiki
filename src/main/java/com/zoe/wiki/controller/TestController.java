package com.zoe.wiki.controller;


import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
public class TestController {

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
    return "Hello World";
  }
  @PostMapping("/hello/post")
  public String helloPost(String name) {
    return "Hello World! Post " + name;
  }
}
