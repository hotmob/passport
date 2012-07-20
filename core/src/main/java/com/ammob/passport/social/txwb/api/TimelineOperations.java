/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import java.util.Date;
import java.util.List;

import org.springframework.core.io.Resource;

/**
 * @author iday
 * 
 */
public interface TimelineOperations {
	/**
	 * 话题时间线
	 * 
	 * @param pageflag
	 *            翻页标识(第一次务必填零) pageflag=1表示向下翻页：tweetid和time是上一页的最后一个帖子ID和时间；
	 *            pageflag=2表示向上翻页：tweetid和time是下一页的第一个帖子ID和时间；
	 * @param time
	 *            微博帖子生成时间
	 * @param reqnum
	 *            请求数量（1-100）
	 * @param flag
	 *            是否拉取认证用户，用作筛选。0-拉取所有用户，0x01-拉取认证用户
	 * @param tweetid
	 *            微博帖子ID
	 * @param httext
	 *            话题内容，长度有限制。
	 * @param htid
	 *            话题ID htid和httext这两个参数不能同时填写，如果都填写只拉取htid指定的微博，如果要拉取指定话题的微博，
	 *            请务必让htid为0
	 * @param type
	 *            1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-点评
	 * @param contentType
	 *            正文类型（按位使用）。1-带文本(这一位一定有)，2-带链接，4-带图片，8-带视频，0x10-带音频
	 * @return
	 */
	public abstract List<Tweet> getHtTimeline(int pageflag, Date time,
			int reqnum, int flag, long tweetid, String httext, long htid,
			int type, int contentType);

	/**
	 * 获取二传手列表
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-50条）
	 * @param rootid
	 *            二传手转发或者回复查询节点的父亲结点id
	 * @param type
	 *            1-转发，2-点评
	 * @param lastid
	 *            和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：
	 *            填上一次请求返回的最后一条记录id
	 * @return
	 */
	public abstract List<Tweet> getRetweetTimeline(int pageflag, Date pagetime,
			int reqnum, long rootid, int type, long lastid);

