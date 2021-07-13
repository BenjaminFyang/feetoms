package com.crm.oms.service;

import com.crm.oms.dto.MailOrderParam;
import com.crm.oms.model.MailOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 邮件订单信息表 服务类
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
public interface MailOrderService extends IService<MailOrder> {




    List<MailOrder> list(MailOrderParam mailOrderParam, Integer pageSize, Integer pageNum);


}
