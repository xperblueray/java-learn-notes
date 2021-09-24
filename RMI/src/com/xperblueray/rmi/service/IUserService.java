package com.xperblueray.rmi.service;

import com.xperblueray.rmi.pojo.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserService extends Remote {
    User getUserById(int id) throws RemoteException;
}
