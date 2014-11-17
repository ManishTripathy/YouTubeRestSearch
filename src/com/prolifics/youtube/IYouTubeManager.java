package com.prolifics.youtube;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.prolifics.youtube.model.YouTubeVideo;

public interface IYouTubeManager {

	public abstract List<YouTubeVideo> retrieveVideos(String textQuery,
			int maxResults, boolean filter, int timeout) throws Exception;

	@GET
	@Path("/textquery/{textquery}")
	@Produces(MediaType.APPLICATION_JSON)
	public abstract List<YouTubeVideo> retrieveVideosForRest(String textQuery,
			int maxResults) throws Exception;

}