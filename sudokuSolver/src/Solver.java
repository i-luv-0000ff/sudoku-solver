import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		while (checkIfAnyCellToBeSolved()) {
			updatePossibleValues();
			if (updateAllBoxUniqLstNo() == null)
				if (!updateLockedCandidates1Nd2All(true))
					if (!updateLockedCandidates1Nd2All(false))
						identifyNakedPairs();

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

	private static boolean identifyNakedPairs() {
		List<Dimension> dimes = new ArrayList<Dimension>();
		dimes.add(new Dimension(0, 0));
		dimes.add(new Dimension(3, 1));
		dimes.add(new Dimension(6, 2));
		dimes.add(new Dimension(1, 3));
		dimes.add(new Dimension(4, 4));
		dimes.add(new Dimension(7, 5));
		dimes.add(new Dimension(2, 6));
		dimes.add(new Dimension(5, 7));
		dimes.add(new Dimension(8, 8));
		for (Dimension dime : dimes) {
			List<Dimension> nakedDimes = DimensionUtil.getBoxDimensions(dime);
			if (nakedDimes.equals(nakedPairs(nakedDimes))) {
				// TODO : Try to update only the dependents of updated values.
				updatePossibleValues();
				return true;
			}
			nakedDimes = DimensionUtil.getHorizontalDimensions(dime);
			if (nakedDimes.equals(nakedPairs(nakedDimes))) {
				// TODO : Try to update only the dependents of updated values.
				updatePossibleValues();
				return true;
			}
			nakedDimes = DimensionUtil.getVerticalDimensions(dime);
			if (nakedDimes.equals(nakedPairs(nakedDimes))) {
				// TODO : Try to update only the dependents of updated values.
				updatePossibleValues();
				return true;
			}
		}
		return false;
	}

	private static List<Dimension> nakedPairs(List<Dimension> dimes) {
		List<Dimension> finalDimes = new ArrayList<Dimension>(dimes);
		for (Dimension dime : dimes) {
			Cell cell = DimensionUtil.getCell(sudoku, dime);
			if (cell.getPossibleValues().size() == 2) {
				for (Dimension innerDime : dimes) {
					Cell innerCell = DimensionUtil.getCell(sudoku, innerDime);
					if (innerCell.getPossibleValues().equals(cell.getPossibleValues())) {
						finalDimes.removeIf(match -> DimensionUtil.getCell(sudoku, match).getPossibleValues()
								.equals(innerCell.getPossibleValues()));
						removeNumInPossibleValues(innerCell.getPossibleValues(), finalDimes);
					}
				}
			}
		}
		return finalDimes;
	}

	/**
	 * Calls for updateLockedCandidates1Nd2() on all the boxes.
	 * 
	 * @param lockedCand1
	 * @return if any locked candidate updated
	 */
	private static boolean updateLockedCandidates1Nd2All(boolean lockedCand1) {
		// TODO : Get dimensions for all variables and remove items with values
		// already available. Do this for similar situations all over.

		// TODO : For every dimension updateLockedCandidates1() methods clears
		// row and column, so consider 3rows and 3column for a box and re-factor
		// the below code, rather than for every single dimension.
		// (x,y) =
		// {(0,0)(1,1)(2,2):(0,3)(1,4)(2,5):(0,6)(1,7)(2,8)::(3,0)(4,1)(5,2):(3,3)(4,4)(5,5):(3,6)(4,7)(5,8)::(6,0)(7,1)(8,2):(6,3)(7,4)(8,5):(6,6)(7,7)(8,8)}

		// TODO : Think to eliminate the above 2 criteria and keep the existing
		// code
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Dimension dime = new Dimension(x, y);
				Cell cell = DimensionUtil.getCell(sudoku, dime);
				if (cell.getCellValue() == 0) {
					if (updateLockedCandidates1Nd2(dime, lockedCand1))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Update locked candidates in the row and column of the given dimension.
	 * Returns true if updated and false if not.
	 * 
	 * @param dime
	 * @param lockedCand1
	 * @return if row or column updated
	 */
	private static boolean updateLockedCandidates1Nd2(Dimension dime, boolean lockedCand1) {
		// Locked candidate1 is a candidate within a box is restricted to one
		// row or column. Since one of these cells must contain that specific
		// candidate, the candidate can safely be excluded from the remaining
		// cells in that row or column outside of the box.
		//
		// Locked candidate2 is a candidate within a row or column is restricted
		// to one box. Since one of these cells must contain that specific
		// candidate, the candidate can safely be excluded from the remaining
		// cells in the box.
		//
		// Reference : angusj.com/sudoku/hints.php

		List<Dimension> boxDimes = DimensionUtil.getBoxDimensions(dime);
		boxDimes = DimensionUtil.removeDimesWithValues(sudoku, boxDimes);
		List<Dimension> horiDimes = DimensionUtil.getHorizontalDimensions(dime);
		horiDimes = DimensionUtil.removeDimesWithValues(sudoku, horiDimes);
		List<Dimension> vertDimes = DimensionUtil.getVerticalDimensions(dime);
		vertDimes = DimensionUtil.removeDimesWithValues(sudoku, vertDimes);
		if (eliminateLckdCandInOthrBoxes(boxDimes, horiDimes, lockedCand1)) {
			// TODO : Try to update only the dependents of updated values.
			updatePossibleValues();
			return true;
		}
		if (eliminateLckdCandInOthrBoxes(boxDimes, vertDimes, lockedCand1)) {
			// TODO : Try to update only the dependents of updated values.
			updatePossibleValues();
			return true;
		}

		return false;

	}

	/**
	 * Eliminate locked candidates from a row or column of the given dimension.
	 * Returns as soon as it updates a value.
	 * 
	 * @param boxDimes
	 * @param dimes
	 * @param lockedCand1
	 * @return if updated
	 */
	private static boolean eliminateLckdCandInOthrBoxes(List<Dimension> boxDimes, List<Dimension> dimes,
			boolean lockedCand1) {
		Set<Dimension> boxLeftOutDimes = new HashSet<Dimension>();
		Set<Dimension> leftOutDimes = new HashSet<Dimension>();
		Set<Integer> avoidDupValues = new HashSet<Integer>();
		// Collecting all the values present in the row inside the box
		for (Dimension commonRowOrColumn : DatastructureUtil.intersection(boxDimes, dimes)) {
			avoidDupValues.addAll(DimensionUtil.getCell(sudoku, commonRowOrColumn).getPossibleValues());
		}

		boxLeftOutDimes.addAll(boxDimes);
		boxLeftOutDimes.removeAll(dimes);

		leftOutDimes.addAll(dimes);
		leftOutDimes.removeAll(boxDimes);

		for (Integer eachValue : avoidDupValues) {
			boolean unique = true;
			for (Dimension boxDime : lockedCand1 ? boxLeftOutDimes : leftOutDimes) {
				if (DimensionUtil.getCell(sudoku, boxDime).getPossibleValues().contains(eachValue)) {
					unique = false;
					break;
				}
			}
			if (unique) {
				removeNumInPossibleValues(eachValue, lockedCand1 ? new ArrayList<Dimension>(leftOutDimes)
						: new ArrayList<Dimension>(boxLeftOutDimes));
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove a integer in the possible value list for a given list of
	 * dimensions.
	 * 
	 * @param eachValue
	 * @param horiLeftOutDimes
	 */
	private static void removeNumInPossibleValues(Integer eachValue, List<Dimension> horiLeftOutDimes) {
		for (Dimension dime : horiLeftOutDimes) {
			DimensionUtil.getCell(sudoku, dime).getPossibleValues().remove(new Integer(eachValue));
		}
	}

	private static void removeNumInPossibleValues(List<Integer> values, List<Dimension> horiLeftOutDimes) {
		for (Dimension dime : horiLeftOutDimes) {
			DimensionUtil.getCell(sudoku, dime).getPossibleValues().removeAll(values);
		}
	}

	/**
	 * Calls the method uniqueNoUpdate() for all the boxes available. Check if
	 * previous and current values are the same and updates unique values.
	 * Returns true if it is updated, false if the solving logic is on progress
	 * and null if this didn't updated anything.
	 * 
	 * @return true, false, null
	 */
	private static Boolean updateAllBoxUniqLstNo() {
		if (Arrays.deepEquals(sudoku, sudoPrev)) {
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					Dimension dime = new Dimension(x, y);
					Cell cell = DimensionUtil.getCell(sudoku, dime);
					if (cell.getCellValue() == 0) {
						if (uniqueNoUpdate(cell, dime)) {
							// cell updated sweep values again
							cell.getPossibleValues().clear();
							cell.updateCellValueIfPossible();
							updatePossibleValues();
							return true;
						}
					}
				}
			}
			return null;
		}
		return false;
	}

	/**
	 * Eliminates impossible values in possible value list in all cells, also
	 * updates cell value for a cell if it has only one possible value in the
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
					// Updates cell value for a cell if it has only one possible
					// value in the possible value list
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
	 * @return is updated
	 */
	private static boolean uniqueNoUpdate(Cell cellToUpdate, Dimension dime) {
		for (int possibleValue : cellToUpdate.getPossibleValues()) {
			boolean isUnique;
			// TODO : Remove dimensions without cell values
			List<Dimension> dimes = DimensionUtil.getBoxDimensions(dime);
			isUnique = checkUnique(dime, possibleValue, dimes);
			if (isUnique) {
				cellToUpdate.setCellValue(possibleValue);
				return true;
				// TODO : Update sweep values for a specific box
			}

			// TODO : Remove dimensions without cell values
			dimes = DimensionUtil.getHorizontalDimensions(dime);
			isUnique = checkUnique(dime, possibleValue, dimes);
			if (isUnique) {
				cellToUpdate.setCellValue(possibleValue);
				return true;
				// TODO : Update sweep values for a specific box
			}

			// TODO : Remove dimensions without cell values
			dimes = DimensionUtil.getVerticalDimensions(dime);
			isUnique = checkUnique(dime, possibleValue, dimes);
			if (isUnique) {
				cellToUpdate.setCellValue(possibleValue);
				return true;
				// TODO : Update sweep values for a specific box
			}
		}
		return false;
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
		for (Dimension eachDime : dimes) {
			Cell compareCell = DimensionUtil.getCell(sudoku, eachDime);
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
	private static boolean checkIfAnyCellToBeSolved() {
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