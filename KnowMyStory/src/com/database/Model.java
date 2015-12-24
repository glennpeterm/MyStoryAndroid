/*
 * Know My Story 
 */
package com.database;

public class Model {
	int id;
	String title;
	String video1;
	String video2;
	String video3;
	String video4;
	String video5;
	String video6;
	String video7;
	String bg_music;
	String output_video;
	String merged_status;
	String upload_status;
	String approved_status;
	String current_screen;
	String description;

	public Model() {

	}

	public Model(int id, String title, String video1, String video2,
			String video3, String video4, String video5, String video6,
			String video7, String bg_music, String output_video,
			String merged_status, String upload_status, String approved_status,
			String current_screen, String description) {

		this.id = id;
		this.title = title;
		this.video1 = video1;
		this.video2 = video2;
		this.video3 = video3;
		this.video4 = video4;
		this.video5 = video5;
		this.video6 = video6;
		this.video7 = video7;
		this.bg_music = bg_music;
		this.output_video = output_video;
		this.merged_status = merged_status;
		this.upload_status = upload_status;
		this.approved_status = approved_status;
		this.current_screen = current_screen;
		this.description = description;

	}

	public Model(String title) {
		this.title = title;
		this.description = description;
	}

	
	// setters
	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setvideo1(String video1) {
		this.video1 = video1;
	}

	public void setvideo2(String video2) {
		this.video2 = video2;
	}

	public void setvideo3(String video3) {
		this.video3 = video3;
	}

	public void setvideo4(String video4) {
		this.video4 = video4;
	}

	public void setvideo5(String video5) {
		this.video5 = video5;
	}

	public void setvideo6(String video6) {
		this.video6 = video6;
	}

	public void setvideo7(String video7) {
		this.video7 = video7;
	}

	public void setbg_music(String bg_music) {
		this.bg_music = bg_music;
	}

	public void setoutput_video(String output_video) {
		this.output_video = output_video;
	}

	public void setmerged_status(String merged_status) {
		this.merged_status = merged_status;
	}

	public void setupload_status(String upload_status) {
		this.upload_status = upload_status;
	}

	public void setapproved_status(String approved_status) {
		this.approved_status = approved_status;
	}

	public void setcurrent_screen(String current_screen) {
		this.current_screen = current_screen;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// getters
	public long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getvideo1() {
		return this.video1;
	}

	public String getvideo2() {
		return this.video2;
	}

	public String getvideo3() {
		return this.video3;
	}

	public String getvideo4() {
		return this.video4;
	}

	public String getvideo5() {
		return this.video5;
	}

	public String getvideo6() {
		return this.video6;
	}

	public String getvideo7() {
		return this.video7;
	}

	public String getbg_music() {
		return this.bg_music;
	}

	public String getoutput_video() {
		return this.output_video;
	}

	public String getmerged_status() {
		return this.merged_status;
	}

	public String getupload_status() {
		return this.upload_status;
	}

	public String getapproved_status() {
		return this.approved_status;
	}

	public String getcurrent_screen() {
		return this.current_screen;
	}

	public String getDescription() {
		return this.description;
	}
}
