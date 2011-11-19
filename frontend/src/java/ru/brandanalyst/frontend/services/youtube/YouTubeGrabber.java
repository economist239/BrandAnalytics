package ru.brandanalyst.frontend.services.youtube;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

public class YouTubeGrabber {
	private static String[] keywords = {"ad", "add", "adds", "advertisement", "commercial", "�������"};

	public static ArrayList<YouTubeEntry> searchYouTubeVideos(String brandName, int maxEntries) throws YouTubeGrabberException {

		String request = getRequest(brandName, 1, maxEntries);
		String response = HttpRequester.sendGetRequest(request);

		try {
			response = new String(response.getBytes(Charset.defaultCharset()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new YouTubeGrabberException("Not supported encoding");
		}

		if (response == null || response.length() == 0) {
			throw new YouTubeGrabberException("No response from youtube server");
		}
		return parseResponse(response);
	}

	private static String getSearchRequest(String brandName) {
		StringBuilder sb = new StringBuilder();

		sb.append("\"" + brandName + "\"+");

		for (String keyword : keywords) {
			sb.append(keyword + "|");
		}

		return sb.toString();
	}

    private static String getRequest(String brandName, int start, int max) throws YouTubeGrabberException {
		if (start < 0 || max < 0) {
			throw new IllegalArgumentException("invalid start/max indexes");
		}
		String searchRequest = getSearchRequest(brandName);

		try {
			URI uri = new URI(
					"http",
					"gdata.youtube.com",
					"/feeds/api/videos",
					"vq=" + searchRequest + "&orderby=relevance&start-index=" + start + "&max-results=" + max + "&alt=json",
					null);
			return uri.toASCIIString();
		} catch (URISyntaxException e) {
			throw new YouTubeGrabberException("Unsupported brand name to encode.", e);
		}
	}

	private static ArrayList<YouTubeEntry> parseResponse(String response) throws YouTubeGrabberException {
		ArrayList<YouTubeEntry> result = new ArrayList<>();

		JSONObject answer = (JSONObject)JSONValue.parse(response);
		if (answer == null) throw new YouTubeGrabberException("answer tag not found");

		JSONObject feed = (JSONObject) answer.get("feed");
		if (feed == null) throw new YouTubeGrabberException("feed tag not found");

		JSONArray entries = (JSONArray) feed.get("entry");
		if (entries == null) {
			return result;
		}

		for (int i = 0; i < entries.size(); i++) {
			String title;
			String href;
			String embeddedUrl;

			JSONObject entry = (JSONObject) entries.get(i);
			JSONObject mediaGroup = (JSONObject) entry.get("media$group");
			if (mediaGroup == null) throw new YouTubeGrabberException("media$group tag not found");

			//title
			JSONObject mediaTitle = (JSONObject) mediaGroup.get("media$title");
			if (mediaTitle == null) throw new YouTubeGrabberException("media$title tag not found");
			String titleType = (String) mediaTitle.get("type");
			if (!"plain".equals(titleType)) {
				System.out.println("no title type: " + titleType);
				continue;
			}
			title = (String) mediaTitle.get("$t");

			//keywords checking
			JSONObject mediaKeywords = (JSONObject) mediaGroup.get("media$keywords");
			if (mediaKeywords == null) throw new YouTubeGrabberException("media$keywords tag not found");
			String keywords = (String) mediaKeywords.get("$t");
			if (keywords != null) {
				if (!isKeywordFoundInString(keywords)) {
					continue;
				}
			}

			JSONArray mediaContent = (JSONArray) mediaGroup.get("media$content");
			if (mediaContent == null) throw new YouTubeGrabberException("media$content tag not found");
			embeddedUrl = null;
			for (int j = 0; j < mediaContent.size(); j++) {
				JSONObject link = (JSONObject) mediaContent.get(j);
				if ("application/x-shockwave-flash".equals(link.get("type"))) {
					embeddedUrl = (String) link.get("url");
					break;
				}
			}

			JSONArray links = (JSONArray) entry.get("link");
			if (links == null) throw new YouTubeGrabberException("link tag not found");
			href = null;
			for (int j = 0; j < links.size(); j++) {
				JSONObject link = (JSONObject) links.get(j);
				if ("alternate".equals(link.get("rel"))
						&& "text/html".equals(link.get("type"))) {
					href = (String) link.get("href");
					break;
				}
			}

			if (title == null || href == null || embeddedUrl == null) {
				System.out.println("Smth is null: " + title + " " + href + " " + embeddedUrl);
				continue;
			}

			result.add(new YouTubeEntry(href, embeddedUrl, title));
		}
		return result;
	}

	private static boolean isKeywordFoundInString(String str) {
		String lowcasedStr = str.toLowerCase();
		for (String keyword : keywords) {
			if (lowcasedStr.indexOf(keyword.toLowerCase()) != -1) {
				return true;
			}
		}
		return false;
	}
}
