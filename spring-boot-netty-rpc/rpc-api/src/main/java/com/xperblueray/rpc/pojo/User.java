package com.xperblueray.rpc.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    int id;
    String name;

}