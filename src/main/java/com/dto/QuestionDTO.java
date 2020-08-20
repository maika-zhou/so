package com.dto;

public class QuestionDTO
{
	
	
	private String title;
	private String body;
	private String tags;
	private String filter;
	private String TEAM;
	private String key;
	private String site;
	
	
	
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getBody()
	{
		return body;
	}
	public void setBody(String body)
	{
		this.body = body;
	}
	public String getTags()
	{
		return tags;
	}
	public void setTags(String tags)
	{
		this.tags = tags;
	}
	public String getFilter()
	{
		return filter;
	}
	public void setFilter(String filter)
	{
		this.filter = filter;
	}
	public String getTEAM()
	{
		return TEAM;
	}
	public void setTEAM(String tEAM)
	{
		TEAM = tEAM;
	}
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public String getSite()
	{
		return site;
	}
	public void setSite(String site)
	{
		this.site = site;
	}
	
}
