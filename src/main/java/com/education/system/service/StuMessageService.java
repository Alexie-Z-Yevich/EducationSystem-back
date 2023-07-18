package com.education.system.service;

import com.education.system.entity.StuMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AizNoyer
 * @since 2023-07-17
 */
public interface StuMessageService extends IService<StuMessage> {

    StuMessage getByStuId(String stuId);

    String getUserAuthorityInfo(Long id);

}
