package database;

import java.util.ArrayList;

public class QueryResult {
	private ArrayList<ArrayList<String>> data;

	public QueryResult(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}

	public ArrayList<String> getRow(int i) {
		return this.data.get(i);
	}

	public String getElement(int i, int j) {
		return this.data.get(i).get(j);
	}

	public int numRows() {
		return this.data.size();
	}

	public int numCols() {
		if (numRows() == 0)
			return 0;

		return this.data.get(0).size();
	}

	public boolean containsData() {
		return numRows() > 0 && numCols() > 0;
	}
}