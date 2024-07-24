package com.zoe.wiki.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoe.wiki.domain.Content;
import com.zoe.wiki.domain.Doc;
import com.zoe.wiki.domain.DocExample;
import com.zoe.wiki.exception.BusinessException;
import com.zoe.wiki.exception.BusinessExceptionCode;
import com.zoe.wiki.mapper.ContentMapper;
import com.zoe.wiki.mapper.DocMapper;
import com.zoe.wiki.mapper.DocMapperCust;
import com.zoe.wiki.req.DocQueryReq;
import com.zoe.wiki.req.DocSaveReq;
import com.zoe.wiki.resp.DocQueryResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.util.CopyUtil;
import com.zoe.wiki.util.RedisUtil;
import com.zoe.wiki.util.RequestContext;
import com.zoe.wiki.util.SnowFlake;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service  //识别是Service
public class DocService {

  private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

  @Resource  //把docMapper注入进来
  private DocMapper docMapper;
  @Resource  //把docMapperCust注入进来
  private DocMapperCust docMapperCust;
  @Resource  //把contentMapper注入进来
  private ContentMapper contentMapper;
  @Resource  //把SnowFlake注入进来
  private SnowFlake snowFlake;
  @Resource
  public RedisUtil redisUtil;

  public List<DocQueryResp> all(Long ebookId) {
    DocExample docExample = new DocExample();
    docExample.createCriteria().andEbookIdEqualTo(ebookId);
    docExample.setOrderByClause("sort asc");
    List<Doc> docList = docMapper.selectByExample(docExample);

    // 列表复制
    List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

    return list;
  }

  public PageResp<DocQueryResp> list(DocQueryReq req) {
    DocExample docExample = new DocExample();
    docExample.setOrderByClause("sort asc");
    DocExample.Criteria criteria = docExample.createCriteria();
    PageHelper.startPage(req.getPage(), req.getSize());
    List<Doc> docList = docMapper.selectByExample(docExample);

    PageInfo<Doc> pageInfo = new PageInfo<>(docList);
    LOG.info("总行数：{}", pageInfo.getTotal());
    LOG.info("总页数：{}", pageInfo.getPages());

    // List<DocResp> respList = new ArrayList<>();
    // for (Doc doc : docList) {
    //     // DocResp docResp = new DocResp();
    //     // BeanUtils.copyProperties(doc, docResp);
    //     // 对象复制
    //     DocResp docResp = CopyUtil.copy(doc, DocResp.class);
    //
    //     respList.add(docResp);
    // }

    // 列表复制
    List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

    PageResp<DocQueryResp> pageResp = new PageResp();
    pageResp.setTotal(pageInfo.getTotal());
    pageResp.setList(list);

    return pageResp;
  }

  /**
   * 保存
   */
  public void save(DocSaveReq req) {
    Doc doc = CopyUtil.copy(req, Doc.class);
    Content content = CopyUtil.copy(req, Content.class);
    if (isEmpty(req.getId())) {
      // 新增
      doc.setId(snowFlake.nextId());
      doc.setViewCount(0);
      doc.setVoteCount(0);
      docMapper.insert(doc);

      content.setId(doc.getId());
      contentMapper.insert(content);
    } else {
      // 更新
      docMapper.updateByPrimaryKey(doc);
      int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
      if (count == 0) {
        contentMapper.insert(content);
      }
    }
  }

  /**
   * 删除
   */
  public void delete(Long id) {
    docMapper.deleteByPrimaryKey(id);
  }

  public void delete(List<String> ids) {
    DocExample docExample = new DocExample();
    DocExample.Criteria criteria = docExample.createCriteria();
    criteria.andIdIn(ids);
    docMapper.deleteByExample(docExample);
  }

  public String findContent(Long id) {
    Content content = contentMapper.selectByPrimaryKey(id);
    docMapperCust.increaseViewCount(id);
    System.out.println(content);
    if (isEmpty(content)) {
      return "";
    } else {
      return content.getContent();
    }
  }

  /**
   * 点赞
   */
  public void vote(Long id) {
//    docMapperCust.increaseVoteCount(id);
    // 远程IP+doc.id作为key，24小时内不能重复
    String ip = RequestContext.getRemoteAddr();
    if (redisUtil.validateRepeat("DOC_VOTE_" + id + "_" + ip, 3600 * 24)) {
      docMapperCust.increaseVoteCount(id);
    } else {
      throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
    }
  }

}