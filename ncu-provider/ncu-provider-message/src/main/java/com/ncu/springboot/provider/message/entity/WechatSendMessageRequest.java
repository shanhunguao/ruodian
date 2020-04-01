package com.ncu.springboot.provider.message.entity;

import java.util.List;

public class WechatSendMessageRequest {
	
	private String touser;
	
	private String toparty;
	
	private String totag;
	
	private String msgtype;//
	
	private Integer agentid;//
	
	private List<Article> articles;
	
	private Text text;
	
	private List<Integer> media_id;
	
	private String safe;
	
	private String enable_id_trans;
	
	private String enable_duplicate_check;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getTotag() {
		return totag;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Integer getAgentid() {
		return agentid;
	}

	public void setAgentid(Integer agentid) {
		this.agentid = agentid;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public List<Integer> getMedia_id() {
		return media_id;
	}

	public void setMedia_id(List<Integer> media_id) {
		this.media_id = media_id;
	}

	public String getSafe() {
		return safe;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}

	public String getEnable_id_trans() {
		return enable_id_trans;
	}

	public void setEnable_id_trans(String enable_id_trans) {
		this.enable_id_trans = enable_id_trans;
	}

	public String getEnable_duplicate_check() {
		return enable_duplicate_check;
	}

	public void setEnable_duplicate_check(String enable_duplicate_check) {
		this.enable_duplicate_check = enable_duplicate_check;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentid == null) ? 0 : agentid.hashCode());
		result = prime * result + ((articles == null) ? 0 : articles.hashCode());
		result = prime * result + ((enable_duplicate_check == null) ? 0 : enable_duplicate_check.hashCode());
		result = prime * result + ((enable_id_trans == null) ? 0 : enable_id_trans.hashCode());
		result = prime * result + ((media_id == null) ? 0 : media_id.hashCode());
		result = prime * result + ((msgtype == null) ? 0 : msgtype.hashCode());
		result = prime * result + ((safe == null) ? 0 : safe.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((toparty == null) ? 0 : toparty.hashCode());
		result = prime * result + ((totag == null) ? 0 : totag.hashCode());
		result = prime * result + ((touser == null) ? 0 : touser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WechatSendMessageRequest other = (WechatSendMessageRequest) obj;
		if (agentid == null) {
			if (other.agentid != null)
				return false;
		} else if (!agentid.equals(other.agentid))
			return false;
		if (articles == null) {
			if (other.articles != null)
				return false;
		} else if (!articles.equals(other.articles))
			return false;
		if (enable_duplicate_check == null) {
			if (other.enable_duplicate_check != null)
				return false;
		} else if (!enable_duplicate_check.equals(other.enable_duplicate_check))
			return false;
		if (enable_id_trans == null) {
			if (other.enable_id_trans != null)
				return false;
		} else if (!enable_id_trans.equals(other.enable_id_trans))
			return false;
		if (media_id == null) {
			if (other.media_id != null)
				return false;
		} else if (!media_id.equals(other.media_id))
			return false;
		if (msgtype == null) {
			if (other.msgtype != null)
				return false;
		} else if (!msgtype.equals(other.msgtype))
			return false;
		if (safe == null) {
			if (other.safe != null)
				return false;
		} else if (!safe.equals(other.safe))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (toparty == null) {
			if (other.toparty != null)
				return false;
		} else if (!toparty.equals(other.toparty))
			return false;
		if (totag == null) {
			if (other.totag != null)
				return false;
		} else if (!totag.equals(other.totag))
			return false;
		if (touser == null) {
			if (other.touser != null)
				return false;
		} else if (!touser.equals(other.touser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WechatSendMessageRequest [touser=" + touser + ", toparty=" + toparty + ", totag=" + totag + ", msgtype="
				+ msgtype + ", agentid=" + agentid + ", articles=" + articles + ", text=" + text + ", media_id="
				+ media_id + ", safe=" + safe + ", enable_id_trans=" + enable_id_trans + ", enable_duplicate_check="
				+ enable_duplicate_check + "]";
	}

}
