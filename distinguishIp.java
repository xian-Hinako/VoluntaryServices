package com.VolunServices.achieve;

import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

  
//调用百度地图api服务
public class distinguishIp {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
	    while ((cp = rd.read()) != -1) {
		sb.append((char) cp);
		}
		 return sb.toString();
	   }

	 public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	InputStream is = new URL(url).openStream();
		try{
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String jsonText = readAll(rd);
		JSONObject json = JSONObject.fromObject(jsonText);
//		JSONObject json = new JSONObject(jsonText);
	    return json;
	   }finally {
		is.close();
	}
	}
	 
	 //根据百度地图api接口和获取到的外网Ip得到最终的地理位置
	 public static Object getaddress(String ip){
//		 getLocalIp g = new getLocalIp();
//		 String ip = g.getWebIP(strUrl);
		 Object object = null;
		 if(ip == null){
			 System.out.println("本机访问");
			 return object;
		 }else{
			 JSONObject json;
			 System.out.println("ip="+ip);
			try {
				json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=SY7LGBXj0wcYpNFt36OyEAmEb3QQtQhR&ip="+ip);
//				System.out.println(json);
//				System.out.println(((JSONObject) json.get("content")).get("address_detail"));
			    object = ((JSONObject) json.get("content")).get("address");
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		 }		 
	     return object;
	 }

} 
