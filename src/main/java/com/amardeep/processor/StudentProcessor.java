package com.amardeep.processor;

import java.util.Map;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amardeep.constants.AppConstants;

public class StudentProcessor implements MessageProcessor{

	private static final Logger LOG=LoggerFactory.getLogger(StudentProcessor.class);
	
	@Override
	public MuleEvent process(MuleEvent event) throws MuleException {
		MuleMessage studentMessage=event.getMessage();
		Class<Map<String,String>> payloadType=(Class<Map<String,String>>)(Class)Map.class;
		Map<String,String> payload=studentMessage.getPayload(payloadType);
		if(isValidPayload(payload)){
			if(isValidStudent(payload)){
				payload.put(AppConstants.FLAG.toString(),"F");
			}else{
				payload.put(AppConstants.FLAG.toString(),"");
			}
			studentMessage.setPayload(payload);
			event.setMessage(studentMessage);
		}else{
			event.setStopFurtherProcessing(true);
			throw new RuntimeException(AppConstants.EXCEPTION_MESSAGE.toString());
		}
		return event;
	}
	private boolean isValidStudent(Map<String,String> payload){
		boolean isValid=false;
		try{
		isValid=payload.containsKey(AppConstants.NAME.toString())
				&&
				payload.containsKey(AppConstants.GPA.toString())
				&&
				payload.get(AppConstants.NAME.toString())!=null
				&&
				!String.valueOf(payload.get(AppConstants.NAME.toString())).trim().isEmpty()
				&&
				payload.get(AppConstants.GPA.toString())!=null
				&&
				!String.valueOf(payload.get(AppConstants.GPA.toString())).trim().isEmpty()
				&&
				Double.parseDouble(String.valueOf(payload.get(AppConstants.GPA.toString())).trim())>2.5;
		}catch(Exception e){
			LOG.error("Error validating message ",e.getCause());
		}
		return isValid;
	}
	private boolean isValidPayload(Map<String,String> map){
		boolean isValid=false;
		try{
		isValid=map.containsKey(AppConstants.ID.toString())
				&&
				map.get(AppConstants.ID.toString())!=null
				&&
				!String.valueOf(map.get(AppConstants.ID.toString())).trim().isEmpty()
				&&
				Integer.parseInt(String.valueOf(map.get(AppConstants.ID.toString())).trim())>0;
		}catch(Exception e){
			LOG.error("Error validating message ",e.getCause());
		}
		return isValid;
	}

}
