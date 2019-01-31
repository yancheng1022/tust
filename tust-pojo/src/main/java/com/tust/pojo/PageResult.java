package com.tust.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/8 16:31
 * @Description: 分页结果类
 */
@Data
public class PageResult implements Serializable {

    private long total;  //总记录数
    private List rows; //当前页记录



}
