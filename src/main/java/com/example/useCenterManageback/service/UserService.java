package com.example.useCenterManageback.service;

import com.example.useCenterManageback.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;

/**
* @author 28044
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-05-24 15:23:03
*/
public interface UserService extends IService<User> {
    /**
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword) throws NoSuchAlgorithmException;

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @return
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

}
