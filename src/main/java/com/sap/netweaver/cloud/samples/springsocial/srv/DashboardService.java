package com.sap.netweaver.cloud.samples.springsocial.srv;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Ordering;
import com.sap.netweaver.cloud.samples.springsocial.srv.model.Dashboard;
import com.sap.netweaver.cloud.samples.springsocial.srv.model.User;

@Service("dashboardService")
@Path("/dashboard")
@Produces({ "application/json" })
public class DashboardService extends BaseService
{
	@Inject
	ObjectMapper mapper = null;
	
	@Inject 
	Twitter twitter = null;
	
	/**
	 * List every dashboard.
	 * 
	 * Reserved for future functionality!
	 * 
	 * @return
	 */
	@GET
	public List<Dashboard> getDashboards()
	{
		List <Dashboard> retVal = new ArrayList<Dashboard>(1);
		
		retVal.add(getDashboardByID("dashboard"));
		
		return retVal;
	}
	
	/**
	 * Get specific dashboard
	 * (Usually called every 30 seconds; pulling latest data; 
	 * at the moment, pushing data via WebSockets is not support by NetWeaver Cloud;
	 * in addition, if {id} is set to �latest� the newest dashboard is returned)
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public Dashboard getDashboardByID(@PathParam("id") String id)
	{
		Dashboard dashboard = new Dashboard();
		
		SearchResults results = twitter.searchOperations().search("#sapnwcloud");
		
		if (results != null && results.getTweets() != null)
		{
			
			// initialize counts
			int tweetCount = results.getTweets().size();
			int reTweetCount = 0;
			
			// initialize payload
			List<User> leaderboard = new ArrayList<User>(3);
			dashboard.setLeaderboard(leaderboard);
			
			List<com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet> latestTweets = new ArrayList<com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet>();
			dashboard.setLatestTweets(latestTweets);
			
			List<com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet> hottestTweets = new ArrayList<com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet>();
			dashboard.setHottestTweets(hottestTweets);
			
			// initialize helper objects
			Multiset<Long> hottestTweetBag = HashMultiset.create();
			Multiset<Long> leaderBoardBag = HashMultiset.create();
			
			Map<Long, Tweet> userMap = new HashMap<Long, Tweet>(results.getTweets().size());
			Map<Long, Tweet> tweetMap = new HashMap<Long, Tweet>(results.getTweets().size());
			
			/**
			 *  unfortunately the twitter search API does not populate the retweet count, so we need
			 *  to gather this data from via the timeline and store it in a map.
			 *  
			 *  However that would cost us 5 API calls per trip. If we poll every 30s we would end up
			 *  with 600 calls per hour, and we only have 150 :(
			 */
			
			Map<Long, Tweet> timelineMap = new HashMap<Long, Tweet>();
/*			
			List<Tweet> timeline = new ArrayList<Tweet>(1000);
			
			for (int page = 0; page < 5; page++)
			{
				List<Tweet> partialTimeline = twitter.timelineOperations().getHomeTimeline(page, 200);
				timeline.addAll(partialTimeline);
			}
			
			for (Tweet tweet : timeline)
			{
				timelineMap.put(tweet.getId(), tweet);
			}
*/	
			// now let's work this out ;)
			List<Tweet> tweets = results.getTweets();
			
			for (Tweet tweet : tweets)
			{
				// check if we know the tweet (if it was contained in our timeline)
				if (timelineMap.containsKey(tweet.getId()))
				{
					// if we got the tweet, let's substitute the incomplete version from the search
					tweet = timelineMap.get(tweet.getId());
					
					System.out.println(tweet.getId() + " : " + tweet.getRetweetCount() + " - " + tweet.getText());
					
					// update global reTweet Count
					if (tweet.getRetweetCount() != null) // retweet count is always null if originates from search API !!!
					{
						reTweetCount = reTweetCount + tweet.getRetweetCount().intValue();
					}
				}
				
				// add to User map (so that we can later fetch the user details)
				userMap.put(tweet.getFromUserId(), tweet);
				
				// store tweet in Tweet map
				tweetMap.put(tweet.getId(), tweet);
				
				// calculate and update individual score for leaderboard
				int score = (tweet.getRetweetCount() == null) ? 1 : 1 + tweet.getRetweetCount();
				leaderBoardBag.add(tweet.getFromUserId(), score);
				
				// build up hottest tweet map
				int tweetScore = (tweet.getRetweetCount() == null) ? 1 : 1 + tweet.getRetweetCount();
				hottestTweetBag.add(tweet.getId(), tweetScore);
										
			}

