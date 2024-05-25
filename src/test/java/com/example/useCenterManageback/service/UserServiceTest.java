package com.example.useCenterManageback.service;

import com.example.useCenterManageback.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

@Test
    public void testAddUser(){
        User user =new User();
        user.setUsername("ljp");
        user.setUserAccount("123");
        user.setAvatarUrl("https://picsum.photos/id/237/200/300");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);





}


}