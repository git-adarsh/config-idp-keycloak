package com.auth.ms.resources.dto;

public class ResponseDto {
	private String status;
	private ResponseDesc desc;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseDesc getDesc() {
		return desc;
	}

	public void setDesc(ResponseDesc desc) {
		this.desc = desc;
	}
}
