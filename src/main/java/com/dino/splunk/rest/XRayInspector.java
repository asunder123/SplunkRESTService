package com.dino.splunk.rest;

import java.util.Map;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.amazonaws.services.xray.model.Segment;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.exceptions.SegmentNotFoundException;
import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
import com.amazonaws.xray.strategy.ContextMissingStrategy;

@Aspect
@Component
public class XRayInspector extends AbstractXRayInterceptor {

	  @Override    
	    protected Map<String, Map<String, Object>> generateMetadata(ProceedingJoinPoint proceedingJoinPoint, Subsegment subsegment) {      
	        return super.generateMetadata(proceedingJoinPoint, subsegment);    
	    }    
	  
	  private static ContextMissingStrategy getContextMissingStrategy() {
	        return AWSXRay.getGlobalRecorder().getContextMissingStrategy();
	    }
	  
	
	  @Override    
	  @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && bean(*Controller)")    
	  public void xrayEnabledClasses() {}
}
