package com.crm.oms.mapper;

import com.crm.oms.dto.MailOrderParam;
import com.crm.oms.model.MailOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 邮件订单信息表 Mapper 接口
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
public interface MailOrderMapper extends BaseMapper<MailOrder> {

//
//    List<MailOrder> list(@Param("mailOrderParam") MailOrderParam mailOrderParam,
//                         @Param("pageSize") Integer pageSize,
//                         @Param("pageNum") Integer pageNum);

}
