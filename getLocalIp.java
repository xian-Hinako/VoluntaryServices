package com.VolunServices.achieve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


//获取本机外网IP地址
public class getLocalIp {
	
	public String getIp(){

		String ip = "";
		String chinaz = "http://ip.chinaz.com";
		
		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
		    in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			while((read=in.readLine())!=null){
				inputLine.append(read+"\r\n");
			}
			//System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if(m.find()){
			String ipstr = m.group(1);
			ip = ipstr;
			//System.out.println(ipstr);
		}
		return ip;

	}
	
	 public static String getWebIP(String strUrl) {
		 try {		 
		 //连接网页
		  URL url = new URL(strUrl);
		  BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		  String s = "";
		  StringBuffer sb = new StringBuffer(""); 
		  String webContent = "";
		  //读取网页信息
		  while ((s = br.readLine()) != null) {
		  sb.append(s + "\r\n");
		  }
		  br.close();
		  //网页信息
		  webContent = sb.toString();
		  int start = webContent.indexOf("[")+1;
		  int end = webContent.indexOf("]");
		  //获取网页中  当前 的 外网IP
		  webContent = webContent.substring(start,end);
		  return webContent;
		 
		 } catch (Exception e) {
		  e.printStackTrace();
		  return "error open url:" + strUrl;
		 }
	 }
	

}
