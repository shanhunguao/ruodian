package com.ncu.springboot.api.resource.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_file")
public class ResourceFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FID")
	private Integer fid;

	@Column(name = "FILENAME")
	private String filename;

	@Column(name = "FILERNAME")
	private String filername;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "OBJECT_ID")
	private String objectId;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Column(name = "CREATE_USER_ID")
	private String createUserId;

	@Column(name = "CREATE_USERNAME")
	private String createUsername;

	@Column(name = "FILE_FLAG")
	private String fileFlag;

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilername() {
		return filername;
	}

	public void setFilername(String filername) {
		this.filername = filername;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getFileFlag() {
		return fileFlag;
	}

	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}

	@Override
	public String toString() {
		return "File [fid=" + fid + ", filename=" + filename + ", filername=" + filername + ", filePath=" + filePath
				+ ", type=" + type + ", objectId=" + objectId + ", createTime=" + createTime + ", createUserId="
				+ createUserId + ", createUsername=" + createUsername + ", fileFlag=" + fileFlag + "]";
	}

}