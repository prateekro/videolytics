package com.prateek.videolytics.models;

import com.google.gson.annotations.SerializedName;

public class TagsItem{

	@SerializedName("title")
	private String title;

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"TagsItem{" + 
			"title = '" + title + '\'' + 
			"}";
		}
}