package jj.druid.provider;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class AccountDao {
    private SqlSessionFactory sqlSessionFactory;

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void function() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List l = sqlSession.selectList("selectAll");
        System.out.println(l);
        sqlSession.close();
    }

    public void func() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("update");
        System.out.println("update");
        sqlSession.close();
    }
}
