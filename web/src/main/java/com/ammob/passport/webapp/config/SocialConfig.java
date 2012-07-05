package com.ammob.passport.webapp.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.jasig.cas.CentralAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.util.CookieGenerator;

import com.ammob.passport.social.renren.connect.RenrenConnectionFactory;
import com.ammob.passport.social.txwb.api.Txwb;
import com.ammob.passport.social.txwb.api.oauth1.TxwbTemplate;
import com.ammob.passport.social.txwb.connect.oauth1.TxwbConnectionFactory;
import com.ammob.passport.social.weibo.api.Weibo;
import com.ammob.passport.social.weibo.api.v2.WeiboTemplate;
import com.ammob.passport.social.weibo.connect.v2.WeiboConnectionFactory;
import com.ammob.passport.webapp.adapter.SimpleSignInAdapter;
import com.ammob.passport.webapp.interceptor.PostToWallAfterConnectInterceptor;
import com.ammob.passport.webapp.interceptor.TweetAfterConnectInterceptor;

@Configuration
@PropertySource("classpath:oauth.properties")
public class SocialConfig {

	@Inject
	private Environment environment;

	@Inject
	private DataSource dataSource;
	
	@Inject
	private CentralAuthenticationService centralAuthenticationService;
	
	@Inject
	private CookieGenerator ticketGrantingTicketCookieGenerator;
	
	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES) 
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new TwitterConnectionFactory(environment.getProperty("twitter.consumerKey"),
				environment.getProperty("twitter.consumerSecret")));
		registry.addConnectionFactory(new FacebookConnectionFactory(environment.getProperty("facebook.clientId"),
				environment.getProperty("facebook.clientSecret")));
		registry.addConnectionFactory(new LinkedInConnectionFactory(environment.getProperty("linkedin.consumerKey"),
				environment.getProperty("linkedin.consumerSecret")));
		registry.addConnectionFactory(new WeiboConnectionFactory(environment.getProperty("weibo.consumerKey"),
				environment.getProperty("weibo.consumerSecret")));
		registry.addConnectionFactory(new TxwbConnectionFactory(environment.getProperty("tencent.consumerKey"),
				environment.getProperty("tencent.consumerSecret")));
		registry.addConnectionFactory(new RenrenConnectionFactory(environment.getProperty("renren.consumerKey"),
				environment.getProperty("renren.consumerSecret")));
		return registry;
	}

	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES) 
	public UsersConnectionRepository usersConnectionRepository() {
		JdbcUsersConnectionRepository usersConnectionRepository = 
				new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), Encryptors.noOpText());
		return usersConnectionRepository;
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public ConnectionRepository connectionRepository() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		return usersConnectionRepository().createConnectionRepository(authentication.getName());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Facebook facebook() {
		Connection<Facebook> facebook = connectionRepository().findPrimaryConnection(Facebook.class);
		return facebook != null ? facebook.getApi() : new FacebookTemplate();
	}
	
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Twitter twitter() {
		Connection<Twitter> twitter = connectionRepository().findPrimaryConnection(Twitter.class);
		return twitter != null ? twitter.getApi() : new TwitterTemplate();
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public LinkedIn linkedin() {
		Connection<LinkedIn> linkedin = connectionRepository().findPrimaryConnection(LinkedIn.class);
		return linkedin != null ? linkedin.getApi() : null;
	}
	
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Weibo weibo() {
		Connection<Weibo> weibo = connectionRepository().findPrimaryConnection(Weibo.class);
		return weibo != null ? weibo.getApi() : new WeiboTemplate();
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Txwb txwb() {
		Connection<Txwb> txwb = connectionRepository().findPrimaryConnection(Txwb.class);
		return txwb != null ? txwb.getApi() : new TxwbTemplate();
	}
	
//	@Bean
//	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
//	public Renren renren() {
//		Connection<Renren> renren = connectionRepository().findPrimaryConnection(Renren.class);
//		return renren != null ? renren.getApi() : new RenrenTemplate();
//	}
	
	@Bean
	public ConnectController connectController() {
		ConnectController connectController = new ConnectController(connectionFactoryLocator(), connectionRepository());
		connectController.addInterceptor(new PostToWallAfterConnectInterceptor());
		connectController.addInterceptor(new TweetAfterConnectInterceptor());
		return connectController;
	}

	@Bean
	public ProviderSignInController providerSignInController(RequestCache requestCache) {
		ProviderSignInController providerSignInController = new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(), new SimpleSignInAdapter(requestCache, centralAuthenticationService, ticketGrantingTicketCookieGenerator));
		providerSignInController.setSignUpUrl("/signup?bind");
		return providerSignInController;
	}
}
