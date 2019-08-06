package com.liwen.demo.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Table(name = "tb_promo")
public class Promo {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 秒杀活动名称
     */
    @Column(name = "promo_name")
    private String promoName;

    /**
     * 活动开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 活动结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 活动商品价格
     */
    @Column(name = "promo_item_price")
    private Double promoItemPrice;
}