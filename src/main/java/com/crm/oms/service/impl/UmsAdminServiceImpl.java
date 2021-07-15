package com.crm.oms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crm.oms.common.utils.JwtTokenUtil;
import com.crm.oms.dto.AddUmsAdmin;
import com.crm.oms.dto.UpdateAdminParam;
import com.crm.oms.dto.UpdateUmsAdminParam;
import com.crm.oms.exception.ApiException;
import com.crm.oms.mapper.UmsAdminMapper;
import com.crm.oms.model.UmsAdmin;
import com.crm.oms.model.UmsAdminExample;
import com.crm.oms.model.UmsAdminRoleRelation;
import com.crm.oms.model.UmsPermission;
import com.crm.oms.service.UmsAdminRoleRelationService;
import com.crm.oms.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * UmsAdminService实现类
 */

@Slf4j
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UmsAdminMapper adminMapper;

    @Resource
    private UmsAdminRoleRelationService umsAdminRoleRelationService;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public void register(AddUmsAdmin addUmsAdmin) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(addUmsAdmin, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setUpdateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            throw new ApiException("改姓名已经注册过了");
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);

        UmsAdminRoleRelation umsAdminRoleRelation = new UmsAdminRoleRelation();
        umsAdminRoleRelation.setAdminId(umsAdmin.getId());
        umsAdminRoleRelation.setRoleId(2L);
        umsAdminRoleRelationService.save(umsAdminRoleRelation);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }


    @Override
    public void updatePassword(UpdateUmsAdminParam updateUmsAdminParam) {

        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andIdEqualTo(updateUmsAdminParam.getId());
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (CollUtil.isEmpty(adminList)) {
            throw new ApiException("找不到该用户");
        }
        UmsAdmin umsAdmin = adminList.get(0);
        umsAdmin.setPassword(passwordEncoder.encode(updateUmsAdminParam.getPassword()));
        umsAdmin.setNickName(updateUmsAdminParam.getNickName());
        int result = adminMapper.updateByPrimaryKey(umsAdmin);
        if (result <= 0) {
            throw new ApiException("修改昵称账号密码不正确");
        }

    }


    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminMapper.getPermissionList(adminId);
    }

    @Override
    public void delete(Long adminId) {
        int result = adminMapper.deleteById(adminId);
        if (result <= 0) {
            throw new ApiException("删除用户id不存在");
        }
    }

    @Override
    public void updateAdmin(UpdateAdminParam updateAdminParam) {
        LambdaUpdateChainWrapper<UmsAdmin> umsAdminLambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(adminMapper);
        boolean update = umsAdminLambdaUpdateChainWrapper.eq(UmsAdmin::getId, updateAdminParam.getId())
                .set(UmsAdmin::getUsername, updateAdminParam.getUsername())
                .set(UmsAdmin::getNickName, updateAdminParam.getNickName())
                .set(UmsAdmin::getEmail, updateAdminParam.getEmail())
                .set(UmsAdmin::getPassword, passwordEncoder.encode(updateAdminParam.getPassword()))
                .set(UmsAdmin::getNumberMailbox, updateAdminParam.getNumberMailbox())
                .set(UmsAdmin::getDueDate, updateAdminParam.getDueDate())
                .set(UmsAdmin::getUpdateTime, new Date()).update();
        if (!update) {
            log.error("UmsAdminServiceImpl.updateAdmin修改账户信息失败updateAdminParam={}", JSON.toJSONString(updateAdminParam));
            throw new ApiException("修改账户信息失败请联系管理员");
        }
    }

}
