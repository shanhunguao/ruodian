package com.ncu.springboot.provider.message.entity;

public class WeChatTokenResponse {
	
	private Integer errcode;
	
	private String errmsg;
	
	private String access_token;
	
	//有效时间
	private String expires_in;

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

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	@Override
	public String toString() {
		return "WeChatTokenResponse [errcode=" + errcode + ", errmsg=" + errmsg + ", access_token=" + access_token
				+ ", expires_in=" + expires_in + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((access_token == null) ? 0 : access_token.hashCode());
		result = prime * result + ((errcode == null) ? 0 : errcode.hashCode());
		result = prime * result + ((errmsg == null) ? 0 : errmsg.hashCode());
		result = prime * result + ((expires_in == null) ? 0 : expires_in.hashCode());
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
		WeChatTokenResponse other = (WeChatTokenResponse) obj;
		if (access_token == null) {
			if (other.access_token != null)
				return false;
		} else if (!access_token.equals(other.access_token))
			return false;
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
		if (expires_in == null) {
			if (other.expires_in != null)
				return false;
		} else if (!expires_in.equals(other.expires_in))
			return false;
		return true;
	}
	
}
