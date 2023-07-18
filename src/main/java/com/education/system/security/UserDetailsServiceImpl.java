package com.education.system.security;

import com.education.system.entity.StuMessage;
import com.education.system.service.StuMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    StuMessageService stuMessageService;

    @Override
    public UserDetails loadUserByUsername(String stuId) throws UsernameNotFoundException {

        StuMessage stuMessage = stuMessageService.getByStuId(stuId);
        if (stuMessage == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return new AccountUser(stuMessage.getId(), stuMessage.getStuId(), stuMessage.getPassword(), getUserAuthorities(stuMessage.getId()));
    }

    // 获取用户权限信息（角色、菜单权限）
    public List<GrantedAuthority> getUserAuthorities(Long id) {

        // 角色（Role_admin）、菜单操作权限sys:user:list
        String authority = stuMessageService.getUserAuthorityInfo(id);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
