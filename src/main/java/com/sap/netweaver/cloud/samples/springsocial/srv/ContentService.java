package com.sap.netweaver.cloud.samples.springsocial.srv;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.sap.netweaver.cloud.samples.springsocial.srv.model.Content;

@Service("contentService")
@Path("/content")
@Produces({ "application/json" })
public class ContentService extends BaseService
{
	@Inject
	ObjectMapper mapper = null;
	
	
	/**
	 * List every piece of content
	 * (Usually only call once; when application is started by the user)
	 * 
	 * @return
	 */
	@GET
	public List<Content> getContent()
	{
		List <Content> retVal = new ArrayList<Content>(8);
		
		retVal.add(getContentByID("home"));
		retVal.add(getContentByID("chapter1"));
		retVal.add(getContentByID("chapter2"));
//		retVal.add(getContentByID("chapter3"));
//		retVal.add(getContentByID("chapter4"));
//		retVal.add(getContentByID("chapter5"));
//		retVal.add(getContentByID("chapter6"));
		retVal.add(getContentByID("about"));
		
		return retVal;
	}
	
	/**
	 * Get specific piece of content
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public Content getContentByID(@PathParam("id") String id)
	{
		Content content = null;
		
		
		String filename = "/" + id + ".json"; // quick and dirty ;)
		
		try
		{
			InputStream inStream = this.getClass().getResourceAsStream(filename);
			content = mapper.readValue(inStream, Content.class);
		}
		catch (Exception ex)
		{
			ex.printStackTrace(); // quick and dirty
		}
			
		return content;
	}
}
