package com.wjw.web.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.wjw.web.entity.ServerInfo;

public class ServerDao extends BaseDao {

	private SqlSession session;

	public List<ServerInfo> queryServersByGameId(int gameId) {
		String stmtId = "ServerInfo.queryServersByGameId";
		session = sessionFactory.openSession();
		List<ServerInfo> serverInfos = session.selectList(stmtId, gameId);
		session.close();
		return serverInfos;
	}
	
	public int update(int code, int gameId, String name){
		
		List<ServerInfo> serverInfos = new ArrayList<ServerInfo>();
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setCode(code);
		serverInfo.setGameId(gameId);
		serverInfo.setName(name);
		serverInfos.add(serverInfo);
		
		String stmtId = "ServerInfo.updateServers";
		session = sessionFactory.openSession();
		int row = session.insert(stmtId, serverInfos);
		return row;
	}
}
