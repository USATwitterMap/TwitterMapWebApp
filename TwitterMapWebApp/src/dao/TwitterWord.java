package dao;
public class TwitterWord
{
   private int id;
   private String state;
   private String word;
   private int occurances;
   private int time;
   
   	public int getId() 
	{
	    return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getState() 
	{
	    return state;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	
	public String getWord() 
	{
	    return word;
	}
	public void setWord(String word) 
	{
		this.word = word;
	}
	
	public int getOccurances() 
	{
	    return occurances;
	}
	public void setOccurances(int occurances) 
	{
		this.occurances = occurances;
	}
   
	public int getTime() 
	{
	    return time;
	}
	public void setTime(int time) 
	{
		this.time = time;
	}
}