package com.example.useCenterManageback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.useCenterManageback.model.domain.User;
import com.example.useCenterManageback.service.UserService;
import com.example.useCenterManageback.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.activation.registries.LogSupport.log;

/**
 * @author 28044
 * @description 用户服务实现类
 * @createDate 2024-05-24 15:23:03
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    /**
     * 盐值，混淆密码
     */
    private final static String SALT = "piao";
    /**
     * 用户登录状态
     */
    private final static String USER_LOGIN_STATE = "userLoginState";

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) throws NoSuchAlgorithmException {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        //账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //密码重复性校验
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        //根据userAccount获取对象 这里查询了数据库   来验证账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //2.密码加密
        String passwordMD5 = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(passwordMD5);
        boolean saveResult = this.save(user);//用userMapper.insert也行

        return !saveResult ? -1 : user.getId();


    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            log("用户名或密码为空");
            return null;
        }
        if (userAccount.length() < 4) {
            log("账户长度小于4");
            return null;
        }
        if (userPassword.length() < 8) {
            log("密码小于八位");
            return null;
        }
        //账户不能包含特殊字符
        String validPattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            log("账户有特殊字符");
            return null;
        }

        //2.查询用户是否存在
        String passwordMD5 = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", passwordMD5);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log("user login fail");
            return null;
        }
        //3.用户脱敏
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserStatus(0);
        safetyUser.setCreateTime(user.getCreateTime());
        //4.记录用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        //5.返回脱敏后用户信息
        return safetyUser;
    }
}




