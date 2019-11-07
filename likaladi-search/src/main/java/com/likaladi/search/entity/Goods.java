package com.likaladi.search.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.*;

/**
 * @author likaladi
 *   String类型，又分两种：
 *      - text：可分词，不可参与聚合
 *      - keyword：不可分词，数据会作为完整字段进行匹配，可以参与聚合
 *
 *  index影响字段的索引情况。
 *      - true：字段会被索引，则可以用来进行搜索。默认值就是true
 *      - false：字段不会被索引，不能用来搜索
 *
 */
@Data
@Builder
@Document(indexName = "goods", type = "docs")
public class Goods {

    /**
     * spuId
     */
    @Id
    private Long id;

    /**
     * 所有需要被搜索的信息，包含标题，分类，甚至品牌
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    /**
     * 卖点：字段不会被索引
     */
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 1级分类id
     */
    private Long cid1;

    /**
     * 2级分类id
     */
    private Long cid2;

    /**
     * 3级分类id
     */
    private Long cid3;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 价格数组，是所有sku的价格集合
     */
    private List<Double> price;

    /**
     * 用于页面展示的sku信息，不索引，不搜索。包含skuId、image、price、title字段
     */
    @Field(type = FieldType.Keyword, index = false)
    private String skus;

    /**
     * 可搜索的规格属性集合：key是参数名，值是参数值，如下：
     *  {
     *    "specs":{
     *            "13":[4G,6G],
     *           "14":"红色"
     *           }
     *   }
     *   当存储到索引库时，elasticsearch会处理为两个字段：
     *
     *   - specs.13：[4G,6G]
     *   - specs.14：红色
     */
    private Map<String, Object> specs;


}
