package com.wjw.web.db;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;

public class BaseDao {

	private final static String resource = "com/wjw/web/db/sqlMapConfig.xml";
	protected SqlSessionFactory sessionFactory;

	public BaseDao() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new SqlSessionFactoryBuilder().build(Resources
						.getResourceAsReader(resource));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
