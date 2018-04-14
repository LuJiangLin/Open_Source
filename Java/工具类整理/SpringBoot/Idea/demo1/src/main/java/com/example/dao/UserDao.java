package com.example.dao;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Flower on 2017/5/12.
 */
@Repository
public class UserDao {
    @Autowired
    private UserMapper userMapper;

    public String GetTestword(){
        return userMapper.GetTestword();
    }

}
