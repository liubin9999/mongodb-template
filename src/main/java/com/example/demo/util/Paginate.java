package com.example.demo.util;

import lombok.Data;

/**
 * 分页
 *
 * @author orange
 * @Time 2017/11/28 0028
 */
@Data
public class Paginate {

    /**
     * 当前页数
     */

    private int page;

    /**
     * 每页条数
     */
    private int size;

    /**
     * 总条数
     */
    private long totalNumber;

    /**
     * 总页数
     */
    private int totalPageNumber;

    /**
     * 查询时间
     */
    private long time;

    /**
     * 计算总页数
     */
    public void calcTotalPageNumber() {
        if (this.totalNumber <= 0 || size <= 0) {
            return;
        }

        this.totalPageNumber = (int) this.totalNumber / size;
        if (this.totalNumber % size > 0) {
            this.totalPageNumber++;
        }
    }

    /**
     * 起始条数查询
     *
     * @return
     */
    public int caclStartNum(){
        return this.size * this.page;
    }

}
