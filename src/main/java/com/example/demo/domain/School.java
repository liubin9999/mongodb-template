package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学校
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {

    /**
     * 学校ID
     */
    private long schoolId;

    /**
     * 学校名称
     */
    private String schoolName;


    /**
     * 入学年份
     */
    private int year;

}
