package ctas.test.mapper;

import ctas.test.entity.FeeBase;

public interface FeeBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FeeBase record);

    int insertSelective(FeeBase record);

    FeeBase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FeeBase record);

    int updateByPrimaryKey(FeeBase record);
}