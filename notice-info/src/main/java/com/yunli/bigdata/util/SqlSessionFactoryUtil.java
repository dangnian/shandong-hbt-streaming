package com.yunli.bigdata.util;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author pingchangxin
 * @description mybatis 工具
 * @date 2021/6/22
 **/
@Slf4j
public class SqlSessionFactoryUtil {

    private static SqlSessionFactory sqlSessionFactory;

    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();

    static {
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")){
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static SqlSession openSession() {
        SqlSession session = threadLocal.get();
        try {
            if (session == null) {
                session = sqlSessionFactory.openSession(true);
                threadLocal.set(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return session;
    }

    public static void closeSession() {
        SqlSession session = threadLocal.get();
        if (session != null) {
            session.close();
            threadLocal.remove();
        }
    }

}
