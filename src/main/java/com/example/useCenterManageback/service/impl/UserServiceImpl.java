package com.example.useCenterManageback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.useCenterManageback.model.domain.User;
import com.example.useCenterManageback.service.UserService;
import com.example.useCenterManageback.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 28044
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-05-24 15:23:03
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




