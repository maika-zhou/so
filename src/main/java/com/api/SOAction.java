package com.api;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dto.QuestionDTO;
import com.util.HttpClientUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Api(value = "StackOverFlow API", description = "StackOverFlow API")
@RestController
@RequestMapping(value = "so")
public class SOAction
{

	@Value("${so.token}")
	public String token;

	
	
	
	@ApiOperation(value = "testSO", notes = "testSO")
	@RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
	public String addQuestion(QuestionDTO dto) throws RuntimeException, Exception 
	{ 
//		param.put("title", "CanIcreateaquestionviatheAPI-5");
//		param.put("body", "Yes you can , just do it.");
//		param.put("tags", "Test1");
//		param.put("filter", "default");
//		param.put("TEAM", "stackoverflow.com/c/ebay-test");
//		param.put("key", "44qhbGWbJHQSTwR5UGqBJw((");
//		param.put("site", "stackoverflow.com");
		Map<String, String> param = new HashMap<String, String>();
		param.put("title", dto.getTitle());		
		param.put("body", dto.getBody());		
		param.put("tags", dto.getTags());		
		param.put("filter", dto.getFilter());	
		param.put("TEAM", dto.getTEAM());		
		param.put("key", dto.getKey());			
		param.put("site", dto.getSite());		
		
		Map<String, String> header = new HashMap<String, String>();
		header.put("X-API-Access-Token", token);
		String addJson = "";
		try
		{
			addJson = HttpClientUtil.sendHttpUrl("https://api.stackexchange.com/2.2/questions/add", 
															param, 
															header,
															HttpClientUtil.HTTP_POST,
															HttpClientUtil.UTF8,
															60000);
			
			//System.out.println("---------111>"+addJson);
		}
		catch(Exception e)
		{
			
			addJson = e.getMessage();
			
		}
		return addJson;
	}
}
