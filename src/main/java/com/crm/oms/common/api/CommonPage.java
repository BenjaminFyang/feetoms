package com.crm.oms.common.api;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页数据封装类
 */

@Data
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将MyBatis Plus 分页结果转化为通用结果
     */
    public static <T> CommonPage<T> restPage(Page<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(Convert.toInt(pageResult.getCurrent()));
        result.setPageSize(Convert.toInt(pageResult.getSize()));
        result.setTotal(pageResult.getTotal());
        result.setTotalPage(Convert.toInt(pageResult.getTotal() / pageResult.getSize() + 1));
        result.setList(pageResult.getRecords());
        return result;
    }

}
