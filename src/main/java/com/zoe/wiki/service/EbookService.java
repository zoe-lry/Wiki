package com.zoe.wiki.service;

import com.zoe.wiki.domain.Ebook;
import com.zoe.wiki.mapper.EbookMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service  //识别是Service
public class EbookService {
  @Resource  //把ebookMapper注入进来
  private EbookMapper ebookMapper;

  public List<Ebook> list() {
    return ebookMapper.selectByExample(null);
  }

}
