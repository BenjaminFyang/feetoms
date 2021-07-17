package com.crm.oms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 邮件订单b标识信息表
 * </p>
 *
 * @author fangyang
 * @since 2021-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mail_order_uk_number")
@ApiModel(value="MailOrderUkNumber对象", description="邮件订单b标识信息表")
public class MailOrderUkNumber implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "邮件订单信息表主键id")
    private Long mailOrderId;

    @ApiModelProperty(value = "国外运单号")
    private String ukNumber;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
