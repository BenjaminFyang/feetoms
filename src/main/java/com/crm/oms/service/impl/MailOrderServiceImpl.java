package com.crm.oms.service.impl;

import com.crm.oms.model.MailOrder;
import com.crm.oms.mapper.MailOrderMapper;
import com.crm.oms.service.MailOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件订单信息表 服务实现类
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
@Service
public class MailOrderServiceImpl extends ServiceImpl<MailOrderMapper, MailOrder> implements MailOrderService {

}
