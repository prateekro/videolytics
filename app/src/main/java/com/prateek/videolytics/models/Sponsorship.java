package com.prateek.videolytics.models;

import com.google.gson.annotations.SerializedName;

public class Sponsorship{

	@SerializedName("sponsor")
	private Sponsor sponsor;

	@SerializedName("tagline")
	private String tagline;

	@SerializedName("impressions_id")
	private String impressionsId;

	public void setSponsor(Sponsor sponsor){
		this.sponsor = sponsor;
	}

	public Sponsor getSponsor(){
		return sponsor;
	}

	public void setTagline(String tagline){
		this.tagline = tagline;
	}

	public String getTagline(){
		return tagline;
	}

	public void setImpressionsId(String impressionsId){
		this.impressionsId = impressionsId;
	}

	public String getImpressionsId(){
		return impressionsId;
	}

	@Override
 	public String toString(){
		return 
			"Sponsorship{" + 
			"sponsor = '" + sponsor + '\'' + 
			",tagline = '" + tagline + '\'' + 
			",impressions_id = '" + impressionsId + '\'' + 
			"}";
		}
}