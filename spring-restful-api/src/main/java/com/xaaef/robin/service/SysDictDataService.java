package com.xaaef.robin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xaaef.robin.base.service.BaseService;
import com.xaaef.robin.entity.SysDictData;
import com.xaaef.robin.param.DictQueryParams;

import java.util.List;

/**
 * @author WangChenChen
 * @description 针对表【sys_dict_data([ 通用 ] 字典数据表)】的数据库操作Service
 * @createDate 2022-03-22 09:59:32
 */

public interface SysDictDataService extends BaseService<SysDictData> {

    /**
     * 分页查询
     *
     * @author WangChenChen
     * @date 2022/3/22 11:09
     */
    IPage<SysDictData> pageKeywords(DictQueryParams params);


    /**
     * 根据关键字查询
     *
     * @author Wang Chen Chen
     * @date 2021/8/24 18:24
     */
    List<SysDictData> listByKey(String dictTypeKey);


}
