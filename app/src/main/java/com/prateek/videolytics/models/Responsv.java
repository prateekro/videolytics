package com.prateek.videolytics.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Responsv{

	@SerializedName("total")
	private int total;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsItem> results;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"Responsv{" + 
			"total = '" + total + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}