	/**
	 * 拉取vip用户发表微博消息接口
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-70条）
	 * @param lastid
	 *            用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：
	 *            填上一次请求返回的最后一条记录id） 如果用户不指定该参数，默认为0
	 * @return
	 */
	public abstract List<Tweet> getVipTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid);

	/**
	 * 地区发表时间线
	 * 
	 * @param pos
	 *            记录的起始位置（第一次请求时填0，继续请求时填上次请求返回的pos）
	 * @param reqnum
	 *            每次请求记录的条数（1-100条）
	 * @param country
	 *            国家码
	 * @param province
	 *            省份码
	 * @param city
	 *            城市码
	 * @return
	 */
	public abstract List<Tweet> getAreaTimeline(int pos, int reqnum,
			String country, String province, String city);

	/**
	 * 特别收听的人发表时间线
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-100条）
	 * @param lastid
	 *            用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：
	 *            填上一次请求返回的最后一条记录id）
	 * @param type
	 *            拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	 *            如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	 * @param contentType
	 *            内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	 * @return
	 */
	public abstract List<Tweet> getSpecialTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid, int type, int contentType);

	/**
	 * 我发表时间线
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-200条）
	 * @param lastid
	 *            和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：
	 *            填上一次请求返回的最后一条记录id）
	 * @param type
	 *            拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	 *            如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	 * @param contentType
	 *            内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	 * @return
	 */
	public abstract List<Tweet> getBroadcastTimeline(int pageflag,
			Date pagetime, int reqnum, long lastid, int type, int contentType);

	/**
	 * 用户提及时间线
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-70条）
	 * @param lastid
	 *            用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：
	 *            填上一次请求返回的最后一条记录id）
	 * @param type
	 *            拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	 *            如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	 * @param contentType
	 *            内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	 * @return
	 */
	public abstract List<Tweet> getMentionsTimeline(int pageflag,
			Date pagetime, int reqnum, long lastid, int type, int contentType);

	/**
	 * 多用户发表时间线
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-70条）
	 * @param lastid
	 *            和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：
	 *            填上一次请求返回的最后一条记录id）
	 * @param names
	 *            你需要读取用户列表数组
	 * @param type
	 *            拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	 *            如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	 * @param contentType
	 *            内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	 * @return
	 */
	public abstract List<Tweet> getUsersTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid, String[] names, int type, int contentType);

	/**
	 * 其他用户发表时间线
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-70条）
	 * @param lastid
	 *            用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：
	 *            填上一次请求返回的最后一条记录id）
	 * @param name
	 *            你需要读取的用户的用户名
	 * @param type
	 *            拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	 *            如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	 * @param contentType
	 *            内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(int pageflag, Date pagetime,
			int reqnum, long lastid, String name, int type, int contentType);

	/**
	 * 广播大厅时间线
	 * 
	 * @param pos
	 *            记录的起始位置（第一次请求时填0，继续请求时填上次请求返回的pos）
	 * @param reqnum
	 *            每次请求记录的条数（1-100条）
	 * @return
	 */
	public abstract List<Tweet> getPublicTimeline(int pos, int reqnum);

	/**
	 * 主页时间线
	 * 
	 * @param pageflag
	 *            分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime
	 *            本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum
	 *            每次请求记录的条数（1-70条）
	 * @param type
	 *            拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	 *            如需拉取多个类型请使用|，如(0x1|0x2)得到3，此时type=3即可，填零表示拉取所有类型
	 * @param contentType
	 *            内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	 * @return
	 */
	public abstract List<Tweet> getHomeTimeline(int pageflag, Date pagetime,
			int reqnum, int type, int contentType);

	/**
	 * 根据微博id批量获取微博内容
	 * 
	 * @param ids
	 *            微博id列表，用“,”隔开，如123456,456789（请求数量不要超过100个）
	 * @return
	 */
	public abstract List<Tweet> getBatchTimeline(Long... ids);

	/**
	 * 点评一条微博
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param reid
	 *            点评根结点（非父结点）微博id
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void commentStatus(String content, String clientip,
			long reid, float jing, float wei);

	/**
	 * 回复一条微博
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param reid
	 *            点评根结点（非父结点）微博id
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void replyStatus(String content, String clientip,
			long reid, float jing, float wei);

	/**
	 * 转播一条微博
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param reid
	 *            点评根结点（非父结点）微博id
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void retweetStatus(String content, String clientip,
			long reid, float jing, float wei);

	/**
	 * 删除一条微博
	 * 
	 * @param id
	 *            微博id
	 */
	public abstract void delStatus(long id);

	/**
	 * 发表视频微博
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param url
	 *            视频地址，后台自动分析视频信息，支持youku,tudou,ku6
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void addVideoStatus(String content, String clientip,
			String url, float jing, float wei);

	/**
	 * 发表音乐微博
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param url
	 *            音乐地址
	 * @param title
	 *            音乐名
	 * @param author
	 *            演唱者
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void addMusicStatus(String content, String clientip,
			String url, String title, String author, float jing, float wei);

	/**
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param picUrl
	 *            图片的URL地址（URL最长不能超过1024字节）
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void addPicStatus(String content, String clientip,
			String picUrl, float jing, float wei);

	/**
	 * 发表一条带图片的微博
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param pic
	 *            文件域表单名。
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void addPicStatus(String content, String clientip,
			Resource pic, float jing, float wei);

	/**
	 * 发表心情帖子
	 * 
	 * @param content
	 *            心情微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param signtype
	 *            心情类型（1，2，3，4，5）
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void addEmotionStatus(String content, String clientip,
			int signtype, float jing, float wei);

	/**
	 * 发表一条微博
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void addStatus(String content, String clientip, float jing,
			float wei);

	/**
	 * 获取一条微博数据
	 * 
	 * @param id
	 *            微博id
	 * @return
	 */
	public abstract Tweet getStatus(long id);

	/**
	 * 多类型发表
	 * 
	 * @param content
	 *            微博内容
	 * @param clientip
	 *            用户ip(以分析用户所在地)
	 * @param picUrl
	 *            图片URL，可填空（URL最长为1024字节）
	 * @param videoUrl
	 *            视频URL，可填空（URL最长为1024字节）
	 * @param musicUrl
	 *            音乐URL，可填空（如果填写该字段，则music_title和music_author都必须填写）
	 * @param musicTitle
	 *            歌曲名 （UTF8编码）
	 * @param musicAuthor
	 *            歌曲作者（UTF8编码）
	 * @param jing
	 *            经度（可以填空）
	 * @param wei
	 *            纬度（可以填空）
	 */
	public abstract void addMultiStatus(String content, String clientip,
			String picUrl, String videoUrl, String musicUrl, String musicTitle,
			String musicAuthor, float jing, float wei);

}
