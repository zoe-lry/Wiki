package com.zoe.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoe.wiki.domain.User;
import com.zoe.wiki.domain.UserExample;
import com.zoe.wiki.exception.BusinessException;
import com.zoe.wiki.exception.BusinessExceptionCode;
import com.zoe.wiki.mapper.UserMapper;
import com.zoe.wiki.req.UserQueryReq;
import com.zoe.wiki.req.UserSaveReq;
import com.zoe.wiki.resp.PageResp;
import com.zoe.wiki.resp.UserQueryResp;
import com.zoe.wiki.util.CopyUtil;
import com.zoe.wiki.util.SnowFlake;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Resource
  private UserMapper userMapper;

  @Resource
  private SnowFlake snowFlake;

  public PageResp<UserQueryResp> list(UserQueryReq req) {
    UserExample userExample = new UserExample();
    UserExample.Criteria criteria = userExample.createCriteria();
    if (!ObjectUtils.isEmpty(req.getLoginName())) {
      criteria.andLoginNameEqualTo(req.getLoginName());
    }
    PageHelper.startPage(req.getPage(), req.getSize());
    List<User> userList = userMapper.selectByExample(userExample);

    PageInfo<User> pageInfo = new PageInfo<>(userList);
    LOG.info("总行数：{}", pageInfo.getTotal());
    LOG.info("总页数：{}", pageInfo.getPages());

    // List<UserResp> respList = new ArrayList<>();
    // for (User user : userList) {
    //     // UserResp userResp = new UserResp();
    //     // BeanUtils.copyProperties(user, userResp);
    //     // 对象复制
    //     UserResp userResp = CopyUtil.copy(user, UserResp.class);
    //
    //     respList.add(userResp);
    // }

    // 列表复制
    List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);

    PageResp<UserQueryResp> pageResp = new PageResp();
    pageResp.setTotal(pageInfo.getTotal());
    pageResp.setList(list);

    return pageResp;
  }

  /**
   * 保存
   */
  public void save(UserSaveReq req) {
    User user = CopyUtil.copy(req, User.class);

    if (ObjectUtils.isEmpty(req.getId())) {
      if (ObjectUtils.isEmpty(selectByLoginName(user.getLoginName()))) {
        // 新增
        user.setId(snowFlake.nextId());
        userMapper.insert(user);
      } else {
        // 用户名已存在，抛出异常
        throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);

      }

    } else {
      // 更新
      user.setLoginName(null); // 不允许修改login name
      user.setPassword(null); // 不允许修改password
      userMapper.updateByPrimaryKeySelective(user);
    }
  }

  public User selectByLoginName(String loginName) {
    UserExample userExample = new UserExample();
    UserExample.Criteria criteria = userExample.createCriteria();
    criteria.andLoginNameEqualTo(loginName);
    List<User> userList = userMapper.selectByExample(userExample);
    if (CollectionUtils.isEmpty(userList)) {
      return null;
    } else {
      return userList.get(0);
    }
  }

  public void delete(Long id) {
    userMapper.deleteByPrimaryKey(id);
  }
}
