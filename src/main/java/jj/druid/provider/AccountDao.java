package jj.druid.provider;

import jj.druid.mybatis.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

public class AccountDao {
    private SqlSessionFactory sqlSessionFactory;

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public int checkAgencyExists(String type,String value) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        Map map = new HashMap();
        map.put(type,value);
        int id = mapper.selectAgencyID(map);
        sqlSession.close();
        return id;
    }

    public int checkUserExists(String type,String value) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        Map map = new HashMap();
        map.put(type,value);
        int id = mapper.selectUserID(map);
        sqlSession.close();
        return id;
    }

    public int checkUserAgencyDuplicate(String user_identity,int agency) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        Map map = new HashMap();

        sqlSession.close();
        return 0;
    }
}
