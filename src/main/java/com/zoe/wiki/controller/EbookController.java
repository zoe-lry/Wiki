package com.zoe.wiki.controller;


import com.zoe.wiki.domain.Ebook;
import com.zoe.wiki.resp.CommonResp;
import com.zoe.wiki.service.EbookService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
@RequestMapping("/ebook")
public class EbookController {

  @Resource
  private EbookService ebookService;

  @GetMapping("/list")
  public CommonResp list() {
    CommonResp<List<Ebook>> resp = new CommonResp<>();
    List<Ebook> list = ebookService.list();
    resp.setContent(list);
    return resp;
  }
}
