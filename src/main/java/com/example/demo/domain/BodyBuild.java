package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 体型
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyBuild {

    /**
     * 宽
     */
    private long width;

    /**
     * 高度
     */
    private long height;

    /**
     * 血型
     */
    private String blood;
}
