package com.liwen.demo.user.service;

import com.liwen.demo.user.dao.UserMapper;
import com.liwen.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Integer id)  throws InterruptedException {
        // 为了演示超时现象，我们在这里然线程休眠,时间随机 0~2000毫秒
        //Thread.sleep(new Random().nextInt(2000));
        return this.userMapper.selectByPrimaryKey(id);
    }
}
