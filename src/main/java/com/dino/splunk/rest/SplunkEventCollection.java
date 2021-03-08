package com.dino.splunk.rest;


import lombok.Data;

@Data
public class SplunkEventCollection {

	private String Task_Name;

	private String Task_Description;

	private String Estimated_Completion_Date;

	private String Notes;

	private String Status;

	private String _user ;

	private String _key;
	
}
