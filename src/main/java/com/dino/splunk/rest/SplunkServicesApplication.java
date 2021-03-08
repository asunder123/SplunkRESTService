package com.dino.splunk.rest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.http.ssl.*;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class SplunkServicesApplication {

	@Bean
	public RestTemplate restTemplate() throws Exception {

		/*TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
	                    .loadTrustMaterial(null, acceptingTrustStrategy)
	                    .build();

	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

	    CloseableHttpClient httpClient = HttpClients.custom()
	                    .setSSLSocketFactory(csf)
	                    .build();

	    HttpComponentsClientHttpRequestFactory requestFactory =
	                    new HttpComponentsClientHttpRequestFactory();

	    requestFactory.setHttpClient(httpClient); */
		
		System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\asunder\\Splunkcacerts.jks");
		//System.setProperty("java.security.cert", "C:\\Program Files\\Java\\jdk1.8.0_221\\jre\\lib\\security");
	    RestTemplate restTemplate = new RestTemplate();//requestFactory); 
	    return restTemplate;
	}
	
	@Bean
	public SplunkServiceImpl services() {
		return new SplunkServiceImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(SplunkServicesApplication.class, args);
	}
}
