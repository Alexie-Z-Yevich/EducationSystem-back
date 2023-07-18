package com.education.system.mapper;

import com.education.system.entity.StuMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author AizNoyer
 * @since 2023-07-17
 */
@Mapper
@Repository
public interface StuMessageMapper extends BaseMapper<StuMessage> {

    List<Long> getNavMenuIds(Long id);

}
