YouTubeRestSearch
=================

This project uses YouTube API to search YouTube for any video, and then passes you the video url 
and other related info like Thumbnails in JSON format.
The project consists of YouTubeManager class which listens to REST requests when the url sent is of the following pattern- 

http://<hostname:portname>/YouTubeRestSearch/rest/youtubeServices/textquery/haider 2014?maxResults=2

The parts of the above url are - 
YouTubeRestSearch - The application name
/rest/ - When the url is prepended with "rest", then only the the request is identified as a request for a Restful resource(See the web.xml for the mapping information)
/youtubeServices - The path for the resource YouTubeManager class
textquery - It is a path parameter. This parameter takes the video name to be searched as the parameter.

[NOTE - While in browser, if you hit the above example url you will see the JSON output on the browser. But in case you are sending a 
request to this url programmanticaly, you may get an error because of the space character between haider and 2014. You may replace the 
space character in the url wit either "+" or "%20". For more details refer to http://www.w3schools.com/tags/ref_urlencode.asp

In short, if your query contains spaces, then it is wise to replace those with + or %20]

maxResults - This is an optional parameter which you need to provide. That's why it is defined as a query parameter in the code.
It is prepended with "?" indicating this parameter is optional. If you don't provide this value, then by default the maximum search results it returns is 1.





