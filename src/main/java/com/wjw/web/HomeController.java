package com.wjw.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wjw.web.cache.MemcacheManger;
import com.wjw.web.db.GameDao;
import com.wjw.web.db.ServerDao;
import com.wjw.web.entity.GameInfo;
import com.wjw.web.entity.ServerInfo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	private GameDao gameDao;
	private ServerDao serverGao;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "home";
	}

	@RequestMapping(value = "/addServers", method = RequestMethod.GET)
	public String updateServers(Model model, @RequestParam("code") int code,
			@RequestParam("gameId") int gameId,
			@RequestParam("name") String name) {
		serverGao = new ServerDao();
		int row = serverGao.update(code, gameId, name);
		String result = null;
		if (row != -1) {
			try {
				System.out.println("row = " + row + new String(name.getBytes("gbk"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			selectServers(gameId);
			result = "add server is success";
		}else {
			result = "add data is fail";
		}
		model.addAttribute("update_result", result);
		return "update";
	}

	@RequestMapping(value = "/serverInfos", method = RequestMethod.GET)
	public String getServers(Model model, @RequestParam("gameId") int gameId) {
		Object obj = MemcacheManger.getCache("wangjunwei" + gameId);
		if (obj != null) {
			System.out.println("+++++++++++");
			model.addAttribute("gameList", obj);
		} else {
			System.out.println("-----------");
			serverGao = new ServerDao();
			List<ServerInfo> servers = selectServers(gameId);
			model.addAttribute("gameList", servers);
		}
		return "gameInfos";
	}

	private List<ServerInfo> selectServers(int gameId) {
		List<ServerInfo> servers = serverGao.queryServersByGameId(gameId);
		for (ServerInfo serverInfo : servers) {
			try {
				String name = encodStr(serverInfo.getName(), "ISO8859-1");
				serverInfo.setName(name);
				System.out.println(name + "::" + serverInfo.getCode());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		MemcacheManger.setCache("wangjunwei" + gameId, 10000, servers);
		return servers;
	}

	private String encodStr(String name, String encode) throws UnsupportedEncodingException {
		return new String(name.getBytes(encode), "gb2312");
	}
}
