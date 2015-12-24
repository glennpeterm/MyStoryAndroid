/*
 * Know My Story 
 */
package com.model;



public class Menu {
	
	String MainText,SubText;

	public Menu(String mainText, String subText) {
		super();
		MainText = mainText;
		SubText = subText;
	}

	public String getMainText() {
		return MainText;
	}

	public void setMainText(String mainText) {
		MainText = mainText;
	}

	public String getSubText() {
		return SubText;
	}

	public void setSubText(String subText) {
		SubText = subText;
	}
	
	

}
