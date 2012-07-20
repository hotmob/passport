/**
 * 
 */
package com.ammob.passport.social.txwb.api.oauth1;

import java.util.Date;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.txwb.api.TimelineOperations;
import com.ammob.passport.social.txwb.api.Tweet;
import com.ammob.passport.social.txwb.api.TweetList;
import com.ammob.passport.social.txwb.api.Txwb;

/**
 * @author iday
 * 
 */
public class TimelineTemplate extends AbstractTxwbOperations implements
		TimelineOperations {

	public TimelineTemplate(Txwb api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super(api, restTemplate, isAuthorized);
	}

	@Override
	public Tweet getStatus(long id) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("id", String.valueOf(id));
		return restTemplate.getForObject(buildUrl("t/show", params),
				Tweet.class);
	}

	@Override
	public void addStatus(String content, String clientip, float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/add"), params);
	}

	@Override
	public void addEmotionStatus(String content, String clientip, int signtype,
			float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("signtype", String.valueOf(signtype));
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/add_emotion"), params);
	}

	@Override
	public void addPicStatus(String content, String clientip, Resource pic,
			float jing, float wei) {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("pic", pic);
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/add_pic"), params);
	}

	@Override
	public void addPicStatus(String content, String clientip, String picUrl,
			float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("pic_url", picUrl);
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/add_pic_url"), params);
	}

	@Override
	public void addMusicStatus(String content, String clientip, String url,
			String title, String author, float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("url", url);
		params.add("title", title);
		params.add("author", author);
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/add_music"), params);
	}

	@Override
	public void addVideoStatus(String content, String clientip, String url,
			float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("url", url);
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/add_video"), params);
	}

	@Override
	public void addMultiStatus(String content, String clientip, String picUrl,
			String videoUrl, String musicUrl, String musicTitle,
			String musicAuthor, float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("pic_url", picUrl);
		params.add("video_url", videoUrl);
		params.add("music_url", musicUrl);
		params.add("music_title", musicTitle);
		params.add("music_author", musicAuthor);
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/add_multi"), params);
	}

	@Override
	public void delStatus(long id) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("id", String.valueOf(id));
		restTemplate.postForLocation(buildUrl("t/del"), params);
	}

	@Override
	public void retweetStatus(String content, String clientip, long reid,
			float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("reid", String.valueOf(reid));
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/re_add"), params);
	}

	@Override
	public void replyStatus(String content, String clientip, long reid,
			float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("reid", String.valueOf(reid));
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/reply"), params);
	}

	@Override
	public void commentStatus(String content, String clientip, long reid,
			float jing, float wei) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("content", content);
		params.add("clientip", clientip);
		params.add("reid", String.valueOf(reid));
		params.add("jing", String.valueOf(jing));
		params.add("wei", String.valueOf(wei));
		restTemplate.postForLocation(buildUrl("t/comment"), params);
	}

	@Override
	public List<Tweet> getBatchTimeline(Long... ids) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("ids", StringUtils.arrayToCommaDelimitedString(ids));
		return restTemplate.getForObject(buildUrl("t/list", params),
				TweetList.class).getInfo();
	}

	@Override
	public List<Tweet> getHomeTimeline(int pageflag, Date pagetime, int reqnum,
			int type, int contentType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("type", String.valueOf(type));
		params.add("contenttype", String.valueOf(contentType));
		return restTemplate.getForObject(
				buildUrl("statuses/home_timeline", params), TweetList.class)
				.getInfo();
	}

	@Override
	public List<Tweet> getPublicTimeline(int pos, int reqnum) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pos", String.valueOf(pos));
		params.add("reqnum", String.valueOf(reqnum));
		return restTemplate.getForObject(
				buildUrl("statuses/public_timeline", params), TweetList.class)
				.getInfo();
	}

	@Override
	public List<Tweet> getUserTimeline(int pageflag, Date pagetime, int reqnum,
			long lastid, String name, int type, int contentType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("lastid", String.valueOf(lastid));
		params.add("name", name);
		params.add("type", String.valueOf(type));
		params.add("contenttype", String.valueOf(contentType));
		return restTemplate.getForObject(
				buildUrl("statuses/user_timeline", params), TweetList.class)
				.getInfo();
	}

	@Override
	public List<Tweet> getUsersTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid, String[] names, int type, int contentType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("lastid", String.valueOf(lastid));
		params.add("names", StringUtils.arrayToCommaDelimitedString(names));
		params.add("type", String.valueOf(type));
		params.add("contenttype", String.valueOf(contentType));
		return restTemplate.getForObject(
				buildUrl("statuses/users_timeline", params), TweetList.class)
				.getInfo();
	}

	@Override
	public List<Tweet> getMentionsTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid, int type, int contentType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("lastid", String.valueOf(lastid));
		params.add("type", String.valueOf(type));
		params.add("contenttype", String.valueOf(contentType));
		return restTemplate
				.getForObject(buildUrl("statuses/mentions_timeline", params),
						TweetList.class).getInfo();
	}

	@Override
	public List<Tweet> getBroadcastTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid, int type, int contentType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("lastid", String.valueOf(lastid));
		params.add("type", String.valueOf(type));
		params.add("contenttype", String.valueOf(contentType));
		return restTemplate.getForObject(
				buildUrl("statuses/broadcast_timeline", params),
				TweetList.class).getInfo();
	}

	@Override
	public List<Tweet> getSpecialTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid, int type, int contentType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("lastid", String.valueOf(lastid));
		params.add("type", String.valueOf(type));
		params.add("contenttype", String.valueOf(contentType));
		return restTemplate.getForObject(
				buildUrl("statuses/special_timeline", params), TweetList.class)
				.getInfo();
	}

	@Override
	public List<Tweet> getAreaTimeline(int pos, int reqnum, String country,
			String province, String city) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pos", String.valueOf(pos));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("country", country);
		params.add("province", province);
		params.add("city", city);
		return restTemplate.getForObject(
				buildUrl("statuses/area_timeline", params), TweetList.class)
				.getInfo();
	}

	@Override
	public List<Tweet> getVipTimeline(int pageflag, Date pagetime, int reqnum,
			long lastid) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("lastid", String.valueOf(lastid));
		return restTemplate
				.getForObject(buildUrl("statuses/home_timeline_vip", params),
						TweetList.class).getInfo();
	}

	@Override
	public List<Tweet> getRetweetTimeline(int pageflag, Date pagetime,
			int reqnum, long rootid, int type, long lastid) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("pagetime", String.valueOf(pagetime.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("rootid", String.valueOf(rootid));
		params.add("type", String.valueOf(type));
		params.add("lastid", String.valueOf(lastid));
		return restTemplate.getForObject(
				buildUrl("statuses/sub_re_list", params), TweetList.class)
				.getInfo();
	}

	@Override
	public List<Tweet> getHtTimeline(int pageflag, Date time, int reqnum,
			int flag, long tweetid, String httext, long htid, int type,
			int contentType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("pageflag", String.valueOf(pageflag));
		params.add("flag", String.valueOf(flag));
		params.add("time", String.valueOf(time.getTime() / 1000));
		params.add("reqnum", String.valueOf(reqnum));
		params.add("tweetid", String.valueOf(tweetid));
		params.add("type", String.valueOf(type));
		params.add("htid", String.valueOf(htid));
		params.add("contenttype", String.valueOf(contentType));
		params.add("httext", httext);
		return restTemplate.getForObject(
				buildUrl("statuses/ht_timeline_ext", params), TweetList.class)
				.getInfo();
	}

}