			/*
			 * sort bags and add to payload
			 */
			Ordering<Entry<Long>> byDecreasingCount = new Ordering<Entry<Long>>() 
			{
			    @Override 
			    public int compare(Entry<Long> left, Entry<Long> right) 
			    {
			      return right.getCount() - left.getCount();
			    }
			};
			
			// hottest tweet
			List<Entry<Long>> hottestTweet = byDecreasingCount.sortedCopy(hottestTweetBag.entrySet()).subList(0, 3);
			
			for (Entry<Long> entry : hottestTweet)
			{
				// get user from tweetMap
				Tweet tempTweet = tweetMap.get(entry.getElement());
								
				com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet targetTweet = new com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet();
				mapTweet(tempTweet, targetTweet);
				
				hottestTweets.add(targetTweet);
			}
			
			// leaderboard
			List<Entry<Long>> leaderboardList = byDecreasingCount.sortedCopy(leaderBoardBag.entrySet()).subList(0, 3);
			
			for (Entry<Long> entry : leaderboardList)
			{
				// get user from tweetMap
				Tweet tempTweet = userMap.get(entry.getElement());
				
				com.sap.netweaver.cloud.samples.springsocial.srv.model.User tempUser = new com.sap.netweaver.cloud.samples.springsocial.srv.model.User();
				mapTweetToUser(tempTweet, tempUser);
				tempUser.setTweetCount(Integer.toString(entry.getCount()));
				tempUser.setRetweetCount("0");
								
				leaderboard.add(tempUser);
			}
			
			// latest tweets
			List<Tweet> tempLatestList = tweets.subList(0, 3);
			
			for (Tweet tweet : tempLatestList)
			{
				com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet targetTweet = new com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet();
				mapTweet(tweet, targetTweet);
				
				latestTweets.add(targetTweet);
			}
				
			// set tweet and reTweet counts
			dashboard.setTweetCount(Integer.toString(tweetCount));
			dashboard.setRetweetCount(Integer.toString(reTweetCount));
	
		}
		
		return dashboard;
	}
	
	
	@SuppressWarnings("unused")
    private Dashboard getDashboardByIDMock(String id)
	{
		Dashboard dashboard = null;
		
		String filename = "/" + "dashboard" + ".json"; // quick and dirty ;)
		
		try
		{
			InputStream inStream = this.getClass().getResourceAsStream(filename);
			dashboard = mapper.readValue(inStream, Dashboard.class);
		}
		catch (Exception ex)
		{
			ex.printStackTrace(); // quick and dirty
		}
		
		return dashboard;
	}
	
	protected static void mapTweet(org.springframework.social.twitter.api.Tweet source,
								   com.sap.netweaver.cloud.samples.springsocial.srv.model.Tweet target)
	{
		final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
		
		target.setCreatedAt(dateFormat.format(source.getCreatedAt()));
		target.setFromUser(Long.toString(source.getFromUserId()));
		target.setFromUsername(source.getFromUser());
		target.setProfileImage(source.getProfileImageUrl());
		target.setText(source.getText());
	}
	
	protected static void mapTweetToUser(org.springframework.social.twitter.api.Tweet source,
			   							 com.sap.netweaver.cloud.samples.springsocial.srv.model.User target)
	{
		target.setProfileImageUrl(source.getProfileImageUrl());
		target.setUser(Long.toString(source.getFromUserId()));
		target.setUserName(source.getFromUser());
	}	
}
