package com.yunli.bigdata.needdeal.dao;

import com.yunli.bigdata.needdeal.domain.NeeddealDomain;
import com.yunli.bigdata.needdeal.model.Needdeal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pingchangxin
 */
@Mapper
public interface NeeddealDAO {

    int deleteByPrimaryKey(String id);

    int insert(Needdeal record);

    int insertSelective(NeeddealDomain record);

    Needdeal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Needdeal record);

    int updateByPrimaryKey(Needdeal record);

    int insertBatch(List<Needdeal> list);

    int updateBatch(List<Needdeal> list);

}