import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;

public class TableActions{

	private Set tempHashSet= new HashSet<String>();
	private Table table;
	private Scanner sc;
	private TextFileHandler tfHandler = new TextFileHandler();
	private Pattern pattern = Pattern.compile("\\( ((.{3}),(.{3})) \\)");
	private Matcher matcher;
	public TableActions(){

	}

	public TableActions(Table table) {
		this.table = table;
	}

	public void printList(List list) {
		String display="\n";
		List arrList = list;
		for(int i=0; i<arrList.size();i++) {
			display += arrList.get(i) + "\n";
		}
		System.out.println(display);
	}

	public List pairsToList(String setOfPairs) {
		sc = new Scanner(setOfPairs);
		String curLine;
		String reportOfDuplicates = "Duplicate/s found for keys :";
		int duplicatecounter = 0;
		List arrayList = new ArrayList<LinkedHashMap<String,String>>();
		Map tempHashMap = new LinkedHashMap<String, String>();
		String key, value;
		int rowCounter = 0;

		while(sc.hasNextLine()) {
			matcher = pattern.matcher(sc.nextLine());
			tempHashMap = new LinkedHashMap<String,String>();
			while(matcher.find()) {
				key = matcher.group(2);
				value = matcher.group(3);
				if(tempHashSet.add(key)) {
					tempHashMap.put(key, value);
				}
			}
			arrayList.add(tempHashMap);
		}
		return arrayList;
	}

	public Map pairsToMap(String pairs) {
		String key, value;
		sc = new Scanner(pairs);
		Map tempHashMap = new LinkedHashMap<String, String>();
		while(sc.hasNextLine()) {
			matcher = pattern.matcher(sc.nextLine());
			while(matcher.find()) {
				key = matcher.group(2);
				value = matcher.group(3);
				if(tempHashSet.add(key)) {
					tempHashMap.put(key, value);
				}
			}
		}
		return tempHashMap;
	}

	public String listToPairs(List list) {
		int colCounter = 0, rowCounter = 0;
		String newSetOfPairs = "";
		Map<String, String> tempMap;
		Iterator<Map.Entry<String, String>> tempMapIterator;
		Map.Entry<String, String> entry;
		while(rowCounter < list.size()) {
			tempMap = (Map) list.get(rowCounter);
			tempMapIterator = tempMap.entrySet().iterator();
			while(tempMapIterator.hasNext()) {
				entry = tempMapIterator.next();
				newSetOfPairs += "( " + entry.getKey() + "," + entry.getValue() + " ) ";
			}
			newSetOfPairs += "\n";
			rowCounter++;
		}
		return newSetOfPairs;
	}

	public void editIndex(Table table,int row, int col) {
		List list = table.getArrList();
		Map tempMap = (Map) list.get(row);
		String editChoice;
		sc = new Scanner(System.in);
		System.out.println("EDIT KEY? VALUE? BOTH?");
		editChoice = sc.nextLine();
		switch(editChoice.toUpperCase()) {
			case "KEY":
				editKey(list, row, col);
				break;
			case "VALUE":
				editValue(list, row, col);
				break;
			case "BOTH":	
				if(editKey(list, row, col))
					editValue(list, row, col);
				break;
			default:
				System.out.println("Invalid Input!");
			}
		
	}

	private boolean editKey(List list, int row, int col) {
		sc = new Scanner(System.in);
		int colCounter = 0;
		String newKey = "", key, value;
		while(newKey.length() != 3){
			System.out.println("Enter new Key: (ONLY ENTER UP TO 3 CHARACTERS)");
			newKey = sc.nextLine();
		}
		Map tempMap =(Map) list.get(row);
		Map replacingMap = new LinkedHashMap<String, String>();
		Map difMap;
		for(int i=0; i<list.size(); i++) {
			difMap =(Map) list.get(i);
			if(difMap.containsKey(newKey)) {
				System.out.println("Key already exists! Edit failed!");
				return false;
			} 
		}
		Iterator<Map.Entry<String, String>> tempMapIterator = tempMap.entrySet().iterator();
		while(tempMapIterator.hasNext()) {
			Map.Entry<String, String> entry = tempMapIterator.next();
			key = entry.getKey();
			value = entry.getValue();
			if(colCounter == col ) {
				key = newKey;
				value = entry.getValue();
			} 
			replacingMap.put(key, value);
			colCounter++;
		}
		list.set(row, replacingMap);
		return true;
	}

