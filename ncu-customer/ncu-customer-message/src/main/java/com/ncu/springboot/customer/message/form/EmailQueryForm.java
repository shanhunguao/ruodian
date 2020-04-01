package com.ncu.springboot.customer.message.form;

import javax.validation.constraints.NotBlank;

import com.ncu.springboot.customer.common.annotation.CheckTimeInterval;

@CheckTimeInterval(startTime = "startTime", endTime = "endTime", message = "查询的开始时间不能大于结束时间")
public class EmailQueryForm {

	@NotBlank(message = "开始时间不能为空")
	private String startTime;

	@NotBlank(message = "结束时间不能为空")
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
