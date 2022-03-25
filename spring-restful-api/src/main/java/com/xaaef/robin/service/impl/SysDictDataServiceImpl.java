package com.xaaef.robin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xaaef.robin.base.service.impl.BaseServiceImpl;
import com.xaaef.robin.entity.SysDictData;
import com.xaaef.robin.param.DictQueryParams;
import com.xaaef.robin.service.SysDictDataService;
import com.xaaef.robin.mapper.SysDictDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangChenChen
 * @description 针对表【sys_dict_data([ 通用 ] 字典数据表)】的数据库操作Service实现
 * @createDate 2022-03-22 09:59:32
 */


@Service
@AllArgsConstructor
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData>
        implements SysDictDataService {


    @Override
    public IPage<SysDictData> pageKeywords(DictQueryParams params) {
        Page<SysDictData> page = Page.of(params.getPageNum(), params.getPageSize());
        QueryWrapper<SysDictData> wrapper = getKeywordsQueryWrapper(params, null);
        wrapper.lambda().eq(SysDictData::getTypeKey, params.getDictTypeKey());
        return super.page(page, wrapper);
    }


    @Override
    public List<SysDictData> listByKey(String dictTypeKey) {
        var wrapper = new LambdaQueryWrapper<SysDictData>();
        wrapper.eq(SysDictData::getTypeKey, dictTypeKey);
        return super.list(wrapper);
    }


}




