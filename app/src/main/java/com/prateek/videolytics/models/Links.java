package com.prateek.videolytics.models;

import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("followers")
	private String followers;

	@SerializedName("portfolio")
	private String portfolio;

	@SerializedName("following")
	private String following;

	@SerializedName("self")
	private String self;

	@SerializedName("html")
	private String html;

	@SerializedName("photos")
	private String photos;

	@SerializedName("likes")
	private String likes;

	@SerializedName("download")
	private String download;

	@SerializedName("download_location")
	private String downloadLocation;

	public void setFollowers(String followers){
		this.followers = followers;
	}

	public String getFollowers(){
		return followers;
	}

	public void setPortfolio(String portfolio){
		this.portfolio = portfolio;
	}

	public String getPortfolio(){
		return portfolio;
	}

	public void setFollowing(String following){
		this.following = following;
	}

	public String getFollowing(){
		return following;
	}

	public void setSelf(String self){
		this.self = self;
	}

	public String getSelf(){
		return self;
	}

	public void setHtml(String html){
		this.html = html;
	}

	public String getHtml(){
		return html;
	}

	public void setPhotos(String photos){
		this.photos = photos;
	}

	public String getPhotos(){
		return photos;
	}

	public void setLikes(String likes){
		this.likes = likes;
	}

	public String getLikes(){
		return likes;
	}

	public void setDownload(String download){
		this.download = download;
	}

	public String getDownload(){
		return download;
	}

	public void setDownloadLocation(String downloadLocation){
		this.downloadLocation = downloadLocation;
	}

	public String getDownloadLocation(){
		return downloadLocation;
	}

	@Override
 	public String toString(){
		return 
			"Links{" + 
			"followers = '" + followers + '\'' + 
			",portfolio = '" + portfolio + '\'' + 
			",following = '" + following + '\'' + 
			",self = '" + self + '\'' + 
			",html = '" + html + '\'' + 
			",photos = '" + photos + '\'' + 
			",likes = '" + likes + '\'' + 
			",download = '" + download + '\'' + 
			",download_location = '" + downloadLocation + '\'' + 
			"}";
		}
}