package dao.queries;

import java.util.List;

public class TwitterWordQuery extends StartStopDateQuery{
	
	private List<String> words;

	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
}
