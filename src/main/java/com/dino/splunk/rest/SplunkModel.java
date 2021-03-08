package com.dino.splunk.rest;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
/*import com.splunk.ResponseMessage; */

import lombok.Data;

@Data
public class SplunkModel {

	
	private final long id;
	
	@JsonInclude
	@JsonDeserialize
	private final Object content;
   
	
	public SplunkModel(long id, Object i) {
		this.id = id;
		this.content = i;
	}

	public SplunkModel(long id, InputStream content) {
		this.id = id;
		this.content = content;
	}

	public SplunkModel(long id, String token) {
	 this.id= id;
	 this.content = token;
	}

	public long getId() {
		return id;
	}

	public Object getContent() {
		return content;
	} 
}
