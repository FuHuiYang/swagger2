package com.yang.mybatis.mapper3;

import com.yang.bean.SZIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.mybatis.mapper
 * @Description: TODO
 * @date Date : 2020年03月17日 16:17
 */

public interface SZIndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SZIndex record);

    int insertSelective(SZIndex record);

    SZIndex selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SZIndex record);

    int updateByPrimaryKey(SZIndex record);

    List<SZIndex> selectSZIndex(@Param("startdate")String startdate, @Param("enddate")String enddate);

    int insertSZList(List list);
}
