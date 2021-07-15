package com.crm.oms.service;

import com.crm.oms.enums.MailOrderRecordEnum;
import com.crm.oms.model.MailOrderRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 邮件订单操作记录信息表 服务类
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
public interface MailOrderRecordService extends IService<MailOrderRecord> {


    void insert(Long mailOrderId, MailOrderRecordEnum mailOrderRecordEnum);

}
