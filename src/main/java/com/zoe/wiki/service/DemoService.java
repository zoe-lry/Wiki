package com.zoe.wiki.service;

import com.zoe.wiki.domain.Demo;
import com.zoe.wiki.mapper.DemoMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service  //识别是Service
public class DemoService {
  @Resource  //把demoMapper注入进来
  private DemoMapper demoMapper;

  public List<Demo> list() {
    return demoMapper.selectByExample(null);
  }

}
