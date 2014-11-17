package com.prolifics.youtube;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.prolifics.youtube.model.YouTubeMedia;
import com.prolifics.youtube.model.YouTubeVideo;

@Path("/youtubeServices")
public class YouTubeManager implements IYouTubeManager {
 
    private static final String YOUTUBE_URL = "http://gdata.youtube.com/feeds/api/videos";
    private static final String YOUTUBE_EMBEDDED_URL = "http://www.youtube.com/v/";
 
    private String clientID;
    
    public YouTubeManager() {
        this.clientID = "Manish Thiru";
    }
    
    public YouTubeManager(String clientID) {
        this.clientID = clientID;
    }
 
     /* (non-Javadoc)
	 * @see com.prolifics.youtube.IYouTubeManager#retrieveVideos(java.lang.String, int, boolean, int)
	 */
    @Override
	public List<YouTubeVideo> retrieveVideos(String textQuery, int maxResults, boolean filter, int timeout) throws Exception {
  
        YouTubeService service = new YouTubeService(clientID);
        service.setConnectTimeout(timeout); // millis
        YouTubeQuery query = new YouTubeQuery(new URL(YOUTUBE_URL));
  
        query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
        query.setFullTextQuery(textQuery);
        query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
        query.setMaxResults(maxResults);

        VideoFeed videoFeed = service.query(query, VideoFeed.class);  
        List<VideoEntry> videos = videoFeed.getEntries();
  
        return convertVideos(videos);
  
    }
 
    private List<YouTubeVideo> convertVideos(List<VideoEntry> videos) {
  
        List<YouTubeVideo> youtubeVideosList = new LinkedList<YouTubeVideo>();
  
        for (VideoEntry videoEntry : videos) {
   
            YouTubeVideo ytv = new YouTubeVideo();
   
            YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();
            String webPlayerUrl = mediaGroup.getPlayer().getUrl();
            ytv.setWebPlayerUrl(webPlayerUrl);
   
            String query = "?v=";
            int index = webPlayerUrl.indexOf(query);

            String embeddedWebPlayerUrl = webPlayerUrl.substring(index+query.length());
            embeddedWebPlayerUrl = YOUTUBE_EMBEDDED_URL + embeddedWebPlayerUrl;
            ytv.setEmbeddedWebPlayerUrl(embeddedWebPlayerUrl);
   
            List<String> thumbnails = new LinkedList<String>();
            for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
                thumbnails.add(mediaThumbnail.getUrl());
            }   
            ytv.setThumbnails(thumbnails);
   
            List<YouTubeMedia> medias = new LinkedList<YouTubeMedia>();
            for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
                medias.add(new YouTubeMedia(mediaContent.getUrl(), mediaContent.getType()));
            }
            ytv.setMedias(medias);
   
            youtubeVideosList.add(ytv);
   
        }
  
        return youtubeVideosList;
  
    }
    
    /* (non-Javadoc)
	 * @see com.prolifics.youtube.IYouTubeManager#retrieveVideosForRest(java.lang.String, int)
	 */
    @Override
	@GET
	@Path("/textquery/{textquery}")
	@Produces(MediaType.APPLICATION_JSON)
    public List<YouTubeVideo> retrieveVideosForRest(@PathParam("textquery")String textQuery,
    		@DefaultValue("1") @QueryParam("maxresults") int maxResults) throws Exception {
    	  
        return this.retrieveVideos(textQuery, maxResults, true, 2000);
  
    }
    
}