import java.util.ArrayList;
import java.util.List;

public class Cell {
	private int cellValue;
	private List<Integer> possibleValues = new ArrayList<Integer>(){{
		add(1);
		add(2);
		add(3);
		add(4);
		add(5);
		add(6);
		add(7);
		add(8);
		add(9);
	}};

	public int getCellValue() {
		return cellValue;
	}

	public void setCellValue(int cellValue) {
		this.cellValue = cellValue;
	}

	public List<Integer> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(ArrayList<Integer> possibleValues) {
		this.possibleValues = possibleValues;
	}

	/**
	 * updates the cell value if the list has only one possible value
	 * @return ifUpdated
	 */
	public boolean updateCellValueIfPossible() {
		if (possibleValues != null && possibleValues.size() == 1) {
			cellValue = possibleValues.get(0);
			return true;
		} else
			return false;
	}
	
	
	/**
	 * Workaround for a toString() of sudoku block
	 * @param sudoku
	 */
	public static void displaySudoku(Cell[][] sudoku){
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Dimension dime = new Dimension(x,y);
				System.out.print(DimensionUtil.getCell(sudoku, dime).getCellValue());
			}
			System.out.println(" ");
		}
	}
}
