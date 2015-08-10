package com.sirius.skymall.redis.vo;


/**
 * app 行为统计
 * 
 * @author zzpeng
 *
 */
public class AppLog {
	private String createTime;// 统计时间点
	private String content;
	private String fromUser;
	private String toUser;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "from_user:" + getFromUser() + ", to_user:" + getToUser()
				+ ", createTime:" + getCreateTime() + ",  content:"
				+ getContent();
	}
}
