package jj.druid.provider;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class AccountDao {
    private SqlSessionFactory sqlSessionFactory;

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void function() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.selectList("selectAll");
    }
}
