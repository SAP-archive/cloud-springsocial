package com.sap.netweaver.cloud.samples.springsocial.srv.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tweet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tweet  implements Serializable
{

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * Creation date of the tweet
	 */
	@XmlElement(name = "created_at")
	String createdAt = null;

	/**
	 * User sending the tweet
	 */
	@XmlElement(name = "from_user")
	String fromUser = null;

	/**
	 * User name sending the tweet
	 */
	@XmlElement(name = "from_user_name")
	String fromUsername = null;

	/**
	 * URL to user's profile image
	 */
	@XmlElement(name = "profile_image_url")
	String profileImage = null;

	/**
	 * Text of the tweet
	 */
	@XmlElement(name = "text")
	String text = null;

	public String getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(String createdAt)
	{
		this.createdAt = createdAt;
	}

	public String getFromUser()
	{
		return fromUser;
	}

	public void setFromUser(String fromUser)
	{
		this.fromUser = fromUser;
	}

	public String getFromUsername()
	{
		return fromUsername;
	}

	public void setFromUsername(String fromUsername)
	{
		this.fromUsername = fromUsername;
	}

	public String getProfileImage()
	{
		return profileImage;
	}

	public void setProfileImage(String profileImage)
	{
		this.profileImage = profileImage;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
