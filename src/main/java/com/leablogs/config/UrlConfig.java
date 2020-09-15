package com.leablogs.config;

public class UrlConfig {
	// 获取access_token
	public static String accessTokenUrl() {
		String appid = WechatConfig.getAppid();
		String secret = WechatConfig.getAppsecret();
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret="
				+ secret;
	}
}
