package com.crm.oms.service.impl;

import com.crm.oms.common.utils.TransmittableThreadLocalContext;
import com.crm.oms.enums.MailOrderRecordEnum;
import com.crm.oms.model.MailOrderRecord;
import com.crm.oms.mapper.MailOrderRecordMapper;
import com.crm.oms.model.UmsAdmin;
import com.crm.oms.service.MailOrderRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 邮件订单操作记录信息表 服务实现类
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
@Service
public class MailOrderRecordServiceImpl extends ServiceImpl<MailOrderRecordMapper, MailOrderRecord> implements MailOrderRecordService {

    @Resource
    private MailOrderRecordMapper mailOrderRecordMapper;

    @Override
    public void insert(Long mailOrderId, MailOrderRecordEnum mailOrderRecordEnum) {
        UmsAdmin umsAdmin = TransmittableThreadLocalContext.getAuthDataBo();
        MailOrderRecord mailOrderRecord = new MailOrderRecord(mailOrderId, mailOrderRecordEnum, umsAdmin);
        mailOrderRecordMapper.insert(mailOrderRecord);
    }
}
