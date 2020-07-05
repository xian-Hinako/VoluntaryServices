package com.VolunServices.achieve;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

//��ȡ��ַ��ַ
@Component
public class getWebAddress {
	public String getIp(HttpServletRequest request) {
		/**
         * ��һ�������ʹ��Request.getRemoteAddr()���ɣ�
         * ���Ǿ���nginx�ȷ��������������������ʧЧ��
         * �������ȴ�Header�л�ȡX-Real-IP��
         * ����������ٴ�X-Forwarded-For��õ�һ��IP(��,�ָ�)��
         * ����������������Request .getRemoteAddr()
         */
        String ip = request.getHeader("X-Real-IP");
        /**org.apache.commons.lang.StringUtils�����ַ�����
         * IsEmpty/IsBlank �C ����ַ����Ƿ������ݡ�
         * IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable �C �ж��ַ��Ƿ����ַ����С�
         */
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // ��η���������ж��IPֵ����һ��Ϊ��ʵIP��
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
