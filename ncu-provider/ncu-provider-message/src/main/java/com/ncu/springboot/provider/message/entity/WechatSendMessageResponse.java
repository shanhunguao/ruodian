package com.ncu.springboot.provider.message.entity;

public class WechatSendMessageResponse {

	private Integer errcode;
	
	private String errmsg;
	
	private String invaliduser;
	
	private String invalidparty;
	
	private String invalidtag;
	
	

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getInvaliduser() {
		return invaliduser;
	}

	public void setInvaliduser(String invaliduser) {
		this.invaliduser = invaliduser;
	}

	public String getInvalidparty() {
		return invalidparty;
	}

	public void setInvalidparty(String invalidparty) {
		this.invalidparty = invalidparty;
	}

	public String getInvalidtag() {
		return invalidtag;
	}

	public void setInvalidtag(String invalidtag) {
		this.invalidtag = invalidtag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errcode == null) ? 0 : errcode.hashCode());
		result = prime * result + ((errmsg == null) ? 0 : errmsg.hashCode());
		result = prime * result + ((invalidparty == null) ? 0 : invalidparty.hashCode());
		result = prime * result + ((invalidtag == null) ? 0 : invalidtag.hashCode());
		result = prime * result + ((invaliduser == null) ? 0 : invaliduser.hashCode());
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
		WechatSendMessageResponse other = (WechatSendMessageResponse) obj;
		if (errcode == null) {
			if (other.errcode != null)
				return false;
		} else if (!errcode.equals(other.errcode))
			return false;
		if (errmsg == null) {
			if (other.errmsg != null)
				return false;
		} else if (!errmsg.equals(other.errmsg))
			return false;
		if (invalidparty == null) {
			if (other.invalidparty != null)
				return false;
		} else if (!invalidparty.equals(other.invalidparty))
			return false;
		if (invalidtag == null) {
			if (other.invalidtag != null)
				return false;
		} else if (!invalidtag.equals(other.invalidtag))
			return false;
		if (invaliduser == null) {
			if (other.invaliduser != null)
				return false;
		} else if (!invaliduser.equals(other.invaliduser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WechatSendMessageResponse [errcode=" + errcode + ", errmsg=" + errmsg + ", invaliduser=" + invaliduser
				+ ", invalidparty=" + invalidparty + ", invalidtag=" + invalidtag + "]";
	}

}
