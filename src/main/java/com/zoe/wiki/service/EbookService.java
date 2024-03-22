package com.zoe.wiki.service;

import com.zoe.wiki.domain.Ebook;
import com.zoe.wiki.domain.EbookExample;
import com.zoe.wiki.mapper.EbookMapper;
import com.zoe.wiki.req.EbookReq;
import com.zoe.wiki.resp.EbookResp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service  //识别是Service
public class EbookService {
  @Resource  //把ebookMapper注入进来
  private EbookMapper ebookMapper;

  public List<EbookResp> list(EbookReq req) {
    EbookExample ebookExample = new EbookExample();
    EbookExample.Criteria criteria = ebookExample.createCriteria();
    criteria.andNameLike("%" + req.getName() + "%");
    List<Ebook> ebooksList = ebookMapper.selectByExample(ebookExample);
    List<EbookResp> respList = new ArrayList<>();
    for (Ebook ebook : ebooksList) {
      EbookResp ebookResp = new EbookResp();
      BeanUtils.copyProperties(ebook, ebookResp);
      respList.add(ebookResp);
    }
    return respList;
  }

}
