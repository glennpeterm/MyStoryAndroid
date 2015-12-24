/*
 * Know My Story 
 */
package com.model;

public class VideoResponse {

	private String ID,Description,Title,thumbnail,EmbedCode,languge,mail,youtube_url,scripture;
	
	
	
	
	
	public VideoResponse() {
		super();
	}





	public VideoResponse(String iD, String description, String title,
			String thumbnail, String embedCode, String languge, String mail,
			String youtube_url, String scripture) {
		super();
		ID = iD;
		Description = description;
		Title = title;
		this.thumbnail = thumbnail;
		EmbedCode = embedCode;
		this.languge = languge;
		this.mail = mail;
		this.youtube_url = youtube_url;
		this.scripture = scripture;
	}





	public String getID() {
		return ID;
	}





	public void setID(String iD) {
		ID = iD;
	}





	public String getDescription() {
		return Description;
	}





	public void setDescription(String description) {
		Description = description;
	}





	public String getTitle() {
		return Title;
	}





	public void setTitle(String title) {
		Title = title;
	}





	public String getThumbnail() {
		return thumbnail;
	}





	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}





	public String getEmbedCode() {
		return EmbedCode;
	}





	public void setEmbedCode(String embedCode) {
		EmbedCode = embedCode;
	}





	public String getLanguge() {
		return languge;
	}





	public void setLanguge(String languge) {
		this.languge = languge;
	}





	public String getMail() {
		return mail;
	}





	public void setMail(String mail) {
		this.mail = mail;
	}





	public String getYoutube_url() {
		return youtube_url;
	}





	public void setYoutube_url(String youtube_url) {
		this.youtube_url = youtube_url;
	}





	public String getScripture() {
		return scripture;
	}





	public void setScripture(String scripture) {
		this.scripture = scripture;
	}




}
