package com.crm.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crm.oms.dto.MailOrderParam;
import com.crm.oms.model.MailOrder;
import com.crm.oms.mapper.MailOrderMapper;
import com.crm.oms.service.MailOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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


    @Resource
    private MailOrderMapper mailOrderMapper;

    @Override
    public List<MailOrder> list(MailOrderParam mailOrderParam, Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<MailOrder> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("sku", "k")
                .between(Objects.nonNull(mailOrderParam.getStartOrderTime()) && Objects.nonNull(mailOrderParam.getEndOrderTime()), "create_time", mailOrderParam.getStartOrderTime(), mailOrderParam.getEndOrderTime());
        return mailOrderMapper.selectList(userQueryWrapper);
    }
}
