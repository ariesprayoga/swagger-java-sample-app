package com.wordnik.swagger.sample.resource;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.*;
import javax.ws.rs.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.sun.jersey.api.core.ResourceConfig;
import com.wordnik.swagger.core.*;

public abstract class Help {
	@GET
	public Response getHelp(@Context ServletConfig servConfig,
			@Context ResourceConfig resConfig, @Context HttpHeaders headers,
			@Context UriInfo uriInfo) throws JsonGenerationException,
			JsonMappingException, IOException {

		String apiVersion = servConfig != null ? servConfig.getInitParameter("api.version") : null;
		String swaggerVersion = servConfig != null ? servConfig.getInitParameter("swagger.version") : null;
		String basePath = servConfig != null ? servConfig.getInitParameter("swagger.api.basepath") : null;
		String apiFilterClassName = servConfig != null ? servConfig.getInitParameter("swagger.security.filter") : null;

		boolean filterOutTopLevelApi = true;

		Api currentApiEndPoint = this.getClass().getAnnotation(Api.class);
		String currentApiPath = currentApiEndPoint != null && filterOutTopLevelApi ? currentApiEndPoint.value() : null;

		HelpApi helpApi = new HelpApi(apiFilterClassName);
		System.out.println(this.getClass());
		Documentation docs = helpApi.filterDocs(ApiReader.read(this.getClass(),
				apiVersion, swaggerVersion, basePath, currentApiPath), headers,
				uriInfo, currentApiPath);
		Response response = Response.ok(docs).build();
		return response;
	}
}
