package com.scriptures;

/**
 * Modal class for passing the online verses
 */
public class OnlineModal 
{
	public int id=0;
	public String verseIndex="";
	public String verseText="";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVerseIndex() {
		return verseIndex;
	}
	public void setVerseIndex(String verseIndex) {
		this.verseIndex = verseIndex;
	}
	public String getVerseText() {
		return verseText;
	}
	public void setVerseText(String verseText) {
		this.verseText = verseText;
	}

}
