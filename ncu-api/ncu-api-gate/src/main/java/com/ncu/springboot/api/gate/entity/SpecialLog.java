package com.ncu.springboot.api.gate.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Id;
import com.ncu.springboot.core.annotation.Mark;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "gate_special_log")
public class SpecialLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Mark(name = "")
	@Column(name = "id")
	private Integer id;

	@Mark(name = "")
	@Column(name = "remark")
	private String remark;

	@Mark(name = "")
	@Column(name = "img_path1")
	private String imgPath1;

	@Mark(name = "")
	@Column(name = "img_path2")
	private String imgPath2;

	@Mark(name = "")
	@Column(name = "img_path3")
	private String imgPath3;

	@Mark(name = "")
	@Column(name = "gate_id")
	private String gateId;

	@Mark(name = "")
	@Column(name = "guard_id")
	private String guardId;

	@Mark(name = "")
	@Column(name = "create_time")
	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImgPath1() {
		return imgPath1;
	}

	public void setImgPath1(String imgPath1) {
		this.imgPath1 = imgPath1;
	}

	public String getImgPath2() {
		return imgPath2;
	}

	public void setImgPath2(String imgPath2) {
		this.imgPath2 = imgPath2;
	}

	public String getImgPath3() {
		return imgPath3;
	}

	public void setImgPath3(String imgPath3) {
		this.imgPath3 = imgPath3;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getGuardId() {
		return guardId;
	}

	public void setGuardId(String guardId) {
		this.guardId = guardId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SpecialLog [id=" + id + ", remark=" + remark + ", imgPath1=" + imgPath1 + ", imgPath2=" + imgPath2
				+ ", imgPath3=" + imgPath3 + ", gateId=" + gateId + ", guardId=" + guardId + ", createTime="
				+ createTime + "]";
	}

}