package com.ammob.passport.webapp.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public class IpAddressInInterceptor extends AbstractPhaseInterceptor<Message> {

    protected final transient Log log = LogFactory.getLog(getClass());
    
	private Set<String> allowedList = new HashSet<String>();
	private Set<String> deniedList = new HashSet<String>();

	public IpAddressInInterceptor() {
		super(Phase.RECEIVE);
	}

	public void handleMessage(Message message) throws Fault {
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = request.getRemoteAddr(); // 取客户端IP地址
		// 先处理拒绝访问的地址
		for (String deniedIpAddress : deniedList) {
			if (ipAddress.startsWith(deniedIpAddress)) {
				throw new Fault(new IllegalAccessException("IP address " + ipAddress + " is denied"));
			}
		}
		// 如果允许访问的集合非空，继续处理，否则认为全部IP地址均合法
		if (allowedList.size() > 0) {
			boolean contains = false;
			for (String allowedIpAddress : allowedList) {
				if (ipAddress.startsWith(allowedIpAddress)) {
					contains = true;
					log.info("Allowed Ip Address : " + getIpAddr(request));
					break;
				}
			}
			if (!contains) {
				throw new Fault(new IllegalAccessException("IP address " + ipAddress + " is not allowed"));
			}
		}
	}

	public Set<String> getAllowedList() {
		return allowedList;
	}

	public void setAllowedList(Set<String> allowedList) {
		this.allowedList = allowedList;
	}

	public Set<String> getDeniedList() {
		return deniedList;
	}

	public void setDeniedList(Set<String> deniedList) {
		this.deniedList = deniedList;
	}
	
	private String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(ipAddress.equals("127.0.0.1")){
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress= inet.getHostAddress();
			}
		}
		return ipAddress;
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//		if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
//			if(ipAddress.indexOf(",")>0){
//				ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
//			}
//		}
//		return ipAddress;
	}
}