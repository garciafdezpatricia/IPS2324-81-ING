package creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CausesCreator {
	
	private Map<String, List<String>> causes;
	
	public Map<String, List<String>> getListsOfCauses() {
		initList();
		return causes;
	}
	
	private void initList() {
		causes = new HashMap<String, List<String>>();
		putRespiratoryCauses();
		putNutritionalDeficiencies();
		putInjuries();
	}
	
	private void putInjuries() {
		List<String> list = new ArrayList<String>();
		list.add("Road traffic accidents");
		list.add("Poisonings");
		list.add("Falls");
		list.add("Fires");
		list.add("Drownings");
		list.add("Exposure to mechanical forces");
		list.add("Natural disasters");
		causes.put("Injuries", list);
	}

	private void putRespiratoryCauses() {
		List<String> list = new ArrayList<String>();
		list.add("Lower respiratory infections");
		list.add("COVID-19");
		list.add("Upper respiratory infections");
		list.add("Otitis media");
		causes.put("Respiratory infections", list);
	}
	
	private void putNutritionalDeficiencies() {
		List<String> list = new ArrayList<String>();
		list.add("Protein-energy malnutrition");
		list.add("Iodine deficiency");
		list.add("Vitamin A deficiency");
		list.add("Iron-deficiency anaemia");
		causes.put("Nutritional deficiencies", list);
	}

}