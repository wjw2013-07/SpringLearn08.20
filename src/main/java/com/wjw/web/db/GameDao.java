package com.wjw.web.db;


import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.wjw.web.entity.GameInfo;

public class GameDao extends BaseDao{
	
	private SqlSession session;
	
	public List<GameInfo> queryList(){
		String smtpId = "GameInfo.queryList";
		session = sessionFactory.openSession();
		List<GameInfo> gameInfos = session.selectList(smtpId, null);
		session.close();
		return gameInfos;
	}
}
