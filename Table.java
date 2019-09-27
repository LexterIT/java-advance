import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;

public class Table {
	
	private List arrList;
	private Set setOfKeys;

	public Table() {
		arrList = new ArrayList<LinkedHashMap<String, String>>();
		setOfKeys = new HashSet<String>();
	}

	public Table(List arrList) {
		this.arrList = arrList;
	}

	public void setArrList(List arrList) {
		this.arrList = arrList;
	}

	public void setKeys(Set setOfKeys) {
		this.setOfKeys = setOfKeys;
	}
	
	public List getArrList() {
		return arrList;
	}

	public Set getSetOfKeys() {
		return setOfKeys;
	}

	
}