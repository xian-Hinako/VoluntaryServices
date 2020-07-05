package com.VolunServices.achieve;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

//获取网址地址
@Component
public class getWebAddress {
	public String getIp(HttpServletRequest request) {
		/**
         * 在一般情况下使用Request.getRemoteAddr()即可，
         * 但是经过nginx等反向代理软件后，这个方法会失效。
         * 本方法先从Header中获取X-Real-IP，
         * 如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
         * 如果还不存在则调用Request .getRemoteAddr()
         */
        String ip = request.getHeader("X-Real-IP");
        /**org.apache.commons.lang.StringUtils处理字符串类
         * IsEmpty/IsBlank – 检查字符串是否有内容。
         * IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable – 判断字符是否在字符串中。
         */
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
            	if(ip=="127.0.0.1"){
            		getLocalIp localIP = new getLocalIp();
            		ip = localIP.getIp();
            	}
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