	private void editValue(List list, int row, int col) {
		 sc = new Scanner(System.in);
		 int colCounter = 0;
 	 	 String key = "", value, newValue = "";
		 while(newValue.length() != 3){
		 	System.out.println("Enter new Value: ");
		 	newValue = sc.nextLine();
		 }
		 Map tempMap = (Map) list.get(row);
		 Map replacingMap = new LinkedHashMap<String, String>();
		 Iterator<Map.Entry<String, String>> tempMapIterator = tempMap.entrySet().iterator();

		 while(tempMapIterator.hasNext()) {
		 	Map.Entry<String, String> entry = tempMapIterator.next();
		 	if( colCounter == col) {	
		 		key = entry.getKey();
		 	}
		 	colCounter++;
		}
		tempMap.put(key, newValue);
		list.set(row, tempMap);
	}

	public void searchTable(Table table, String textToSearch) {
		List list = table.getArrList();
		Map tempMap;
		String results = "", curText;
		String key, value;
		int rowCounter = 0, colCounter = 0;
		Iterator<Map.Entry<String, String>> tempMapIterator;
		while(rowCounter < list.size()) {
			tempMap = (Map) list.get(rowCounter);
			tempMapIterator = tempMap.entrySet().iterator();
			while(tempMapIterator.hasNext()) {
				Map.Entry<String, String> entry = tempMapIterator.next();
				key = entry.getKey();
				value = entry.getValue();
				results += searchOccur(textToSearch, key, "KEY", rowCounter, colCounter);
				results += searchOccur(textToSearch, value, "VALUE", rowCounter, colCounter);
				colCounter++;
				if(colCounter == tempMap.size()) {
					colCounter = 0;
				}
				tempMap = (Map) list.get(rowCounter);
			}
			rowCounter++;
		}

		System.out.println(results);
	}

	private String searchOccur(String textToSearch, String curText, String keyVal, int rowCounter, int colCounter) {
		String res = "";
		String comparison;
		int occurences = 0;

		for(int i = 0; i <= curText.length() - textToSearch.length(); i++) {
			comparison = "";
			for(int a = 0; a < textToSearch.length(); a++) {
				comparison += curText.charAt(i+a);
			}
			if(comparison.equals(textToSearch)) {
				occurences += 1;
			}
		}
		if(occurences != 0 ) {
			res += "Occurences for " + keyVal + " at (" + rowCounter + " , " + colCounter + ")  is " + occurences + "\n";
		}
		return res;
	}

	public void addRow(Table table, int col) {
		List list = table.getArrList();
		String additionalSetOfPairs = tfHandler.keyValueGenerator(1, col);
		Map tempMap = pairsToMap(additionalSetOfPairs);
		list.add(tempMap);
	}

	public void sortRow(Table table, int row) {
		List list = table.getArrList();
		sc = new Scanner(System.in);
		String sortOrder;
		String key, value, keyVal;
		Map tempMap = (Map) list.get(row);
		Iterator<Map.Entry<String, String>> tempMapIterator = tempMap.entrySet().iterator();
		List tempList = new ArrayList<String>();
		while(tempMapIterator.hasNext()) {
			Map.Entry<String, String> entry = tempMapIterator.next();
			key = entry.getKey();
			value = entry.getValue();
			keyVal = key +","+ value;
			tempList.add(keyVal);
		}
		System.out.println("ASC?DESC?");
		sortOrder = sc.nextLine();

		if(sortOrder.equalsIgnoreCase("ASC")) {
			Collections.sort(tempList);
		} else if(sortOrder.equalsIgnoreCase("DESC")) {
			Collections.reverse(tempList);
		} else {
			System.out.println("Invalid input!");
			return;
		}
		int counter = 0;
		Map newMap = new LinkedHashMap<String, String>();
		while(counter < tempList.size()) {
			String listKeyVal = (String) tempList.get(counter);
			String listKey = listKeyVal.substring(0,3);
			String listValue = listKeyVal.substring(listKeyVal.length()-3, listKeyVal.length());
			newMap.put(listKey, listValue);
			counter++;
		}
		list.set(row, newMap);
	}

}