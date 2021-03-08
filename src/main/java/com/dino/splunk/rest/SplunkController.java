package com.dino.splunk.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.annotation.WebInitParam;
import javax.websocket.server.PathParam;

import org.apache.tomcat.util.json.JSONParser;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*import com.splunk.Args;
import com.splunk.EntityCollection;
import com.splunk.Job;
import com.splunk.JobEventsArgs; */

import lombok.extern.slf4j.Slf4j;

@Controller
@RestController
@Slf4j
public class SplunkController {

	private final AtomicLong counter = new AtomicLong();
     
	@Autowired
	private SplunkServiceImpl splunkServiceImpl;
	
  
	
	/*@RequestMapping("/token")
	public SplunkModel splunkapptoken(@RequestParam(value="name", defaultValue="SplunkServiceImpl") String name) {
		return new SplunkModel(counter.incrementAndGet(),
							splunkServiceImpl.splunkconnection().getToken());
	}

	
	@RequestMapping("/statuscode")
	public SplunkModel splunkdatamodelscode(@RequestParam(value="name", defaultValue="SplunkServiceImpl") Object name) throws IOException {
		return new SplunkModel(counter.incrementAndGet(),
							splunkServiceImpl.splunkconnection().getDataModels().getService().getJobs().list().getStatus());
	} */
	
	
	
	@RequestMapping("/splunkresttemplate")
	public SplunkModel splunkresttemplate(@RequestParam(value="name", defaultValue="SplunkServiceImpl") @RequestBody Object name) throws IOException, InterruptedException, InstantiationException, IllegalAccessException {
		return new SplunkModel(counter.incrementAndGet(),
							splunkServiceImpl.splunksearch());
		
	}
	
	@RequestMapping("/splunkcollectiondata")
	public ResponseEntity<String> splunkcollectiondata() throws IOException, InterruptedException, InstantiationException, IllegalAccessException, URISyntaxException {
		return splunkServiceImpl.getsplunkcollection();
		
	}
	
	
	
	
	/*@RequestMapping(value="/searchjob",method=RequestMethod.POST)
	public @ResponseBody SplunkModel splunkappsearch(@RequestParam(value="name", defaultValue="SplunkServiceImpl") Object name , @PathParam(value = "search") String search) throws IOException {
		return new SplunkModel(counter.incrementAndGet(),
							splunkServiceImpl.splunkjobs(search));
	}
	
	@RequestMapping(value="/searchcr",method = RequestMethod.POST)
	public @ResponseBody Object splunkretsearch(@RequestParam(value="name", defaultValue="SplunkServiceImpl") Object name , @PathParam(value = "search") String search) throws IOException {
	
		return 	 splunkServiceImpl.eventlogging(search);
	}
	
*/

	
	
}
