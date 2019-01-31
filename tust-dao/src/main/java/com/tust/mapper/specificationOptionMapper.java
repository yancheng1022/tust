package com.tust.mapper;

import com.tust.pojo.specificationOption;
import com.tust.pojo.specificationOptionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface specificationOptionMapper {
    int countByExample(specificationOptionExample example);

    int deleteByExample(specificationOptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(specificationOption record);

    int insertSelective(specificationOption record);

    List<specificationOption> selectByExample(specificationOptionExample example);

    specificationOption selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") specificationOption record, @Param("example") specificationOptionExample example);

    int updateByExample(@Param("record") specificationOption record, @Param("example") specificationOptionExample example);

    int updateByPrimaryKeySelective(specificationOption record);

    int updateByPrimaryKey(specificationOption record);
}