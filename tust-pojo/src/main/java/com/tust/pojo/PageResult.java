package com.tust.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/8 16:31
 * @Description: ��ҳ�����
 */
@Data
public class PageResult implements Serializable {

    private long total;  //�ܼ�¼��
    private List rows; //��ǰҳ��¼



}
