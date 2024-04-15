package com.zoe.wiki.controller;


import com.zoe.wiki.req.DocQueryReq;
import com.zoe.wiki.req.DocSaveReq;
import com.zoe.wiki.resp.CommonResp;
import com.zoe.wiki.resp.DocQueryResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.service.DocService;
import java.util.List;
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
@RequestMapping("/doc")
public class DocController {

  @Resource
  private DocService docService;

  @GetMapping("/all")
  public CommonResp all() {
    CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
    List<DocQueryResp> list = docService.all();
    resp.setContent(list);
    return resp;
  }
  @GetMapping("/list")
  public CommonResp list(@Valid DocQueryReq req) {
    CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
    PageResp<DocQueryResp> list = docService.list(req);
    resp.setContent(list);
    return resp;
  }

  @PostMapping("/save")
  public CommonResp save(@Valid @RequestBody DocSaveReq req) {
    CommonResp resp = new CommonResp<>();
    docService.save(req);
    return resp;
  }

  @DeleteMapping("/delete/{id}")
  public CommonResp delete(@PathVariable Long id) {
    CommonResp resp = new CommonResp<>();
    docService.delete(id);
    return resp;
  }
}
