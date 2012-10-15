package com.sap.netweaver.cloud.samples.springsocial.srv.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "leaderboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class User  implements Serializable
{

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * User
	 */
	@XmlElement(name = "user")
	String user = null;

	/**
	 * Username
	 */
	@XmlElement(name = "user_name")
	String userName = null;

	/**
	 * URL to user's profile image
	 */
	@XmlElement(name = "profile_image_url")
	String profileImageUrl = null;

	/**
	 * Number of tweets sent by the user
	 */
	@XmlElement(name = "tweet_count")
	String tweetCount =" 0";

	/**
	 * Content containing HTML
	 */
	@XmlElement(name = "re_tweet_count")
	String retweetCount = "0";

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getProfileImageUrl()
	{
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl)
	{
		this.profileImageUrl = profileImageUrl;
	}

	public String getTweetCount()
	{
		return tweetCount;
	}

	public void setTweetCount(String tweetCount)
	{
		this.tweetCount = tweetCount;
	}

	public String getRetweetCount()
	{
		return retweetCount;
	}

	public void setRetweetCount(String retweetCount)
	{
		this.retweetCount = retweetCount;
	}
    
}
