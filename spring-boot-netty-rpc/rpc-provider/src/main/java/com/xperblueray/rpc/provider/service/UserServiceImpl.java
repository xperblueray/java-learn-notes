package com.xperblueray.rpc.provider.service;

import com.xperblueray.rpc.api.IUserService;
import com.xperblueray.rpc.pojo.User;
import com.xperblueray.rpc.provider.annotation.RpcService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RpcService
@Service
public class UserServiceImpl implements IUserService {
    Map<Object, User> userMap = new HashMap<>();
    @Override
    public User getUserById(int id) {
        if (userMap.size() == 0) {
            User user1 = new User();
            user1.setId(1);
            user1.setName("法外狂徒");
            userMap.put(user1.getId(), user1);
            User user2 = new User();
            user2.setId(2);
            user2.setName("天外谪仙");
            userMap.put(user2.getId(), user2);
        }
        return userMap.get(id);
    }
}
