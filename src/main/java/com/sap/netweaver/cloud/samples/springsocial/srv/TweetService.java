package com.sap.netweaver.cloud.samples.springsocial.srv;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

@Service("tweetService")
@Path("/twitter")
@Produces({ "application/json" })
public class TweetService extends BaseService
{
	@Inject
	Twitter twitter = null;
	
	@Path("/tweet")
	@POST
	public Response tweet(@FormParam("message") String message)
	{
		try
		{
			Tweet tweet = twitter.timelineOperations().updateStatus(message);
			return Response.ok(tweet).build();
		}
		catch (Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}
	
}
