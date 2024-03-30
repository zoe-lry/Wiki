package com.zoe.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoe.wiki.domain.Ebook;
import com.zoe.wiki.domain.EbookExample;
import com.zoe.wiki.mapper.EbookMapper;
import com.zoe.wiki.req.EbookReq;
import com.zoe.wiki.resp.EbookResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.util.CopyUtil;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service  //识别是Service
public class EbookService {
  private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

  @Resource  //把ebookMapper注入进来
  private EbookMapper ebookMapper;

  public PageResp<EbookResp> list(EbookReq req) {
    EbookExample ebookExample = new EbookExample();
    EbookExample.Criteria criteria = ebookExample.createCriteria();
    if (!ObjectUtils.isEmpty(req.getName())) {
      criteria.andNameLike("%" + req.getName() + "%");
    }
    PageHelper.startPage(req.getPage(),req.getSize());
    List<Ebook> ebooksList = ebookMapper.selectByExample(ebookExample);

    PageInfo<Ebook> pageInfo = new PageInfo<>(ebooksList);
    LOG.info("总行数: {} ",(pageInfo.getTotal()));
    LOG.info("总页数: {} ",(pageInfo.getPages()));



//    List<EbookResp> respList = new ArrayList<>();
//    for (Ebook ebook : ebooksList) {

////      EbookResp ebookResp = new EbookResp();
////      BeanUtils.copyProperties(ebook, ebookResp);
// //      //对象复制
//      EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//      respList.add(ebookResp);
//    }

//    列表复制
    List<EbookResp> list = CopyUtil.copyList(ebooksList, EbookResp.class);

    PageResp<EbookResp> pageResp = new PageResp<>();
    pageResp.setTotal(pageInfo.getTotal());
    pageResp.setList(list);

    return pageResp;
  }

}
