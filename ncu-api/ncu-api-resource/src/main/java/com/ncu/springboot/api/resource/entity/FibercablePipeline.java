package com.ncu.springboot.api.resource.entity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.Integer;

@Table(name = "tb_fibercable_pipeline")
public class FibercablePipeline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "FIBERCABLE_ID")
	private Integer fibercableId;

	@Column(name = "PIPELINE_ID")
	private Integer pipelineId;

	public Integer getFibercableId() {
		return fibercableId;
	}

	public void setFibercableId(Integer fibercableId) {
		this.fibercableId = fibercableId;
	}

	public Integer getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(Integer pipelineId) {
		this.pipelineId = pipelineId;
	}

	@Override
	public String toString() {
		return "FibercablePipeline [fibercableId=" + fibercableId + ", pipelineId=" + pipelineId + "]";
	}
	
}