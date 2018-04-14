package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Flower on 2017/5/12.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

   public String GetTestword()
    {
        return userDao.GetTestword();
    }
}
