import java.util.Arrays;
import java.util.List;

/**
 * @author guna
 *
 */
// TODO: Change this class to have non static methods. Re-factor
// createNewCells()
public class Solver {

	private static Cell[][] sudoku;
	static Cell[][] sudoPrev;

	public static void main(String args[]) {
		InitializeCells ic = new InitializeCells();
		sudoku = ic.createNewCells();

		// run forever till solution
		while (checkIfAnyValueToUpdate()) {
			updatePossibleValues();
			updateAllBoxUniqLstNo();

			// cloning object for real
			sudoPrev = new Cell[9][9];
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					sudoPrev[x][y] = sudoku[x][y].clone();
				}
			}
			// cloning object for real

			Cell.displaySudoku(sudoku);
		}
	}

	/**
	 * Calls the method uniqueNoUpdate() for all the boxes available. Check if
	 * previous and current values are the same and updates unique values
	 */
	private static void updateAllBoxUniqLstNo() {
		if (Arrays.deepEquals(sudoku, sudoPrev)) {
			boolean sweepValuesAgain = false;
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					Dimension dime = new Dimension(x, y);
					Cell cell = DimensionUtil.getCell(sudoku, dime);
					if (cell.getCellValue() == 0) {
						uniqueNoUpdate(cell, dime);
						if (cell.getCellValue() != 0) {
							// cell updated sweep values again
							sweepValuesAgain = true;
							cell.getPossibleValues().clear();
							break;
						}
					}
				}
				if (sweepValuesAgain)
					break;
			}
		}
	}

	/**
	 * Updates cell value for a cell if it has only one possible value in the
	 * possible value list. Does the same for all boxes.
	 */
	public static void updatePossibleValues() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Dimension dime = new Dimension(x, y);
				Cell cellToUpdate = DimensionUtil.getCell(sudoku, dime);
				if (cellToUpdate.getCellValue() == 0) {
					// TODO Have to be upgraded to run out of the checked boxes.
					// Make a method to have cell to 'check dimensions'
					// horizontally, vertically, and inside box.
					List<Dimension> dimes = DimensionUtil.getBoxDimensions(dime);
					sweepValuesWithDimes(dime, cellToUpdate, dimes);
					// TODO Have to be upgraded to run out of the checked boxes.
					// Make a method to have cell to 'check dimensions'
					// horizontally, vertically, and inside box.
					dimes = DimensionUtil.getHorizontalDimensions(dime);
					sweepValuesWithDimes(dime, cellToUpdate, dimes);
					// TODO Have to be upgraded to run out of the checked boxes.
					// Make a method to have cell to 'check dimensions'
					// horizontally, vertically, and inside box.
					dimes = DimensionUtil.getVerticalDimensions(dime);
					sweepValuesWithDimes(dime, cellToUpdate, dimes);
					cellToUpdate.updateCellValueIfPossible();
				}
			}
		}

	}

	/**
	 * Single square candidate. Checks for squares' list with unique candidates
	 * and updates the cell value. Looks for a unique number in possible number
	 * lists of column, row and 9 cell box boxes.
	 * 
	 * @param cellToUpdate
	 * @param dime
	 */
	private static void uniqueNoUpdate(Cell cellToUpdate, Dimension dime) {
		for (int possibleValue : cellToUpdate.getPossibleValues()) {
			boolean isUnique;
			List<Dimension> dimes = DimensionUtil.getBoxDimensions(dime);
			isUnique = checkUnique(dime, possibleValue, dimes);
			if (isUnique) {
				cellToUpdate.setCellValue(possibleValue);
				break;
				// TODO : Update sweep values for a specific box
			}

			dimes = DimensionUtil.getHorizontalDimensions(dime);
			isUnique = checkUnique(dime, possibleValue, dimes);
			if (isUnique) {
				cellToUpdate.setCellValue(possibleValue);
				break;
				// TODO : Update sweep values for a specific box
			}

			dimes = DimensionUtil.getVerticalDimensions(dime);
			isUnique = checkUnique(dime, possibleValue, dimes);
			if (isUnique) {
				cellToUpdate.setCellValue(possibleValue);
				break;
				// TODO : Update sweep values for a specific box
			}
		}
	}

	/**
	 * Checks and returns a boolean for the dimensions if any dimension has the
	 * same possible value in its list.
	 * 
	 * @param dime
	 * @param possibleValue
	 * @param dimes
	 * @return if the possible value is unique
	 */
	private static boolean checkUnique(Dimension dime, int possibleValue, List<Dimension> dimes) {
		boolean isUnique = true;
		// excluding already present dimension
		dimes.remove(dime);
		for (Dimension boxDime : dimes) {
			Cell compareCell = DimensionUtil.getCell(sudoku, boxDime);
			if (compareCell.getCellValue() == 0 && compareCell.getPossibleValues().contains(possibleValue)) {
				isUnique = false;
				break;
			}
		}
		return isUnique;
	}

	/**
	 * Eliminates impossible values in possible value list in a cell, for given
	 * dimensions
	 * 
	 * @param dime
	 * @param updateCell
	 * @param dimes
	 */
	private static void sweepValuesWithDimes(Dimension dime, Cell updateCell, List<Dimension> dimes) {
		// excluding already present dimension
		dimes.remove(dime);
		for (Dimension eachDime : dimes) {
			// removing horizontal available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, eachDime).getCellValue()));
		}
	}

	/**
	 * Checks if any value is zero
	 * 
	 * @return if any value is empty
	 */
	private static boolean checkIfAnyValueToUpdate() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Dimension dime = new Dimension(x, y);
				if (DimensionUtil.getCell(sudoku, dime).getCellValue() == 0)
					return true;
			}
		}
		return false;
	}

}