package com.zoe.wiki.controller;


import com.zoe.wiki.req.EbookQueryReq;
import com.zoe.wiki.req.EbookSaveReq;
import com.zoe.wiki.resp.CommonResp;
import com.zoe.wiki.resp.EbookQueryResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.service.EbookService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
@RequestMapping("/ebook")
public class EbookController {

  @Resource
  private EbookService ebookService;

  @GetMapping("/list")
  public CommonResp list(EbookQueryReq req) {
    CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
    PageResp<EbookQueryResp> list = ebookService.list(req);
    resp.setContent(list);
    return resp;
  }

  @PostMapping("/save")
  public CommonResp save(@RequestBody EbookSaveReq req) {
    CommonResp resp = new CommonResp<>();
    ebookService.save(req);
    return resp;
  }

  @DeleteMapping("/delete/{id}")
  public CommonResp delete(@PathVariable Long id) {
    CommonResp resp = new CommonResp<>();
    ebookService.delete(id);
    return resp;
  }
}
