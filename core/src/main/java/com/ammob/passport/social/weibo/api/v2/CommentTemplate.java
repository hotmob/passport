/**
 * 
 */
package com.ammob.passport.social.weibo.api.v2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.weibo.api.Comment;
import com.ammob.passport.social.weibo.api.CommentOperations;
import com.ammob.passport.social.weibo.api.Weibo;

/**
 * @author iday
 * 
 */
public class CommentTemplate extends AbstractWeiboOperations implements CommentOperations {

	public CommentTemplate(Weibo api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super(api, restTemplate, isAuthorized);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#createComment(long, java.lang.String, int)
	 */
	@Override
	public Comment createComment(long id, String message, int commentOri) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("id", String.valueOf(id));
		commentParams.add("comment", message);
		commentParams.add("comment_ori", String.valueOf(commentOri));
		return restTemplate.postForObject(buildUrl("comments/show.json"),
				commentParams, Comment.class);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#createComment(long, java.lang.String)
	 */
	@Override
	public Comment createComment(long id, String message) {
		return createComment(id, message, 0);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#destroyComment(long)
	 */
	@Override
	public Comment destroyComment(long id) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("cid", String.valueOf(id));
		return restTemplate.postForObject(buildUrl("comments/destroy.json"),
				commentParams, Comment.class);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#destroyBatch(java.lang.Long)
	 */
	@Override
	public List<Comment> destroyComment(Long... ids) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("ids", StringUtils.join(ids));
		return restTemplate.postForObject(
				buildUrl("comments/destroy_batch.json"), commentParams,
				CommentArrayList.class);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#replyComment(long, long, java.lang.String, int, int)
	 */
	@Override
	public Comment replyComment(long id, long cid, String comment,
			int withoutMention, int commentOri) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("id", String.valueOf(id));
		commentParams.add("cid", String.valueOf(cid));
		commentParams.add("comment", comment);
		commentParams.add("without_mention", String.valueOf(withoutMention));
		commentParams.add("comment_ori", String.valueOf(commentOri));
		return restTemplate.postForObject(buildUrl("comments/reply.json"),
				commentParams, Comment.class);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#replyComment(long, long, java.lang.String)
	 */
	@Override
	public Comment replyComment(long id, long cid, String comment) {
		return replyComment(id, cid, comment, 0, 0);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getComments(long, long, long, int, int, int)
	 */
	@Override
	public List<Comment> getComments(long id, long sinceId, long maxId,
			int count, int page, int filterByAuthor) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("id", String.valueOf(id));
		commentParams.add("since_id", String.valueOf(sinceId));
		commentParams.add("max_id", String.valueOf(maxId));
		commentParams.add("count", String.valueOf(count));
		commentParams.add("page", String.valueOf(page));
		commentParams.add("filter_by_author", String.valueOf(filterByAuthor));
		return restTemplate.getForObject(
				buildUrl("comments/show.json", commentParams),
				CommentList.class).getComments();
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getComments(long, int, int)
	 */
	@Override
	public List<Comment> getComments(long id, int count, int page) {
		return getComments(id, 0, 0, count, page, 0);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getComments(long)
	 */
	@Override
	public List<Comment> getComments(long id) {
		return getComments(id, 0, 0, 50, 1, 0);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getCommentsBatch(java.lang.Long)
	 */
	@Override
	public List<Comment> getCommentsBatch(Long... ids) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("cids", StringUtils.join(ids));
		return restTemplate.getForObject(
				buildUrl("comments/show_batch.json", commentParams),
				CommentList.class).getComments();
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getCommentsByMe(long, long, long, int, int, int)
	 */
	@Override
	public List<Comment> getCommentsByMe(long sinceId, long maxId,
			int count, int page, int filterBySource) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("since_id", String.valueOf(sinceId));
		commentParams.add("max_id", String.valueOf(maxId));
		commentParams.add("count", String.valueOf(count));
		commentParams.add("page", String.valueOf(page));
		commentParams.add("filter_by_source", String.valueOf(filterBySource));
		return restTemplate.getForObject(
				buildUrl("comments/by_me.json", commentParams),
				CommentList.class).getComments();
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getCommentsByMe(long, int, int)
	 */
	@Override
	public List<Comment> getCommentsByMe(int count, int page) {
		return getCommentsByMe(0, 0, count, page, 0);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getCommentsByMe(long)
	 */
	@Override
	public List<Comment> getCommentsByMe() {
		return getCommentsByMe(50, 1);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getCommentsToMe(long, long, long, int, int, int, int)
	 */
	@Override
	public List<Comment> getCommentsToMe(long sinceId, long maxId,
			int count, int page, int filterByAuthor, int filterBySource) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("since_id", String.valueOf(sinceId));
		commentParams.add("max_id", String.valueOf(maxId));
		commentParams.add("count", String.valueOf(count));
		commentParams.add("page", String.valueOf(page));
		commentParams.add("filter_by_author", String.valueOf(filterByAuthor));
		commentParams.add("filter_by_source", String.valueOf(filterBySource));
		return restTemplate.getForObject(
				buildUrl("comments/to_me.json", commentParams),
				CommentList.class).getComments();
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getCommentsToMe(long, int, int)
	 */
	@Override
	public List<Comment> getCommentsToMe(int count, int page) {
		return getCommentsToMe(0, 0, count, page, 0, 0);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getCommentsToMe(long)
	 */
	@Override
	public List<Comment> getCommentsToMe() {
		return getCommentsToMe(50, 1);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getTimeline(long, long, int, int)
	 */
	@Override
	public List<Comment> getTimeline(long sinceId, long maxId, int count,
			int page) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("since_id", String.valueOf(sinceId));
		commentParams.add("max_id", String.valueOf(maxId));
		commentParams.add("count", String.valueOf(count));
		commentParams.add("page", String.valueOf(page));
		return restTemplate.getForObject(
				buildUrl("comments/timeline.json", commentParams),
				CommentList.class).getComments();
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getTimeline(int, int)
	 */
	@Override
	public List<Comment> getTimeline(int count, int page) {
		return getTimeline(0, 0, count, page);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getTimeline()
	 */
	@Override
	public List<Comment> getTimeline() {
		return getTimeline(50, 1);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getMentions(long, long, int, int, int, int)
	 */
	@Override
	public List<Comment> getMentions(long sinceId, long maxId, int count,
			int page, int filterByAuthor, int filterBySource) {
		requireAuthorization();
		MultiValueMap<String, String> commentParams = new LinkedMultiValueMap<String, String>();
		commentParams.add("since_id", String.valueOf(sinceId));
		commentParams.add("max_id", String.valueOf(maxId));
		commentParams.add("count", String.valueOf(count));
		commentParams.add("page", String.valueOf(page));
		commentParams.add("filter_by_author", String.valueOf(filterByAuthor));
		commentParams.add("filter_by_source", String.valueOf(filterBySource));
		return restTemplate.getForObject(
				buildUrl("comments/mentions.json", commentParams),
				CommentList.class).getComments();
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getMentions(int, int)
	 */
	@Override
	public List<Comment> getMentions(int count, int page) {
		return getMentions(0, 0, count, page, 0, 0);
	}

	/* (non-Javadoc)
	 * @see com.ammob.passport.social.weibo.api.v2.CommentOperations#getMentions()
	 */
	@Override
	public List<Comment> getMentions() {
		return getMentions(50, 1);
	}

	private static class CommentArrayList extends ArrayList<Comment> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8678058509055312875L;

	}

}
