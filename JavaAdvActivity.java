import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.InputMismatchException;
import java.lang.NumberFormatException;

public class JavaAdvActivity implements Actions {

	private Scanner sc;
	private Table table;
	private TableActions tableActions;
	private TextFileHandler tfHandler;
	private List list;

	public static void main(String[]args) {
		String choice, setOfPairs;
		JavaAdvActivity jvActivity = new JavaAdvActivity();
		jvActivity.tableActions = new TableActions();
		jvActivity.sc = new Scanner(System.in);

		//Checks if args is used
		if(args != null) {
			jvActivity.tfHandler = new TextFileHandler(args);
		} else {
			jvActivity.tfHandler = new TextFileHandler();
		}

		//get the texts from file or if not found generate it's own
		setOfPairs = jvActivity.tfHandler.generateSetOfPairs();
		//insert the pairs generated into a list of linkedhashmap
	 	jvActivity.list = jvActivity.tableActions.pairsToList(setOfPairs);

		jvActivity.table = new Table(jvActivity.list);

		while(true) {
			jvActivity.showActions();
			choice = jvActivity.sc.nextLine();
			jvActivity.doActions(choice);
			jvActivity.tfHandler.createFile(jvActivity.tableActions.listToPairs(jvActivity.table.getArrList())); 
		}

	}

	public void showActions() {
			System.out.println("Enter PRINT for Print");
			System.out.println("Enter EDIT for Edit");
			System.out.println("Enter SEARCH for Search");
			System.out.println("Enter RESET for Reset");
			System.out.println("Enter ADD for Add Row");
			System.out.println("Enter SORT for Sort");
			System.out.println("Enter EXIT for Exit");
	}

	public void doActions(String action){
		switch(action.toUpperCase()) {
			case "PRINT":
				printAction();
				break;
			case "EDIT":
				editAction();
				break;
			case "SEARCH":
				searchAction();
				break;
			case "RESET":
				resetAction();
				break;
			case "ADD":
				addAction();
				break;
			case "SORT":
				sortAction();
				break;
			case "EXIT":
				exitAction();
				break;
			default:
				System.out.println("Invalid Input!");
				break;
		}
	}

	public void printAction() {
		List list = table.getArrList();
		tableActions.printList(list);
	}

	public void editAction() {
		int row, col;
		try{
			System.out.println("Enter row number");
			row = Integer.parseInt(sc.nextLine());
			if(row >= list.size()) {
				System.out.println("Invalid Index number!");
				return;
			}
			System.out.println("Enter col number");
			col = Integer.parseInt(sc.nextLine());
			Map tempMap = (Map) list.get(row);
			if(col >= tempMap.size()) {
				System.out.println("Invalid Index number!");
				return;
			}
			tableActions.editIndex(table, row ,col);
		} catch(NumberFormatException e) {
			System.out.println("Only enter numbers!");
			return;
		}
	}
	
	public void searchAction() {
		String textToSearch;
		System.out.println("Enter the text you want to search");
		textToSearch = sc.nextLine();
		if(!textToSearch.equals(""))
			tableActions.searchTable(table, textToSearch);
		else
			System.out.println("Invalid Input!");
	}

	public void resetAction() {
		int row = 0, col = 0;
		String newSetOfPairs;
		try{
			while(row <= 0) {
				System.out.println("Enter number of rows: ");
				row = Integer.parseInt(sc.nextLine());
			}
			while(col <= 0) {
				System.out.println("Enter number of columns: ");
				col = Integer.parseInt(sc.nextLine());
			}
		} catch(NumberFormatException e) {
			System.out.println("Only enter numbers!");
			sc.next();
			return;
		}
		newSetOfPairs = tfHandler.keyValueGenerator(row, col);
		tfHandler.createFile(newSetOfPairs);
		List newArrList = tableActions.pairsToList(newSetOfPairs);
		table.setArrList(newArrList);
	}

	public void addAction() {
		int col;
		try{
		String setOfPairs = tableActions.listToPairs(list);
		System.out.println("Enter number of column:");
		col = Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			System.out.println("Only enter numbers!");
			return;
		}
		tableActions.addRow(table, col);
	}

	public void sortAction() {
		int row;
		try{
		System.out.println("Enter row you want to sort");
		row = Integer.parseInt(sc.nextLine());
		if(row >= list.size()) {
			System.out.println("Index does not exist!");
			return;
		} else {
			tableActions.sortRow(table, row);
		}
		} catch(NumberFormatException e) {
			System.out.println("Only enter numbers!");
		}
	}

	public void exitAction() {
		System.out.println("Program Closing!");
		System.exit(0);
	}
}