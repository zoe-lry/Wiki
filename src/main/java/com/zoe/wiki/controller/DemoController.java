package com.zoe.wiki.controller;


import com.zoe.wiki.domain.Demo;
import com.zoe.wiki.service.DemoService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
@RequestMapping("/demo")
public class DemoController {

  @Resource
  private DemoService demoService;

  @GetMapping("/list")
  public List<Demo> list() {
    return demoService.list();
  }
}
