package com.leablogs.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leablogs.config.UrlConfig;
import com.leablogs.entity.JsonParams;
import com.leablogs.util.HttpClientResultUtil;
import com.leablogs.util.HttpClientUtil;
import com.leablogs.util.JsonUtil;

@RestController
public class AccessTokenController {
	@GetMapping("getAccessTokne")
	public HttpClientResultUtil getAccessToken() throws IOException {
		String access = UrlConfig.accessTokenUrl();
		HttpClientResultUtil httpClientResult = HttpClientUtil.doGet(access);
		String jsonString = JsonUtil.getJson(httpClientResult);
		System.out.println(jsonString);
		return httpClientResult;
	}
}
