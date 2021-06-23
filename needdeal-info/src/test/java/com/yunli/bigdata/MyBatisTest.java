package com.yunli.bigdata;

import com.yunli.bigdata.needdeal.dao.NeeddealDAO;
import com.yunli.bigdata.needdeal.model.Needdeal;
import com.yunli.bigdata.util.SqlSessionFactoryUtil;
import org.junit.Test;

/**
 * @author pingchangxin
 * @description mybatis测试
 * @date 2021/6/22
 **/
public class MyBatisTest {

    @Test
    public void testMyBatis() {
        NeeddealDAO needdealDAO = SqlSessionFactoryUtil.getMapper(NeeddealDAO.class);
        Needdeal needdeal = needdealDAO.selectByPrimaryKey("1");
        System.out.println(needdeal);
    }


}
