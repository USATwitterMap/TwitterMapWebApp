package controller;

public enum StateLocations {
	Alabama("Alabama", "AL", -86.791130, 32.806671, 1),
	Alaska("Alaska", "AK", -152.404419, 61.370716, 2),
	Arizona("Arizona", "AZ", -111.431221, 33.729759, 4),
	Arkansas("Arkansas", "AR", -92.373123, 34.969704, 5),
	California("California", "CA", -119.681564, 36.116203, 6),
	Colorado("Colorado", "CO", -105.311104, 39.059811, 8),
	Connecticut("Connecticut", "CT", -72.755371, 41.597782, 9),
	Delaware("Delaware", "DE", -75.507141, 39.318523, 10),
	Florida("Florida", "FL", -81.686783, 27.766279, 12),
	Georgia("Georgia", "GA", -83.643074, 33.040619, 13),
	Hawaii("Hawaii", "HI", -157.498337, 21.094318, 15),
	Idaho("Idaho", "ID", -114.478828, 44.240459, 16),
	Illinois("Illinois", "IL", -88.986137, 40.349457, 17),
	Indiana("Indiana", "IN", -86.258278, 39.849426, 18),
	Iowa("Iowa", "IA", -93.210526, 42.011539, 19),
	Kansas("Kansas", "KS", -96.726486, 38.526600, 20),
	Kentucky("Kentucky", "KY", -84.670067, 37.668140, 21),
	Louisiana("Louisiana", "LA", -91.867805, 31.169546, 22),
	Maine("Maine", "ME", -69.381927, 44.693947, 23),
	Maryland("Maryland", "MD", -76.802101, 39.063946, 24),
	Massachusetts("Massachusetts", "MA", -71.530106, 42.230171, 25),
	Michigan("Michigan", "MI", -84.536095, 43.326618, 26),
	Minnesota("Minnesota", "MN", -93.900192, 45.694454, 27),
	Mississippi("Mississippi", "MS", -89.678696, 32.741646, 28),
	Missouri("Missouri", "MO", -92.288368, 	38.456085, 29),
	Montana("Montana", "MT", -110.454353, 46.921925, 30),
	Nebraska("Nebraska", "NE", -98.268082, 41.125370, 31),
	Nevada("Nevada", "NV", -117.055374, 38.313515, 32),
	NewHampshire("New Hampshire", "NH", -71.563896, 43.452492, 33),
	NewJersey("New Jersey", "NJ", -74.521011, 40.298904, 34),
	NewMexico("New Mexico", "NM", -106.248482, 34.840515, 35),
	NewYork("New York", "NY", -74.948051, 42.165726, 36),
	NorthCarolina("North Carolina", "NC", -79.806419, 35.630066, 37),
	NorthDakota("North Dakota", "ND", -99.784012, 47.528912, 38),
	Ohio("Ohio", "OH", -82.764915, 40.388783, 39),
	Oklahoma("Oklahoma", "OK", -96.928917, 35.565342, 40),
	Oregon("Oregon", "OR", -122.070938, 44.572021, 41),
	Pennsylvania("Pennsylvania", "PA", -77.209755, 40.590752, 42),
	RhodeIsland("Rhode Island", "RI", -71.511780, 41.680893, 44),
	SouthCarolina("South Carolina", "SC", -80.945007, 33.856892, 45),
	SouthDakota("South Dakota", "SD", -99.438828, 44.299782, 46),
	Tennessee("Tennessee", "TN", -86.692345, 35.747845, 47),
	Texas("Texas", "TX", -97.563461, 31.054487, 48),
	Utah("Utah", "UT", -111.862434, 40.150032, 49),
	Vermont("Vermont", "VT", -72.710686, 44.045876, 50),
	Virginia("Virginia", "VA", -78.169968, 37.769337, 51),
	Washington("Washington", "WA", -121.490494, 47.400902, 53),
	WestVirginia("West Virginia", "WV", -80.954453, 38.491226, 54),
	Wisconsin("Wisconsin", "WI", -89.616508, 44.268543, 55),
	Wyoming("Wyoming", "WY", -107.302490, 42.755966, 56);
	
	private String state ;
	private String stateAbbr;
	private double longitude;
	private double latitude;
	private double id;
	
	/**
	 * Constructor of above states
	 * @param state
	 * @param stateAbbr
	 */
	StateLocations(String state, String stateAbbr, double longitude, double latitude, int id) {
        this.setState(state);
        this.stateAbbr = stateAbbr;
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setId(id);
    }

	/**
	 * Check if the input looks like it has a state in it
	 * @param input
	 * @return The state identified, null otherwise
	 */
    public static StateLocations FindLatLong(String input) 
    {
    	StateLocations result = null;
    	
    	//cycle through all the states
    	for(StateLocations aState : StateLocations.values()) 
    	{
    		//check if state contains either the full state name or abbreviation 
    		//Potentially a lot of false positives here...
    		if(input.contains(aState.stateAbbr)) {
    			result = aState;
    			break;
    		}
    	}
    	return result;
    }
    
    public String getStateAbbr() {
		return stateAbbr;
	}

	public void setAbbr(String stateAbbr) {
		this.stateAbbr = stateAbbr;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}