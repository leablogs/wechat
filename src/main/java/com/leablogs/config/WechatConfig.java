package com.leablogs.config;

public class WechatConfig {
	private static String APPID;
	private static String APPSECRET;
	private static String Token;
	private static String EncodingAESKey;
//	static {
//		APPID = "wx0bf8a984201c5b29";
//		APPSECRET="d68faa7dbdb4806fdbc282fe7556b882";
//	}
	public WechatConfig(String aPPID, String aPPSECRET, String token, String encodingAESKey) {
		super();
		APPID = aPPID;
		APPSECRET = aPPSECRET;
		Token = token;
		EncodingAESKey = encodingAESKey;
	}

	public static String getAppid() {
		return APPID;
	}

	public static String getAppsecret() {
		return APPSECRET;
	}

	public static String getToken() {
		return Token;
	}

	public static String getEncodingaeskey() {
		return EncodingAESKey;
	}

}