package dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public class TwitterWordQuery {
	
	private List<String> words;
	private String startDate;
	private String stopDate;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
}
