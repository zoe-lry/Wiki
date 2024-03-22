package com.zoe.wiki.service;

import com.zoe.wiki.domain.Test;
import com.zoe.wiki.mapper.TestMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service  //识别是Service
public class TestService {
  @Resource  //把testMapper注入进来
  private TestMapper testMapper;

  public List<Test> list() {
    return testMapper.list();
  }

}
