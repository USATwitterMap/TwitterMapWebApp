package dao;

import java.util.ArrayList;
import java.util.List;

import dao.results.StatePopulation;
import service.StateLocations;

public class StatePopulationDaoMock implements StatePopulationDao{

	@Override
	public List<StatePopulation> GetPopulation() {
		// TODO Auto-generated method stub
		List<StatePopulation> fakeStateData = new ArrayList<StatePopulation>();
		for(StateLocations state : StateLocations.values()){
			fakeStateData.add(new StatePopulation());
			fakeStateData.get(fakeStateData.size() - 1).setState(state.getStateAbbr());
			fakeStateData.get(fakeStateData.size() - 1).setPopulation(5000000);
		}
		return fakeStateData;
	}

}
