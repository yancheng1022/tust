package com.tust.pojo;

import lombok.Data;

import java.io.Serializable;
/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/8 19:51
 * @Description: 执行结果
 */
@Data
public class Result implements Serializable {
    private boolean success; //是否成功
    private String message;//返回信息

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
