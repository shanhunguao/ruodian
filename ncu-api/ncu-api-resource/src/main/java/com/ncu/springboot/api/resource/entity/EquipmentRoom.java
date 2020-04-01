package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ncu.springboot.core.annotation.Mark;

import java.io.Serializable;
import javax.persistence.Id;
import java.lang.String;
import java.lang.Integer;
import java.sql.Timestamp;

@Table(name = "tb_equipment_room")
public class EquipmentRoom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROOM_ID")
	@Mark(name = "设备间主键")
	private Integer roomId;
	
	@Mark(name = "设备间名称")
	@Column(name = "ROOM_NAME")
	private String roomName;

	@Mark(name = "负责人")
	@Column(name = "PRINCIPAL")
	private Integer principal;

	@Mark(name = "创建人")
	@Column(name = "CREATE_PERSON_ID")
	private Integer createPersonId;

	@Mark(name = "管理部门")
	@Column(name = "MANAGE_DEPT")
	private Integer manageDept;

	@Mark(name = "状态")
	@Column(name = "STATUS")
	private Integer status;

	@Mark(name = "设备间类型")
	@Column(name = "ROOM_TYPE")
	private Integer roomType;

	@Mark(name = "使用部门")
	@Column(name = "USE_DEPT")
	private Integer useDept;

	@Mark(name = "面积")
	@Column(name = "AREA")
	private String area;

	@Mark(name = "抱杆数")
	@Column(name = "HOLD_BAR_NUM")
	private String holdBarNum;

	@Mark(name = "简介")
	@Column(name = "FUNCTION")
	private String function;

	@Mark(name = "备注")
	@Column(name = "REMARK")
	private String remark;

	@Mark(name = "经度")
	@Column(name = "LONGITUDE")
	private String longitude;

	@Mark(name = "纬度")
	@Column(name = "LATITUDE")
	private String latitude;

	@Mark(name = "修改时间")
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	@Mark(name = "是否租赁")
	@Column(name = "IS_RENT")
	private Integer isRent;

	@Mark(name = "创建时间")
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Mark(name = "所在层数")
	@Column(name = "FLOOR")
	private Integer floor;

	@Mark(name = "漏洞主键")
	@Column(name = "BUILDING_ID")
	private Integer buildingId;

	@Mark(name = "园区主键")
	@Column(name = "CAMPUS_ID")
	private Integer campusId;

	@Mark(name = "修改人")
	@Column(name = "UPDATE_PERSON_ID")
	private Integer updatePersonId;

	@Mark(name = "设备间编号")
	@Column(name = "ROOM_CODE")
	private String roomCode;
	
	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

	public Integer getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(Integer createPersonId) {
		this.createPersonId = createPersonId;
	}

	public Integer getManageDept() {
		return manageDept;
	}

	public void setManageDept(Integer manageDept) {
		this.manageDept = manageDept;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Integer getUseDept() {
		return useDept;
	}

	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHoldBarNum() {
		return holdBarNum;
	}

	public void setHoldBarNum(String holdBarNum) {
		this.holdBarNum = holdBarNum;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsRent() {
		return isRent;
	}

	public void setIsRent(Integer isRent) {
		this.isRent = isRent;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	@Override
	public String toString() {
		return "EquipmentRoom [roomId=" + roomId + ", roomName=" + roomName + ", principal=" + principal
				+ ", createPersonId=" + createPersonId + ", manageDept=" + manageDept + ", status=" + status
				+ ", roomType=" + roomType + ", useDept=" + useDept + ", area=" + area + ", holdBarNum=" + holdBarNum
				+ ", function=" + function + ", remark=" + remark + ", longitude=" + longitude + ", latitude="
				+ latitude + ", updateTime=" + updateTime + ", isRent=" + isRent + ", createTime=" + createTime
				+ ", floor=" + floor + ", buildingId=" + buildingId + ", campusId=" + campusId + ", updatePersonId="
				+ updatePersonId + ", roomCode=" + roomCode + "]";
	}

}