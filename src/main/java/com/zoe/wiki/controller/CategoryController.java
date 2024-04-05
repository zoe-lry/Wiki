package com.zoe.wiki.controller;


import com.zoe.wiki.req.CategoryQueryReq;
import com.zoe.wiki.req.CategorySaveReq;
import com.zoe.wiki.resp.CommonResp;
import com.zoe.wiki.resp.CategoryQueryResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.service.CategoryService;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
@RequestMapping("/category")
public class CategoryController {

  @Resource
  private CategoryService categoryService;

  @GetMapping("/list")
  public CommonResp list(@Valid CategoryQueryReq req) {
    CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
    PageResp<CategoryQueryResp> list = categoryService.list(req);
    resp.setContent(list);
    return resp;
  }

  @PostMapping("/save")
  public CommonResp save(@Valid @RequestBody CategorySaveReq req) {
    CommonResp resp = new CommonResp<>();
    categoryService.save(req);
    return resp;
  }

  @DeleteMapping("/delete/{id}")
  public CommonResp delete(@PathVariable Long id) {
    CommonResp resp = new CommonResp<>();
    categoryService.delete(id);
    return resp;
  }
}
