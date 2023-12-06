package com.chenmeng.project.model.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 测试4，实体类
 *
 * @author 沉梦听雨
 * @date 2023/11/14 17:18
 **/
@Data
public class Test4 {

    private Integer id;

    private LinkedHashMap<String, List<String>> map;

    private Integer count;



}
