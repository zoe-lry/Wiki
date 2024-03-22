package com.zoe.wiki.service;

import com.zoe.wiki.domain.Ebook;
import com.zoe.wiki.domain.EbookExample;
import com.zoe.wiki.mapper.EbookMapper;
import com.zoe.wiki.req.EbookReq;
import com.zoe.wiki.resp.EbookResp;
import com.zoe.wiki.util.CopyUtil;
import java.util.List;
import javax.annotation.Resource;
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

//    List<EbookResp> respList = new ArrayList<>();
//    for (Ebook ebook : ebooksList) {

////      EbookResp ebookResp = new EbookResp();
////      BeanUtils.copyProperties(ebook, ebookResp);
// //      //对象复制
//      EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//      respList.add(ebookResp);
//    }

//    列表复制
    List<EbookResp> respList = CopyUtil.copyList(ebooksList, EbookResp.class);
    return respList;
  }

}
