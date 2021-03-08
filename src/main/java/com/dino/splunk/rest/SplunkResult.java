package com.dino.splunk.rest;


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonDeserialize
@JsonPOJOBuilder
@JsonSerialize
public class SplunkResult   {
    @JsonProperty
	private boolean preview;
	@JsonProperty
	private int offset;
	@JsonProperty
	private boolean lastrow;
	@JsonProperty
	private Map<String,String> result;
	
}
