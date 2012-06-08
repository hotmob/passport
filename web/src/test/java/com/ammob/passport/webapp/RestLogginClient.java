/*
 * Copyright 2011 the Ammob.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ammob.passport.webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.util.StringUtils;

/**
 * rest 登陆测试.
 * $Revision: 50 $
 * $Date: 2011-07-15 16:47:48 +0800 (周五, 2011-07-15) $
 * $Id: RestLogginClient.java 50 2011-07-15 08:47:48Z hotmob $
 */
public class RestLogginClient {

	private static final Logger logger = Logger.getLogger(RestLogginClient.class.getName());
	
	public static void main(final String[] args) {
//		final String server = "http://passport.test.766.com/v1/tickets/";
//		final String username = "mob";
//		final String password = "121212";
//		final String service = "http://passport.test.766.com/services/";
//		final String validateServer = "http://passport.test.766.com/serviceValidate";
		
		final String server = "http://passport.766.com/v1/tickets/";
		final String username = "hotmob";
		final String password = "123456";
		final String service = "http://passport.766.com/services/";
		final String validateServer = "http://passport.766.com/serviceValidate";
		
//		final String server = "http://dev.local.ammob.com:9080/v1/tickets/";
//		final String username = "hotmob";
//		final String password = "121212";
//		final String service = "http://dev.local.ammob.com:9080/services/";
//		final String validateServer = "http://dev.local.ammob.com:9080/serviceValidate";
		
//		final String validateServer = "http://dev.local.ammob.com:9080/samlValidate";
		
		final String st = getTicket(server, username, password, service);
		final String result = getUserInfo(validateServer, service, st.trim());
		System.out.println(result);
	}
	
	public static String getTicket(final String server, final String username,
			final String password, final String service) {
		notNull(server, "server must not be null");
		notNull(username, "username must not be null");
		notNull(password, "password must not be null");
		notNull(service, "service must not be null");
		String ticketGrantingTicket = getTicketGrantingTicket(server, username, password);
		return getServiceTicket(server, ticketGrantingTicket, service);
	}

	private static String getServiceTicket(final String server,
			final String ticketGrantingTicket, final String service) {
		if (ticketGrantingTicket == null)
			return null;
		final HttpClient client = new DefaultHttpClient();
		final HttpPost httpost = new HttpPost(server + ticketGrantingTicket);
		try {
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("service", service));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse httpResponse = client.execute(httpost);
	        switch(httpResponse.getStatusLine().getStatusCode()){
	        case 200:
	        	HttpEntity entity = httpResponse.getEntity();
	        	if (entity != null) {
	        		BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
	        		StringBuffer buffer = new StringBuffer();
	        		String line = "";
	        		while ((line = in.readLine()) != null){
	        			buffer.append(line);
	        		}
	        		org.apache.http.util.EntityUtils.consume(entity);
	        		logger.info("Service Ticket = " + buffer.toString());
	        		return buffer.toString();
	        	}
	        	break;
	        default:
	        	logger.info("Response (1k): ERROR ! ");
	        	break;
	        }
		} catch (final IOException e) {
			logger.warning(e.getMessage());
		}
		finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	private static String getTicketGrantingTicket(final String server,
			final String username, final String password) {
		final HttpClient client = new DefaultHttpClient();
		final HttpPost httpost = new HttpPost(server);
		try {
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("username", username));
	        nvps.add(new BasicNameValuePair("password", password));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse httpResponse = client.execute(httpost);
	        switch(httpResponse.getStatusLine().getStatusCode()){
			case 201:
				Header[] headers = httpResponse.getHeaders("Location");
				for(Header header : headers){
					String result = header.getValue().substring(server.length());
					if (StringUtils.hasText(result))
						logger.info("TG Ticket = " + result);
						return result;
				}
				logger.warning("Successful ticket granting request, but no ticket found!");
				break;
			default:
				logger.warning("Invalid response code (" + httpResponse.getStatusLine().getStatusCode() + ") from CAS server!");
				break;
			}
		} catch (final IOException e) {
			logger.warning(e.getMessage());
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	private static String getUserInfo(String server, String service, String ticket) {
		final HttpClient client = new DefaultHttpClient();
		final HttpPost httpost = new HttpPost(server);
		HttpResponse httpResponse;
		try {
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("service", service));
	        nvps.add(new BasicNameValuePair("ticket", ticket));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			httpResponse = client.execute(httpost);
			switch(httpResponse.getStatusLine().getStatusCode()){
	        case 200:
	        	HttpEntity entity = httpResponse.getEntity();
	        	if (entity != null) {
	        		BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
	        		StringBuffer buffer = new StringBuffer();
	        		String line = "";
	        		while ((line = in.readLine()) != null){
	        			buffer.append(line);
	        		}       
	        		org.apache.http.util.EntityUtils.consume(entity);
	        		return buffer.toString();
	        	}
	        	break;
			case 201:
				Header[] headers = httpResponse.getHeaders("Location");
				for(Header header : headers){
					logger.info(header.getValue());
					String result = header.getValue().substring(server.length());
					if (StringUtils.hasText(result))
						return result;
				}
				logger.warning("Successful ticket granting request, but no ticket found!");
				break;
			default:
				logger.info("Invalid response code (" + httpResponse.getStatusLine().getStatusCode() + ") from CAS server!");
				break;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}
	
	private static void notNull(final Object object, final String message) {
		if (object == null)
			throw new IllegalArgumentException(message);
	}
}
