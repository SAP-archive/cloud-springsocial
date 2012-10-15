package com.sap.netweaver.cloud.samples.springsocial.srv.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "content")
@XmlAccessorType(XmlAccessType.FIELD)
public class Content  implements Serializable
{

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * Unique id
	 */
	@XmlElement(name = "id")
	String id = null;

	/**
	 * Title of the piece
	 */
	@XmlElement(name = "title")
	String title = null;

	/**
	 * Subtitle of the piece
	 */
	@XmlElement(name = "sub_title")
	String subTitle = null;

	/**
	 * Icon illustrating the piece
	 */
	@XmlElement(name = "icon")
	String icon = null;

	/**
	 * Content containing HTML
	 */
	@XmlElement(name = "content")
	String content = null;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSubTitle()
	{
		return subTitle;
	}

	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

    
}
