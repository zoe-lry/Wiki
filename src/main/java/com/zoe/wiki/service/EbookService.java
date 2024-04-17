package com.zoe.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoe.wiki.domain.Ebook;
import com.zoe.wiki.domain.EbookExample;
import com.zoe.wiki.mapper.EbookMapper;
import com.zoe.wiki.req.EbookQueryReq;
import com.zoe.wiki.req.EbookSaveReq;
import com.zoe.wiki.resp.EbookQueryResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.util.CopyUtil;
import com.zoe.wiki.util.SnowFlake;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class EbookService {

  private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

  @Resource
  private EbookMapper ebookMapper;

  @Resource
  private SnowFlake snowFlake;

  public PageResp<EbookQueryResp> list(EbookQueryReq req) {
    EbookExample ebookExample = new EbookExample();
    EbookExample.Criteria criteria = ebookExample.createCriteria();
    if (!ObjectUtils.isEmpty(req.getName())) {
      criteria.andNameLike("%" + req.getName() + "%");
    }
    if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
      criteria.andCategory2IdEqualTo(req.getCategoryId2());
    }
    PageHelper.startPage(req.getPage(), req.getSize());
    List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

    PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
    LOG.info("总行数：{}", pageInfo.getTotal());
    LOG.info("总页数：{}", pageInfo.getPages());

    // List<EbookResp> respList = new ArrayList<>();
    // for (Ebook ebook : ebookList) {
    //     // EbookResp ebookResp = new EbookResp();
    //     // BeanUtils.copyProperties(ebook, ebookResp);
    //     // 对象复制
    //     EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
    //
    //     respList.add(ebookResp);
    // }

    // 列表复制
    List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

    PageResp<EbookQueryResp> pageResp = new PageResp();
    pageResp.setTotal(pageInfo.getTotal());
    pageResp.setList(list);

    return pageResp;
  }

  /**
   * 保存
   */
  public void save(EbookSaveReq req) {
    Ebook ebook = CopyUtil.copy(req, Ebook.class);
    if (ObjectUtils.isEmpty(req.getId())) {
      // 新增
      ebook.setId(snowFlake.nextId());
      ebookMapper.insert(ebook);
    } else {
      // 更新
      ebookMapper.updateByPrimaryKey(ebook);
    }
  }

  public void delete(Long id) {
    ebookMapper.deleteByPrimaryKey(id);
  }
}
