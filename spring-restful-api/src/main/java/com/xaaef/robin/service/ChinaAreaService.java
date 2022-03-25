package com.xaaef.robin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xaaef.robin.entity.ChinaArea;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xaaef.robin.param.QueryParams;

import java.util.List;

/**
 * @author WangChenChen
 * @description 针对表【cn_area([ 通用 ] 中国行政地区表)】的数据库操作Service
 * @createDate 2022-03-22 09:59:32
 */

public interface ChinaAreaService extends IService<ChinaArea> {

    /**
     * 分页查询
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    IPage<ChinaArea> pageKeywords(QueryParams params);


    /**
     * 根据 条件 查询全部
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    List<ChinaArea> list(SFunction<ChinaArea, ?> column, Object value);


}
