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
		list.add("Back pain");
		list.add("Headache");
		list.add("Abdominal pain");
		list.add("Chest pain");
		list.add("Sore throat");
		list.add("Nauseas");
		list.add("Vomiting");
		list.add("Diarrhea");
		list.add("Constipation");
		list.add("Joint pain");
		list.add("Numbness");
		list.add("Tremors");
		list.add("Weakness");
		list.add("Fatigue");
		causes.put("Injuries", list);
	}

	private void putRespiratoryCauses() {
		List<String> list = new ArrayList<String>();
		list.add("Fever");
		list.add("Chronic cough");
		list.add("Cough up blood");
		list.add("Shortness of breath");
		list.add("Nasal congestion");
		list.add("Nasal bleeding");
		causes.put("Respiratory infections", list);
	}
	
	private void putNutritionalDeficiencies() {
		List<String> list = new ArrayList<String>();
		list.add("Dizziness");
		list.add("Vertigo");
		list.add("Blurry vision");
		list.add("Vision loss");
		list.add("Weight loss");
		list.add("Weight gain");
		causes.put("Nutritional deficiencies", list);
	}

}