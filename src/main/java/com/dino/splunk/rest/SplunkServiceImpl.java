package com.dino.splunk.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpRequestFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/*import com.splunk.Args;
import com.splunk.InputKind;
import com.splunk.Job;
import com.splunk.SSLSecurityProtocol;
import com.splunk.Service;
import com.splunk.ServiceArgs;
import com.splunk.TcpInput; */
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.qos.logback.core.net.ssl.KeyManagerFactoryFactoryBean;
import ch.qos.logback.core.net.ssl.SSLConfiguration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@XRayEnabled
public class SplunkServiceImpl {

	@Autowired
	private RestTemplate rt;
	private Function<String,String> valueMapper;
	private Function<String,String> keyMapper;
	private Object myEntityRepository;
     
/*	public Service splunkconnection() {
		   KeyManagerFactoryFactoryBean keyManagerFactoryFactoryBean = new KeyManagerFactoryFactoryBean();
			 SSLConfiguration sslConfiguration  = new SSLConfiguration();
			 keyManagerFactoryFactoryBean.setAlgorithm("AES-256-CBC");
			 sslConfiguration.setKeyManagerFactory(keyManagerFactoryFactoryBean);
			 ServiceArgs loginArgs = new ServiceArgs();
	         loginArgs.setUsername("admin");
	         loginArgs.setPassword("splunk123");
	         loginArgs.setHost("localhost");
	         loginArgs.setPort(8089);
	         loginArgs.setSSLSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
	         loginArgs.setApp("search");
	         loginArgs.setScheme("https");
	         loginArgs.setToken("1bff9765-8eab-428e-a9af-06078fd85e8c");
	         Service service =  Service.connect(loginArgs);
	         service.logout();
	         service.login();
			return service;
		}
	
	public Job splunkjobs(String search) {
		   KeyManagerFactoryFactoryBean keyManagerFactoryFactoryBean = new KeyManagerFactoryFactoryBean();
			 SSLConfiguration sslConfiguration  = new SSLConfiguration();
			 keyManagerFactoryFactoryBean.setAlgorithm("AES-256-CBC");
			 sslConfiguration.setKeyManagerFactory(keyManagerFactoryFactoryBean);
			 ServiceArgs loginArgs = new ServiceArgs();
	         loginArgs.setUsername("admin");
	         loginArgs.setPassword("splunk123");
	         loginArgs.setHost("localhost");
	         loginArgs.setPort(8089);
	         loginArgs.setSSLSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
	         loginArgs.setApp("search");
	         loginArgs.setScheme("https");
	         loginArgs.setToken("1bff9765-8eab-428e-a9af-06078fd85e8c");
	         Service service =  Service.connect(loginArgs); 
		Job job = service.getJobs().create(search);
	    while(!job.isDone())
	      {
	    	  log.info("Inside job Streaming");
	    	  try {
	    		  Thread.sleep(500);
	    		  log.info("Thread setter");
	    	  }
	    	  catch(Exception e) {log.error(e.getMessage());}
	
	}
		return job;
}
	*/

