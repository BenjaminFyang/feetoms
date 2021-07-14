package com.crm.oms.service;

import com.crm.oms.dto.MailOrderEditParam;
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


    /**
     * 订单信息列表查询.
     *
     * @param mailOrderParam 查询参数.
     * @param pageSize       行数
     * @param pageNum        第几页
     * @return the list of MailOrder
     */
    List<MailOrder> list(MailOrderParam mailOrderParam, Integer pageSize, Integer pageNum);


    /**
     * 批量修改对选中的订单同步修改
     *
     * @param mailOrderEditParam 传参数
     */
    void editMailOrder(MailOrderEditParam mailOrderEditParam);

    /**
     * 锁定订单
     *
     * @param mailOrderId 主键的id
     */
    void lockingMailOrder(Long mailOrderId);

    /**
     * 解除锁定
     *
     * @param mailOrderId 主键的id
     */
    void relieveMailOrder(Long mailOrderId);

    /**
     * 删除订单
     *
     * @param mailOrderId 订单主键的id
     */
    void deleteMailOrder(Long mailOrderId);
}
