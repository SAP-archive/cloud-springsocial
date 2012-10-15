package com.sap.netweaver.cloud.samples.springsocial.srv;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class CustomJsonMapper extends ObjectMapper
{
	/**
	 * {@inheritDoc}
	 */
    public CustomJsonMapper()
	{
		super();
		this.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		this.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		this.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT); 
		this.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

		final AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		
		// make deserializer use JAXB annotations (only)
		this.setAnnotationIntrospector(introspector);
	}

}
