package com.crm.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crm.oms.dto.MailOrderEditParam;
import com.crm.oms.dto.MailOrderParam;
import com.crm.oms.enums.IsLockedEnum;
import com.crm.oms.exception.ApiException;
import com.crm.oms.mapper.MailOrderMapper;
import com.crm.oms.model.MailOrder;
import com.crm.oms.service.MailOrderService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 邮件订单信息表 服务实现类
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */

@Slf4j
@Service
public class MailOrderServiceImpl extends ServiceImpl<MailOrderMapper, MailOrder> implements MailOrderService {


    @Resource
    private MailOrderMapper mailOrderMapper;

    @Override
    public Page<MailOrder> list(MailOrderParam mailOrderParam, Integer pageSize, Integer pageNum) {
        Page<MailOrder> page = new Page<>(pageNum, pageSize);
        return page(page, mailOrderParam.list());
    }

    @Override
    public List<MailOrder> export(MailOrderParam mailOrderParam) {
        return mailOrderMapper.selectList(mailOrderParam.list());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editMailOrder(MailOrderEditParam mailOrderEditParam) {
        LambdaUpdateChainWrapper<MailOrder> lambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(mailOrderMapper);
        boolean update = lambdaUpdateChainWrapper
                .in(MailOrder::getId, mailOrderEditParam.getMailOrderIdList())
                .set(MailOrder::getWaybillStatus, mailOrderEditParam.getWaybillStatus())
                .set(MailOrder::getDeliveryStatus, mailOrderEditParam.getDeliveryStatus())
                .set(MailOrder::getNote, mailOrderEditParam.getNote())
                .set(MailOrder::getUpdateTime, new Date()).update();
        if (!update) {
            log.error("editMailOrder编辑订单信息失败mailOrderEditParam={}", JSON.toJSONString(mailOrderEditParam));
            throw new ApiException("编辑订单信息异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockingMailOrder(Long mailOrderId) {
        LambdaUpdateChainWrapper<MailOrder> mailOrderLambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(mailOrderMapper);
        boolean update = mailOrderLambdaUpdateChainWrapper.eq(MailOrder::getId, mailOrderId)
                .eq(MailOrder::getIsLocked, IsLockedEnum.TYPE0.getCode())
                .set(MailOrder::getIsLocked, IsLockedEnum.TYPE1.getCode())
                .set(MailOrder::getUpdateTime, new Date()).update();
        if (!update) {
            log.error("lockingMailOrder锁定订单信息失败mailOrderId={}", mailOrderId);
            throw new ApiException("当前订单信息锁定异常请核实");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void relieveMailOrder(Long mailOrderId) {
        LambdaUpdateChainWrapper<MailOrder> mailOrderLambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(mailOrderMapper);
        boolean update = mailOrderLambdaUpdateChainWrapper.eq(MailOrder::getId, mailOrderId)
                .eq(MailOrder::getIsLocked, IsLockedEnum.TYPE1.getCode())
                .set(MailOrder::getIsLocked, IsLockedEnum.TYPE0.getCode())
                .set(MailOrder::getUpdateTime, new Date()).update();
        if (!update) {
            log.error("relieveMailOrder解除锁定订单信息失败mailOrderId={}", mailOrderId);
            throw new ApiException("当前订单信息解除锁定异常请核实");
        }

    }

    @Override
    public void deleteMailOrder(Long mailOrderId) {
        int result = mailOrderMapper.deleteById(mailOrderId);
        if (result < 0) {
            log.error("deleteMailOrder删除用户订单失败mailOrderId={}", mailOrderId);
            throw new ApiException("删除订单失败");
        }
    }
}
