package com.prateek.videolytics.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("sponsored_by")
	private SponsoredBy sponsoredBy;

	@SerializedName("current_user_collections")
	private List<Object> currentUserCollections;

	@SerializedName("color")
	private String color;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("description")
	private Object description;

	@SerializedName("sponsored")
	private boolean sponsored;

	@SerializedName("sponsored_impressions_id")
	private String sponsoredImpressionsId;

	@SerializedName("liked_by_user")
	private boolean likedByUser;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("urls")
	private Urls urls;

	@SerializedName("alt_description")
	private String altDescription;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("width")
	private int width;

	@SerializedName("links")
	private Links links;

	@SerializedName("id")
	private String id;

	@SerializedName("categories")
	private List<Object> categories;

	@SerializedName("user")
	private User user;

	@SerializedName("height")
	private int height;

	@SerializedName("likes")
	private int likes;

	@SerializedName("sponsorship")
	private Sponsorship sponsorship;

	public void setSponsoredBy(SponsoredBy sponsoredBy){
		this.sponsoredBy = sponsoredBy;
	}

	public SponsoredBy getSponsoredBy(){
		return sponsoredBy;
	}

	public void setCurrentUserCollections(List<Object> currentUserCollections){
		this.currentUserCollections = currentUserCollections;
	}

	public List<Object> getCurrentUserCollections(){
		return currentUserCollections;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
	}

	public void setSponsored(boolean sponsored){
		this.sponsored = sponsored;
	}

	public boolean isSponsored(){
		return sponsored;
	}

	public void setSponsoredImpressionsId(String sponsoredImpressionsId){
		this.sponsoredImpressionsId = sponsoredImpressionsId;
	}

	public String getSponsoredImpressionsId(){
		return sponsoredImpressionsId;
	}

	public void setLikedByUser(boolean likedByUser){
		this.likedByUser = likedByUser;
	}

	public boolean isLikedByUser(){
		return likedByUser;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	public void setUrls(Urls urls){
		this.urls = urls;
	}

	public Urls getUrls(){
		return urls;
	}

	public void setAltDescription(String altDescription){
		this.altDescription = altDescription;
	}

	public String getAltDescription(){
		return altDescription;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCategories(List<Object> categories){
		this.categories = categories;
	}

	public List<Object> getCategories(){
		return categories;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	public void setLikes(int likes){
		this.likes = likes;
	}

	public int getLikes(){
		return likes;
	}

	public void setSponsorship(Sponsorship sponsorship){
		this.sponsorship = sponsorship;
	}

	public Sponsorship getSponsorship(){
		return sponsorship;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"sponsored_by = '" + sponsoredBy + '\'' + 
			",current_user_collections = '" + currentUserCollections + '\'' + 
			",color = '" + color + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",description = '" + description + '\'' + 
			",sponsored = '" + sponsored + '\'' + 
			",sponsored_impressions_id = '" + sponsoredImpressionsId + '\'' + 
			",liked_by_user = '" + likedByUser + '\'' + 
			",tags = '" + tags + '\'' + 
			",urls = '" + urls + '\'' + 
			",alt_description = '" + altDescription + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",width = '" + width + '\'' + 
			",links = '" + links + '\'' + 
			",id = '" + id + '\'' + 
			",categories = '" + categories + '\'' + 
			",user = '" + user + '\'' + 
			",height = '" + height + '\'' + 
			",likes = '" + likes + '\'' + 
			",sponsorship = '" + sponsorship + '\'' + 
			"}";
		}
}