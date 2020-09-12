package com.bz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true) //开启链式调用
public class User {

    private Integer id;
    private String userName;
    private String passWord;
}
