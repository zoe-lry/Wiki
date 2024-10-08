package com.zoe.wiki.controller;


import com.alibaba.fastjson.JSONObject;
import com.zoe.wiki.req.UserLoginReq;
import com.zoe.wiki.req.UserQueryReq;
import com.zoe.wiki.req.UserResetPasswordReq;
import com.zoe.wiki.req.UserSaveReq;
import com.zoe.wiki.resp.CommonResp;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.resp.UserLoginResp;
import com.zoe.wiki.resp.UserQueryResp;
import com.zoe.wiki.service.UserService;
import com.zoe.wiki.util.SnowFlake;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回一个字符串
@RequestMapping("/user")
public class UserController {

  @Resource
  private UserService userService;
  @Resource
  private SnowFlake snowFlake;
  @Resource
  private RedisTemplate redisTemplate;
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/list")
  public CommonResp list(@Valid UserQueryReq req) {
    CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
    PageResp<UserQueryResp> list = userService.list(req);
    resp.setContent(list);
    return resp;
  }

  @PostMapping("/save")
  public CommonResp save(@Valid @RequestBody UserSaveReq req) {
    // 给密码加密
    req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
    CommonResp resp = new CommonResp<>();
    userService.save(req);
    return resp;
  }

  @DeleteMapping("/delete/{id}")
  public CommonResp delete(@PathVariable Long id) {
    CommonResp resp = new CommonResp<>();
    userService.delete(id);
    return resp;
  }

  @PostMapping("/reset-password")
  public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req) {
    // 给密码加密
    req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
    CommonResp<Object> resp = new CommonResp<>();
    userService.resetPassword(req);
    return resp;
  }
  // 登录
  @PostMapping("/login")
  public CommonResp login(@Valid @RequestBody UserLoginReq req) {
    // 给密码加密
    req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
    CommonResp<UserLoginResp> resp = new CommonResp<>();
    UserLoginResp userLoginResp = userService.login(req);

    Long token = snowFlake.nextId();
    LOG.info("生成单点登录token：{}，并放入redis中", token);
    userLoginResp.setToken(token.toString());
    redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600*24, TimeUnit.SECONDS);

    resp.setContent(userLoginResp);
    return resp;
  }

  // 退出登录
  @GetMapping("/logout/{token}")
  public CommonResp logout(@PathVariable String token) {
    CommonResp resp = new CommonResp<>();
    redisTemplate.delete(token);
    LOG.info("从redis中删除token: {}", token);
    return resp;
  }
}
