package com.crm.oms.service;

import com.crm.oms.dto.MailManagementEditParam;
import com.crm.oms.dto.MailManagementParam;
import com.crm.oms.model.MailManagement;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 账号管理同步表 服务类
 * </p>
 *
 * @author macro
 * @since 2021-07-12
 */
public interface MailManagementService extends IService<MailManagement> {

    List<MailManagement> list(Integer pageSize, Integer pageNum);

    void insert(MailManagementParam mailManagementParam);

    void edit(MailManagementEditParam mailManagementEditParam);

    void delete(List<Long> mailManagementIdList);

    void isPass(Long mailManagementId);

}
