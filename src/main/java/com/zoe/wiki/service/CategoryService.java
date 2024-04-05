package com.zoe.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoe.wiki.domain.Category;
import com.zoe.wiki.domain.CategoryExample;
import com.zoe.wiki.mapper.CategoryMapper;
import com.zoe.wiki.req.CategoryQueryReq;
import com.zoe.wiki.req.CategorySaveReq;
import com.zoe.wiki.resp.CategoryQueryResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.util.CopyUtil;
import com.zoe.wiki.util.SnowFlake;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service  //识别是Service
public class CategoryService {
  private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

  @Resource  //把categoryMapper注入进来
  private CategoryMapper categoryMapper;
  @Resource  //把SnowFlake注入进来
  private SnowFlake snowFlake;

  public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
    CategoryExample categoryExample = new CategoryExample();
    CategoryExample.Criteria criteria = categoryExample.createCriteria();
    PageHelper.startPage(req.getPage(),req.getSize());
    List<Category> categorysList = categoryMapper.selectByExample(categoryExample);

    PageInfo<Category> pageInfo = new PageInfo<>(categorysList);
    LOG.info("总行数: {} ",(pageInfo.getTotal()));
    LOG.info("总页数: {} ",(pageInfo.getPages()));



//    List<CategoryResp> respList = new ArrayList<>();
//    for (Category category : categorysList) {

////      CategoryResp categoryResp = new CategoryResp();
////      BeanUtils.copyProperties(category, categoryResp);
// //      //对象复制
//      CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//      respList.add(categoryResp);
//    }

//    列表复制
    List<CategoryQueryResp> list = CopyUtil.copyList(categorysList, CategoryQueryResp.class);

    PageResp<CategoryQueryResp> pageResp = new PageResp<>();
    pageResp.setTotal(pageInfo.getTotal());
    pageResp.setList(list);

    return pageResp;
  }

  /**
   * 保存
   */
  public void save(CategorySaveReq req) {
    Category category = CopyUtil.copy(req, Category.class);
    if (ObjectUtils.isEmpty(req.getId())) {
      //新增
      category.setId(snowFlake.nextId());
      categoryMapper.insert(category);
    } else {
      //更新
      categoryMapper.updateByPrimaryKey(category);
    }
  }

  /**
   * 删除
   */
  public void delete(Long id) {
    categoryMapper.deleteByPrimaryKey(id);
  }

}
