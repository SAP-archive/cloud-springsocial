package com.sap.netweaver.cloud.samples.springsocial.srv.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dashboard implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    /**
	 * Number of tweets
	 */
	@XmlElement(name = "tweet_count")
	String tweetCount = "0";

	/**
	 * Number of re-tweets
	 */
	@XmlElement(name = "re_tweet_count")
	String retweetCount = "0";
	
	/**
	 * Contains the three latest tweets (1..3); Order: First in the list is the latest one.
	 */
	@XmlElement(name = "latest_tweets")
	List<Tweet> latestTweets = null;
	
	/**
	 * Contains the three 'hottest' tweets (1..3); 'Hottest' is defined as numbers of re-tweets; 
	 * Order: First in the list is the 'hottest' one.
	 */
	@XmlElement(name = "hottest_tweets")
	List<Tweet> hottestTweets = null;
	
	/**
	 * Contains the five �top� users (1..5); �Top� is defined as most total number of re-tweets and tweets; 
	 * Order: First in the list is the �top� one.
	 */
	@XmlElement(name = "leaderboard")
	List<User> leaderboard = null;

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

	public List<Tweet> getLatestTweets()
	{
		return latestTweets;
	}

	public void setLatestTweets(List<Tweet> latestTweets)
	{
		this.latestTweets = latestTweets;
	}

	public List<Tweet> getHottestTweets()
	{
		return hottestTweets;
	}

	public void setHottestTweets(List<Tweet> hottestTweets)
	{
		this.hottestTweets = hottestTweets;
	}

	public List<User> getLeaderboard()
	{
		return leaderboard;
	}

	public void setLeaderboard(List<User> leaderboard)
	{
		this.leaderboard = leaderboard;
	}
}