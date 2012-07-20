package com.ammob.passport.social.weibo.api;

import java.util.List;


public interface CommentOperations {
	/**
	 * 评论一条微博
	 * @param id 需要评论的微博ID。
	 * @param message 评论内容，必须做URLencode，内容不超过140个汉字。 
	 * @param commentOri 当评论转发微博时，是否评论给原微博，0：否、1：是。 
	 * @return
	 */
	public abstract Comment createComment(long id, String message,
			int commentOri);
	/**
	 * 评论一条微博
	 * @param id 需要评论的微博ID。
	 * @param message 评论内容，必须做URLencode，内容不超过140个汉字。 
	 * @return
	 */
	public abstract Comment createComment(long id, String message);
	/**
	 * 删除一条评论
	 * @param id 要删除的评论ID，只能删除登录用户自己发布的评论。 
	 * @return
	 */
	public abstract Comment destroyComment(long id);
	/**
	 * 批量删除评论
	 * @param ids 需要删除的评论ID，最多20个，只能删除登录用户自己发布的评论。 
	 * @return
	 */
	public abstract List<Comment> destroyComment(Long... ids);
	/**
	 * 回复一条微博
	 * @param id 	需要评论的微博ID。
	 * @param cid 需要回复的评论ID。
	 * @param comment 回复评论内容，必须做URLencode，内容不超过140个汉字。
	 * @param withoutMention 回复中是否自动加入“回复@用户名”，0：是、1：否
	 * @param commentOri 当评论转发微博时，是否评论给原微博，0：否、1：是
	 * @return
	 */
	public abstract Comment replyComment(long id, long cid, String comment,
			int withoutMention, int commentOri);
	/**
	 * 回复一条微博
	 * @param id 	需要评论的微博ID。
	 * @param cid 需要回复的评论ID。
	 * @param comment 回复评论内容，必须做URLencode，内容不超过140个汉字。
	 * @return
	 */
	public abstract Comment replyComment(long id, long cid, String comment);
	/**
	 * 获取某条微博的评论列表
	 * @param id 需要查询的微博ID。 
	 * @param sinceId 返回ID比since_id大的评论（即比since_id时间晚的评论）
	 * @param maxId 返回ID小于或等于max_id的评论
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @param filterByAuthor 作者筛选类型，0：全部、1：我关注的人、2：陌生人
	 * @return
	 */
	public abstract List<Comment> getComments(long id, long sinceId,
			long maxId, int count, int page, int filterByAuthor);
	/**
	 * 获取某条微博的评论列表
	 * @param id 需要查询的微博ID。 
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Comment> getComments(long id, int count, int page);
	/**
	 * 获取某条微博的评论列表
	 * @param id 需要查询的微博ID。 
	 * @return
	 */
	public abstract List<Comment> getComments(long id);
	/**
	 * 批量获取评论内容
	 * @param ids 需要查询的批量评论ID，最大50。 
	 * @return
	 */
	public abstract List<Comment> getCommentsBatch(Long... ids);
	/**
	 * 我发出的评论列表
	 * @param sinceId 返回ID比since_id大的评论（即比since_id时间晚的评论）
	 * @param maxId 返回ID小于或等于max_id的评论
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @param filterBySource 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论
	 * @return
	 */
	public abstract List<Comment> getCommentsByMe(long sinceId,
			long maxId, int count, int page, int filterBySource);
	/**
	 * 我发出的评论列表
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Comment> getCommentsByMe(int count, int page);
	/**
	 * 我发出的评论列表
	 * @return
	 */
	public abstract List<Comment> getCommentsByMe();
	/**
	 * 我收到的评论列表
	 * @param sinceId 返回ID比since_id大的评论（即比since_id时间晚的评论）
	 * @param maxId 返回ID小于或等于max_id的评论
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @param filterByAuthor 作者筛选类型，0：全部、1：我关注的人、2：陌生人
	 * @param filterBySource 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论
	 * @return
	 */
	public abstract List<Comment> getCommentsToMe(long sinceId,
			long maxId, int count, int page, int filterByAuthor,
			int filterBySource);
	/**
	 * 我收到的评论列表
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Comment> getCommentsToMe(int count, int page);
	/**
	 * 我收到的评论列表
	 * @return
	 */
	public abstract List<Comment> getCommentsToMe();
	/**
	 * 获取用户发送及收到的评论列表
	 * @param sinceId 返回ID比since_id大的评论（即比since_id时间晚的评论）
	 * @param maxId 返回ID小于或等于max_id的评论
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Comment> getTimeline(long sinceId, long maxId,
			int count, int page);
	/**
	 * 获取用户发送及收到的评论列表
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Comment> getTimeline(int count, int page);
	/**
	 * 获取用户发送及收到的评论列表
	 * @return
	 */
	public abstract List<Comment> getTimeline();
	/**
	 * 获取@到我的评论
	 * @param sinceId 返回ID比since_id大的评论（即比since_id时间晚的评论）
	 * @param maxId 返回ID小于或等于max_id的评论
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @param filterByAuthor 作者筛选类型，0：全部、1：我关注的人、2：陌生人
	 * @param filterBySource 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论
	 * @return
	 */
	public abstract List<Comment> getMentions(long sinceId, long maxId,
			int count, int page, int filterByAuthor, int filterBySource);
	/**
	 * 获取@到我的评论
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Comment> getMentions(int count, int page);
	/**
	 * 获取@到我的评论
	 * @return
	 */
	public abstract List<Comment> getMentions();

}