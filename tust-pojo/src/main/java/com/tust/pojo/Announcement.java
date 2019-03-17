package com.tust.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Announcement implements Serializable {
    private Integer id;
    private String announcement;
}