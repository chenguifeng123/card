package org.qinzi.card.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.qinzi.card.po.MemberCard;
import org.qinzi.card.po.MemberCardExample;

import java.util.List;

public interface MemberCardMapper {
    int countByExample(MemberCardExample example);

    int deleteByExample(MemberCardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberCard record);

    int insertSelective(MemberCard record);

    List<MemberCard> selectByExampleWithRowbounds(MemberCardExample example, RowBounds rowBounds);

    List<MemberCard> selectByExample(MemberCardExample example);

    MemberCard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberCard record, @Param("example") MemberCardExample example);

    int updateByExample(@Param("record") MemberCard record, @Param("example") MemberCardExample example);

    int updateByPrimaryKeySelective(MemberCard record);

    int updateByPrimaryKey(MemberCard record);
}