package com.ifsaid.shark.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * [ 通用 ]中国行政地区表
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 22:01
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Entity
@Table(name = "tb_cn_area")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChinaArea implements java.io.Serializable {

    /**
     * @description: 行政代码 [ 唯一 ]
     * @date: 2019/12/11 22:15
     */
    @Id
    private Long areaCode;

    /**
     * @description: 级别
     * @date: 2019/12/11 22:15
     */
    private Integer level;

    /**
     * @description: 父级行政代码
     * @date: 2019/12/11 22:15
     */
    private Long parentCode;

    /**
     * @description: 邮政编码
     * @date: 2019/12/11 22:15
     */
    private Integer zipCode;

    /**
     * @description: 区号
     * @date: 2019/12/11 22:15
     */
    private String cityCode;

    /**
     * @description: 名称
     * @date: 2019/12/11 22:15
     */
    private String name;

    /**
     * @description: 简称
     * @date: 2019/12/11 22:15
     */
    private String shortName;

    /**
     * @description: 组合名
     * @date: 2019/12/11 22:15
     */
    private String mergerName;

    /**
     * @description: 拼音
     * @date: 2019/12/11 22:15
     */
    private String pinyin;

    /**
     * @description: 经度
     * @date: 2019/12/11 22:15
     */
    private Double lng;

    /**
     * @description: 纬度
     * @date: 2019/12/11 22:15
     */
    private Double lat;

}
