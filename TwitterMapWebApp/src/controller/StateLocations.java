package controller;

public enum StateLocations {
	Alabama("Alabama", "AL", -84.51, -88.28, 30.13, 35.00),
	Alaska("Alaska", "AK", -130.00, -173, 54.40, 71.50),
	Arizona("Arizona", "AZ", -109.3, -114.50 , 31.20, 37),
	Arkansas("Arkansas", "AR", -89.41, -94.42 , 33.00, 36.30),
	California("California", "CA", - 114.8, -124.24 , 32.30, 42),
	Colorado("Colorado", "CO", -102.00, -109.00, 37.00, 41),
	Connecticut("Connecticut", "CT", -71.47, -73.44 , 40.58, 42.3),
	Delaware("Delaware", "DE", -75.2, -75.47 , 38.27, 39.50),
	Florida("Florida", "FL", - 79.48, -87.38 , 24.30, 31),
	Georgia("Georgia", "GA", -81.00, -85.53 , 30.31, 35),
	Hawaii("Hawaii", "HI", -154.40, -162.00,  16.55, 23),
	Idaho("Idaho", "ID", -111.00, -117.00, 42.00, 49),
	Illinois("Illinois", "IL", - 87.30, -91.30 , 36.58, 42.30),
	Indiana("Indiana", "IN", -84.49, -88.4 , 37.47, 41.46),
	Iowa("Iowa", "IA", -89.5, -96.31 , 40.36, 43.30),
	Kansas("Kansas", "KS", -94.38, -102.1, 37.00, 40),
	Kentucky("Kentucky", "KY", -81.58, -89.34, 36.30, 39.9),
	Louisiana("Louisiana", "LA", -89.00, -94.00, 29.00, 33),
	Maine("Maine", "ME", -66.57, -71.7, 43.4, 47.28),
	Maryland("Maryland", "MD", -75.4, -79.33, 37.53, 39.43),
	Massachusetts("Massachusetts", "MA", -69.57, -73.30 , 41.10, 42.53),
	Michigan("Michigan", "MI", -82.26, -90.31 , 41.41, 47.30),
	Minnesota("Minnesota", "MN", - 89.34, -97.12, 43.34, 49.23),
	Mississippi("Mississippi", "MS", -88.7, -91.41 , 30.13, 35),
	Missouri("Missouri", "MO", - 89.6, -95.42,  36.00, 40.35),
	Montana("Montana", "MT", - 104.2, -116.2, 44.26, 49),
	Nebraska("Nebraska", "NE", -95.25, -104.00, 40.00, 43),
	Nevada("Nevada", "NV", -114.00, -120.00, 35.00, 42),
	NewHampshire("New Hampshire", "NH", -70.37, -72.37, 42.40, 45.18),
	NewJersey("New Jersey", "NJ", -73.53, -75.35, 38.55, 41.21),
	NewMexico("New Mexico", "NM", - 103.00, -109.00, 31.20, 37),
	NewYork("New York", "NY", -71.47, -79.45 , 40.29, 45.0),
	NorthCarolina("North Carolina", "NC", -75.30, -84.15 , 34.00, 36.21),
	NorthDakota("North Dakota", "ND", -97.00, -104.00, 45.55, 49),
	Ohio("Ohio", "OH", -80.32, -84.49 , 38.27, 41.58),
	Oklahoma("Oklahoma", "OK", -94.29, -103.00,  33.35, 37),
	Oregon("Oregon", "OR",-116.45, -124.30, 42.00, 46.15),
	Pennsylvania("Pennsylvania", "PA", -74.43, -80.31 , 39.43, 42),
	RhodeIsland("Rhode Island", "RI", - 71.8, -71.53, 41.18, 42.1),
	SouthCarolina("South Carolina", "SC", -78.30, -83.20,  32.4, 35.12),
	SouthDakota("South Dakota", "SD", -98.28, -104.3, 42.29, 45.56),
	Tennessee("Tennessee", "TN", -81.37, -90.28, 35.00, 36.41),
	Texas("Texas", "TX", -93.31, -106.38 , 25.50, 36.30),
	Utah("Utah", "UT", -109.00, -114.00, 37.00, 42),
	Vermont("Vermont", "VT", -71.28, -73.26, 42.44, 45.0),
	Virginia("Virginia", "VA", -75.13, -83.37, 36.31, 39.37),
	Washington("Washington", "WA",-116.57, -124.48, 45.32, 49),
	WestVirginia("West Virginia", "WV", - 77.40, -82.40, 37.10, 40.40),
	Wisconsin("Wisconsin", "WI", -86.49,- 92.54, 42.30, 47.3),
	Wyoming("Wyoming", "WY", -104.3, -111.3, 41.00, 45);
	
	private String state ;
	private String stateAbbr ;
	private double latitudeMin;
	private double latitudeMax;
	private double longitudeMin;
	private double longitudeMax;
	
	/**
	 * Constructor of above states
	 * @param state
	 * @param stateAbbr
	 */
	StateLocations(String state, String stateAbbr, double latitudeMin, double latitudeMax, double longitudeMin, double longitudeMax) {
        this.state = state;
        this.stateAbbr = stateAbbr;
        this.setLatitudeMin(latitudeMin);
        this.setLatitudeMax(latitudeMax);
        this.setLongitudeMin(longitudeMin);
        this.setLongitudeMax(longitudeMax);
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


	public double getLatitudeMin() {
		return latitudeMin;
	}

	public void setLatitudeMin(double latitudeMin) {
		this.latitudeMin = latitudeMin;
	}

	public double getLatitudeMax() {
		return latitudeMax;
	}

	public void setLatitudeMax(double latitudeMax) {
		this.latitudeMax = latitudeMax;
	}

	public double getLongitudeMin() {
		return longitudeMin;
	}

	public void setLongitudeMin(double longitudeMin) {
		this.longitudeMin = longitudeMin;
	}

	public double getLongitudeMax() {
		return longitudeMax;
	}

	public void setLongitudeMax(double longitudeMax) {
		this.longitudeMax = longitudeMax;
	}
}