	int SafeParse(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
		
	}

	
	public ResponseEntity<String> getsplunkcollection() throws URISyntaxException {
		URI uri = new URIBuilder("http://ec2-3-17-177-197.us-east-2.compute.amazonaws.com:8089/servicesNS/nobody/search/storage/collections/data/task_collection").build();
		ResponseEntity<String> datateststream  ;
		rt.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "splunk123"));
		 datateststream = rt.exchange(uri, HttpMethod.GET, null,String.class);


		return datateststream;
		
	}
	
	public List<String> splunksearch() throws IOException, InterruptedException, InstantiationException, IllegalAccessException {
		    		HttpHeaders headers = new HttpHeaders();
		    		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		    		headers.setBasicAuth("admin", "splunk123");
		    		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    		params.add("search", "search index=main | head 3  | table host,source,sourcetype,_raw ") ;
		    		params.add("output_mode","json");
		    		HttpEntity<MultiValueMap<String, String>> sprequest = new HttpEntity<>(params, headers);
		    	
	   Map<String,String> map = new HashMap<String,String>();
	   Map<String,String> keyvalmap = new HashMap<String,String>();
	     String uri = "http://ec2-3-17-177-197.us-east-2.compute.amazonaws.com:8089/services/search/jobs/export"; 
	   /*  ResponseEntity<SplunkResult> inputstream  ;
	     HttpClient httpClient = HttpClientBuilder.create().build();
	     inputstream = rt.exchange(uri,HttpMethod.POST, sprequest, SplunkResult.class); 
	     do{
	      log.info("Streaming Splunk Search" );
	     }
	     while(inputstream.getBody().getOffset()>0&&inputstream.getBody().getOffset()<35); 
	 

		 map = inputstream.getBody().getResult();
	 do{
		
		  for(Map.Entry<String,String> k: map.entrySet()) {
				 log.info("Key"+"::" + k +"=>"+"Value"+"::"+map.get(k));
					 
				 keyvalmap.put(k.getKey(),k.getValue());
				 if(k.getKey() == "SplunkErrors")	
				 {
					log.info("SplunkErrors as Integer::" +SafeParse(k.getValue()));

				 }
				
				 log.info("KVMap of objects is " +keyvalmap);
		  }
	
		}		while(inputstream.getBody().getResult().entrySet().iterator().hasNext()==false);
		

		log.info("Splunk Search Map final  is  " +map);
		//return inputstream.getBody().getResult(); */
		 ResponseEntity<String> datateststream  ;
		 datateststream = rt.exchange(uri,HttpMethod.POST, sprequest, String.class); 
		 //log.info("Search Result Array is" + datateststream.getBody());
		 Map<String,String> tmap = new HashMap<String,String>();
			ObjectMapper om = new ObjectMapper();
			tmap = om.convertValue(datateststream,Map.class);
		 //log.info("Content is " + tmap.get("body").toString());
		 Map<String,String>  lmap = new HashMap<String,String>();
		 ObjectMapper omap = new ObjectMapper();
			
		 for(Map.Entry<String, String> k : tmap.entrySet())
		 {
			 if(k.getKey().equals("body"))
			 {
				
				
			 lmap.put(k.getKey(), k.getValue());
			 }
			
		 }
		 
		 Map<String,String> rmap = new HashMap<String,String>();
		 Map<String,String> nmap = new HashMap<String,String>();
		 List<String> lstring = new ArrayList<>();
		 try {
			 log.info("Json is " +tmap.get("body").toString());
			 
			 String[] arr = tmap.get("body").toString().split("\n");
			 
			 for(String s: arr)
			 {
				 log.info("Iterate over Array"+s);
				
				 lstring.add(s);
				 rmap = omap.readValue(s,Map.class);
				 log.info("Json Equival of a Map is " +rmap);
				 
			 }
			 
			 
			 
	        for(String o : lstring)
	        {
			 log.info("Result of ListString  As Array  is " +o);
			 nmap.put(lstring.get(0),lstring.get(1));
			 
			
	        }
			  //rmap = omap.readValue(tmap.get("body").toString(),Map.class);
		
		 }
		 catch(IOException e)
		 {
			 e.printStackTrace();
		 }

	   log.info("Nmap:" +nmap);
		//return lmap; 
		 return lstring;
	}
	
	/*public Object eventlogging(String search) throws IOException {
		   KeyManagerFactoryFactoryBean keyManagerFactoryFactoryBean = new KeyManagerFactoryFactoryBean();
			 SSLConfiguration sslConfiguration  = new SSLConfiguration();
			 keyManagerFactoryFactoryBean.setAlgorithm("AES-256-CBC");
			 sslConfiguration.setKeyManagerFactory(keyManagerFactoryFactoryBean);
		ServiceArgs loginArgs = new ServiceArgs();
	       loginArgs.setUsername("admin");
	         loginArgs.setPassword("splunk123");
	         loginArgs.setHost("localhost");
	         loginArgs.setPort(8089);
	         loginArgs.setSSLSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
	         loginArgs.setApp("search");
	         loginArgs.setScheme("https");
	   	 Service service =  Service.connect(loginArgs); 
		  

		Job job = service.getJobs().create(search);
	    while(!job.isDone())
	      {
	    	  log.info("Inside job Streaming");
	    	  try {
	    		  Thread.sleep(3000);
	    		  log.info("Thread setter");
	    	  }
	    	  catch(Exception e) {log.error(e.getMessage());}
	
	}
	    Args outputargs = new Args();
	    outputargs.put("output_mode", "json");
		 InputStream stream = job.getResults(outputargs);	 
  	  byte[] buffer = new byte[8000];
  	  ObjectMapper om = new ObjectMapper();
 
  	 while(stream.read(buffer)!= -1)
	  {
  	
		  log.info("SplunkEvents in Java are" +new String(buffer));
		  log.info("Mapped object search is " + om.readValue(buffer, new TypeReference<Object>() {
			}));
		
		  
		  	
	  }

  	 
  	return om.readValue(buffer, new TypeReference<Object>() {
	});
  	/*  Map<String,String> splunkstream = om.readValue( buffer, new TypeReference<Map<String,String>>() {
		});
  
  	 Map<String,String> map = new HashMap<String,String>();
  			 map =   oMapper.convertValue(splunkstream,map.getClass());
  	 log.info("KVMapResults"+map);

  	return map; */

	//} 

	
	/*public  Socket gettcpinput() throws IOException {
		   KeyManagerFactoryFactoryBean keyManagerFactoryFactoryBean = new KeyManagerFactoryFactoryBean();
				 SSLConfiguration sslConfiguration  = new SSLConfiguration();
				 keyManagerFactoryFactoryBean.setAlgorithm("AES-256-CBC");
				 sslConfiguration.setKeyManagerFactory(keyManagerFactoryFactoryBean);
			ServiceArgs loginArgs = new ServiceArgs();
		       loginArgs.setUsername("admin");
		         loginArgs.setPassword("splunk123");
		         loginArgs.setHost("localhost");
		         loginArgs.setPort(8089);
		         loginArgs.setSSLSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
		         loginArgs.setApp("search");
		         loginArgs.setScheme("https");
		         loginArgs.setToken("1bff9765-8eab-428e-a9af-06078fd85e8c");
		   	 Service service =  Service.connect(loginArgs); 
		  String inputName = String.valueOf(8091);
          TcpInput tcpInput = (TcpInput)service.getInputs().get(inputName);
          if (tcpInput == null) {
              tcpInput = (TcpInput)service.getInputs().create(
                      inputName, InputKind.Tcp);
          }
          Socket stream = tcpInput.attach();
      
      OutputStream ostream = stream.getOutputStream();
      OutputStreamWriter writerOut = new OutputStreamWriter(ostream, "UTF-8");
      return stream; 
	} */
	

}

	 
