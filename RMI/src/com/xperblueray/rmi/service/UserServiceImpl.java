package com.xperblueray.rmi.service;

import com.xperblueray.rmi.pojo.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl extends UnicastRemoteObject implements IUserService {
    Map<Object, User> userMap = new HashMap<>();
    public UserServiceImpl() throws RemoteException {
        super();
        User user1 = new User();
        user1.setId(1);
        user1.setName("法外狂徒");
        User user2 = new User();
        user2.setId(2);
        user2.setName("天外谪仙");
        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }

    public User getUserById(int id) throws RemoteException {
        return userMap.get(id);
    }
}
