/*
 * Know My Story 
 */
package com.model;

public class YouTubeResponse {
	
	String Id,description,published_at,thumbanil,title;

	public YouTubeResponse(String id, String description, String published_at,
			String thumbanil, String title) {
		super();
		Id = id;
		this.description = description;
		this.published_at = published_at;
		this.thumbanil = thumbanil;
		this.title = title;
	}

	public YouTubeResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublished_at() {
		return published_at;
	}

	public void setPublished_at(String published_at) {
		this.published_at = published_at;
	}

	public String getThumbanil() {
		return thumbanil;
	}

	public void setThumbanil(String thumbanil) {
		this.thumbanil = thumbanil;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
