package com.crm.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crm.oms.common.utils.ShowMail;
import com.crm.oms.dto.MailManagementEditParam;
import com.crm.oms.dto.MailManagementParam;
import com.crm.oms.enums.MailTypeEnum;
import com.crm.oms.exception.ApiException;
import com.crm.oms.mapper.MailManagementMapper;
import com.crm.oms.model.MailManagement;
import com.crm.oms.service.MailManagementService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 账号管理同步表 服务实现类
 * </p>
 *
 * @since 2021-07-12
 */
@Slf4j
@Service
public class MailManagementServiceImpl extends ServiceImpl<MailManagementMapper, MailManagement> implements MailManagementService {

    @Resource
    private MailManagementMapper mailManagementMapper;

    @Override
    public List<MailManagement> list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<MailManagement> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        return mailManagementMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(MailManagementParam mailManagementParam) {
        MailManagement mailManagement = new MailManagement(mailManagementParam);
        mailManagement.setPassNot(1);
        int result = mailManagementMapper.insert(mailManagement);
        if (result < 0) {
            log.error("MailManagementServiceImpl.insert账号管理同步数据插入失败mailManagementParam={}", JSON.toJSONString(mailManagementParam));
            throw new ApiException("账号管理同步表插入数据失败");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(MailManagementEditParam mailManagementEditParam) {

        LambdaUpdateChainWrapper<MailManagement> lambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(mailManagementMapper);
        boolean result = lambdaUpdateChainWrapper
                .eq(MailManagement::getId, mailManagementEditParam.getMailManagementId())
                .set(MailManagement::getMailType, mailManagementEditParam.getMailType())
                .set(MailManagement::getServiceType, mailManagementEditParam.getServiceType())
                .set(MailManagement::getEmail, mailManagementEditParam.getEmail())
                .set(MailManagement::getAuthorizationCode, mailManagementEditParam.getAuthorizationCode())
                .set(MailManagement::getUpdateTime, new Date()).update();
        if (!result) {
            log.error("MailManagementServiceImpl.edit编辑账号管理同步异常mailManagementEditParam={}", JSON.toJSONString(mailManagementEditParam));
            throw new ApiException("账号管理同步表编辑数据失败");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> mailManagementIdList) {

        int result = mailManagementMapper.deleteBatchIds(mailManagementIdList);
        if (result < 0) {
            log.error("MailManagementServiceImpl账号管理失败mailManagementIdList={}", mailManagementIdList);
            throw new ApiException("账号同步删除失败");
        }

    }

    @Override
    public void isPass(Long mailManagementId) {
        MailManagement mailManagement = mailManagementMapper.selectById(mailManagementId);
        Integer serviceType = mailManagement.getServiceType();
        MailTypeEnum mailTypeEnum = MailTypeEnum.ofType(serviceType);
        String host = mailTypeEnum.getMessage();

        // 163登录信息
        String username = mailManagement.getEmail();
        String password = mailManagement.getAuthorizationCode();
        String protocol = mailTypeEnum.getNode();

        try {
            ShowMail.getWEMessage(host, username, password, protocol);
        } catch (MessagingException e) {
            log.error("MailManagementServiceImp系统链接邮件服务异常", e);
            throw new ApiException("邮箱设置存在异常，请检查授权码是否输入正确，或请手动登陆邮箱查看IMAP/SMTP服务是否正确开启");
        }
        mailManagement.setUpdateTime(new Date());
        mailManagement.setPassNot(0);
        mailManagementMapper.updateById(mailManagement);

    }
}
