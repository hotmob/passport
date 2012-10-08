/**
 * 
 */
package com.ammob.passport.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mob
 *
 */
public class GrantManagerImplTest {

	private final JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
	private final String username = "低";
	private final String password = "121212";
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setResourceClasses(GrantManagerImpl.class);
        factoryBean.setAddress("http://localhost:9000");
        factoryBean.create();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * {@link com.ammob.passport.service.impl.GrantManagerImpl#getGrantTicket(java.lang.String, java.lang.String)} 的测试方法。
	 */
	@Test
	public void testGetGrantTicket() throws Exception {
		Get("http://localhost:9000/tickets/time");
		try {
			getUserInfo("http://passport.766.com");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private void getUserInfo(String urlPrefix) throws Exception {
		String response = "", st = "";
		HttpMethod method = Post(urlPrefix + "/v1/tickets/", new NameValuePair[] {new NameValuePair("username", username), new NameValuePair("password", password)});
		String [] result = getResponseHeader(method).split("/");
		String tgt = result != null && result.length > 1 ? result[result.length-1] : "";
		System.out.println(tgt);
		st = getResponseBody(Post(urlPrefix + "/v1/tickets/" + tgt, new NameValuePair[] {new NameValuePair("service", urlPrefix)}));
		System.out.println(st);
		response = getResponseBody(Post(urlPrefix + "/serviceValidate", new NameValuePair[] {new NameValuePair("service", urlPrefix), new NameValuePair("ticket", st)}));
		System.out.println(response.trim());
	}
	
	private static void Get(String url) throws Exception {  
        HttpClient client = new HttpClient();  
        GetMethod method = new GetMethod(url);
        int statusCode = client.executeMethod(method);  
        if (statusCode != HttpStatus.SC_OK) {  
            System.err.println("Method failed: " + method.getStatusLine());  
        }  
        byte[] responseBody = method.getResponseBody();  
        System.out.println(new String(responseBody));  
    }
	
	private static HttpMethod Post(String url, NameValuePair[] nvp) throws Exception {  
        HttpClient client = new HttpClient();  
        PostMethod method = new PostMethod(url);
        method.setRequestBody(nvp);
        int statusCode = client.executeMethod(method);
        if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
            System.err.println("Method failed: " + method.getStatusLine());
        }
        Cookie[] cookies = client.getState().getCookies();
        for (Cookie cookie : cookies)
        	System.out.println("Cookie Name = " + cookie.getName() + ", Value = " + cookie.getValue() + ", Domain = " + cookie.getDomain() + ", Path = " + cookie.getPath() + ", ExpiryDate = " + cookie.getExpiryDate());
        return method;
    }
	
	protected static HttpMethod Put(String url, NameValuePair[] nvp) throws Exception {
		HttpClient client = new HttpClient();  
        PutMethod method = new PutMethod(url);
        method.setQueryString(nvp);
        int statusCode = client.executeMethod(method);
        if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
            System.err.println("Method failed: " + method.getStatusLine());
        }
        Cookie[] cookies = client.getState().getCookies();
        for (Cookie cookie : cookies)
        	System.out.println("Cookie Name = " + cookie.getName() + ", Value = " + cookie.getValue() + ", Domain = " + cookie.getDomain() + ", Path = " + cookie.getPath() + ", ExpiryDate = " + cookie.getExpiryDate());
        return method;
	}
	
	private static String getResponseHeader(HttpMethod method) {
		return method.getResponseHeader("Location").getValue();
	}
	
	private static String getResponseBody(HttpMethod method) throws IOException {
		 byte[] responseBody = method.getResponseBody();
		 return new String(responseBody);
	}
	
	public static void main(String[] args) throws Exception {
		String username = "`檰糀糖﹏♥";
		// 测试注册
		HttpMethod method = GrantManagerImplTest.Put("http://passport.766.com/v1/users/", new NameValuePair[] {new NameValuePair("username", username), new NameValuePair("password", username), new NameValuePair("email", username + "@163.com")});
		System.out.println(method.getResponseBodyAsString());
	}
}
