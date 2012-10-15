package com.sap.netweaver.cloud.samples.springsocial.test;


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Ordering;
import com.sap.netweaver.cloud.samples.springsocial.srv.DashboardService;
import com.sap.netweaver.cloud.samples.springsocial.srv.model.Dashboard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-test.xml" })
public class SpringBasedUnitTest
{
	@Inject
	Twitter twitter = null;
	
	@Inject 
	ObjectMapper mapper = null;
	
	@Inject 
	DashboardService dashboardService = null;
	
	/**
	 * Setup the profiles required for the {@link DetectorFactory}
	 */
	@BeforeClass
	public static void setupProfiles()
	{
		Properties props = System.getProperties();
		props.put("http.proxyHost", "proxy");
		props.put("http.proxyPort", "8080");
		props.put("https.proxyHost", "proxy");
		props.put("https.proxyPort", "8080");
		props.put("http.nonProxyHosts", "*.corp|localhost");
		
		System.setProperty("java.net.useSystemProxies", "false");	
	}
	
	@Test
	public void testTwitter() throws Exception
	{
		List<Tweet> tweets = Collections.emptyList();
		
		try
		{
			for (int i=0; i < 1; i++)
			{
				tweets = twitter.timelineOperations().getHomeTimeline(0, 200);
			}
			
			Assert.notEmpty(tweets);
		}
		catch (org.springframework.social.ServerOverloadedException ex)
		{
			// Twitter is overloaded with requests. Try again later.
			Assert.isTrue(ex.getMessage().equals("Twitter is overloaded with requests. Try again later."));
		}
		catch (org.springframework.social.UncategorizedApiException ex)
		{
			// Error consuming Twitter REST API
			Assert.isTrue(ex.getMessage().equals("Error consuming Twitter REST API"));
			System.out.println("WARN : Please double-check your oAuth credentials in src/main/resources/oauth.properties!");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			// The rate limit has been exceeded.
		}
	}
	
	@Test
	public void testDashboard() throws Exception
	{
		List<Dashboard> dashboards = dashboardService.getDashboards();
		
		Assert.notEmpty(dashboards);
	}
	
	@Test
	public void testMultiSet() throws Exception
	{
		Multiset<Long> testMultiSet = HashMultiset.create();
		
		testMultiSet.add(1L, 1);
		testMultiSet.add(1L, 1);
		testMultiSet.add(2L, 1);
		testMultiSet.add(2L, 2);
		testMultiSet.add(3L, 1);
		testMultiSet.add(3L, 2);
		testMultiSet.add(3L, 1);
		testMultiSet.add(4L, 4);
		testMultiSet.add(4L, 1);

		Ordering<Entry<Long>> byDecreasingCount = new Ordering<Entry<Long>>() 
		{
		    @Override 
		    public int compare(Entry<Long> left, Entry<Long> right) 
		    {
		      // safe because count is never negative
		      return right.getCount() - left.getCount();
		    }
		};
		
		List<Entry<Long>> list = byDecreasingCount.sortedCopy(testMultiSet.entrySet()).subList(0, 3);
		
		for (Entry<Long> entry : list)
		{
			System.out.println(entry.getElement() + " : " + entry.getCount());
		}
	
		Assert.notEmpty(list);
		
		Assert.isTrue(list.get(0).getElement() == 4L);
		Assert.isTrue(list.get(1).getElement() == 3L);
		Assert.isTrue(list.get(2).getElement() == 2L);
	
	}
}
