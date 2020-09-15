package com.leablogs.util;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {
	private static Gson gson = new Gson();
	private static JsonObject jsonObject = null;

	public static String getJson(Object jsonObject) {
		return gson.toJson(jsonObject);
	}

	public static String getJson(List<Object> jsonList) {
		return gson.toJson(jsonList);
	}

	public static String getJson(Map<Object, Object> jsonMap) {
		return gson.toJson(jsonMap);
	}

	public static String getJson(Set<Object> jsonSet) {
		return gson.toJson(jsonSet);
	}

	public static Object getObject(String json, Class class1) {
		return gson.fromJson(json, class1);
	}

	public static JsonObject jsonStringToJsonObject(String jsonStr) {
		return JsonParser.parseString(jsonStr).getAsJsonObject();
	}

	public static JsonObject jsonArrToJsonObject(Reader jsonReader) {
		return JsonParser.parseReader(jsonReader).getAsJsonObject();
	}

}
