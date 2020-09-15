package com.leablogs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.gson.Gson;
import com.leablogs.entity.JsonParams;

@Controller
@RequestMapping("/")
public class DemoController {
	@GetMapping("/doGet")
	@ResponseBody
	public String doGet(String a, String b) {
		System.out.println(a);
		System.out.println(b);
		return "aaaa";
	}

	@RequestMapping(value = "/doPostMap", method = RequestMethod.POST)
	@ResponseBody
	public String doPostMap(@RequestBody Map<String, String> map) {
		System.out.println(map.toString());
		return "bbbb";
	}

	@RequestMapping(value = "/doPostList", method = RequestMethod.POST)
	@ResponseBody
	public String doPostEntity(@RequestBody JsonParams jsonParams) {
		Gson gson = new Gson();
		String json = gson.toJson(jsonParams);
		System.out.println(json);
		JsonParams jsonParams2 = gson.fromJson(json, JsonParams.class);
		System.out.println(jsonParams2.getName());
		return "bbbb";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String doGeta(@RequestParam("id") int id, @RequestParam("name") String name) {
		System.out.println(id);
		System.out.println(name);
		return "ccc";
	}
}
