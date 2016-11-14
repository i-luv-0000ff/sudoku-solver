import java.util.Arrays;
import java.util.List;

/**
 * @author guna
 *
 */
// TODO: Change this class to have non static methods. Re-factor createNewCells()
public class Solver {
	
	private static Cell[][] sudoku;
	static Cell[][] sudoPrev;
	
	public static void main(String args[]) {
		InitializeCells ic = new InitializeCells();
		sudoku = ic.createNewCells();
		
		// run forever till solution
		while(checkIfAnyValueToUpdate()){
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
	 * Calls the method uniqueNoUpdate() for all the boxes available.
	 * Check if previous and current values are the same and updates unique values
	 */
	private static void updateAllBoxUniqLstNo() {
		if(Arrays.deepEquals(sudoku, sudoPrev)){
			boolean sweepValuesAgain = false;
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					Dimension dime = new Dimension(x,y);
					Cell cell = DimensionUtil.getCell(sudoku, dime);
					if(cell.getCellValue() == 0){
						uniqueNoUpdate(cell, dime);
						if(cell.getCellValue()!=0){
							// cell updated sweep values again
							sweepValuesAgain = true;
							cell.getPossibleValues().clear();
							break;
						}
					}
				}
				if(sweepValuesAgain)
					break;
			}
		}
	}
	
	/**
	 * Updates the possible values for a cell.
	 * Does the same foe all boxes.
	 */
	public static void updatePossibleValues(){
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Dimension dime = new Dimension(x,y);
				Cell cellToUpdate = DimensionUtil.getCell(sudoku, dime);
				if(cellToUpdate.getCellValue()==0){
					sweepBoxValues(dime, cellToUpdate);
					sweepHorizontalValues(dime, cellToUpdate);
					sweepVerticalValues(dime, cellToUpdate);
					cellToUpdate.updateCellValueIfPossible();
				}
			}
		}
		
	}

	/**
	 * Single square candidate. Checks for squares' list with unique candidates.
	 * @param cellToUpdate
	 * @param dime
	 */
	private static void uniqueNoUpdate(Cell cellToUpdate, Dimension dime) {
		for(int possibleValue : cellToUpdate.getPossibleValues()){
			List<Dimension> boxDimes = DimensionUtil.getBoxDimensions(dime);
			// excluding already present dimension
			boxDimes.remove(dime);
			boolean isUnique = true;
			for(Dimension boxDime : boxDimes){
				Cell compareCell = DimensionUtil.getCell(sudoku, boxDime);
				if(compareCell.getCellValue() == 0 && compareCell.getPossibleValues().contains(possibleValue)){
					isUnique = false;
					break;					
				}
			}
			if(isUnique){
				cellToUpdate.setCellValue(possibleValue);
				break;
				//TODO : Update sweep values for a specific box 
			}
			
			List<Dimension> horiDimes = DimensionUtil.getHorizontalDimensions(dime);
			// excluding already present dimension
			horiDimes.remove(dime);
			isUnique = true;
			for(Dimension horiDime : horiDimes){
				Cell compareCell = DimensionUtil.getCell(sudoku, horiDime);
				if(compareCell.getCellValue() == 0 && compareCell.getPossibleValues().contains(possibleValue)){
					isUnique = false;
					break;					
				}
			}
			if(isUnique){
				cellToUpdate.setCellValue(possibleValue);
				break;
			}
			
			List<Dimension> vertDimes = DimensionUtil.getVerticalDimensions(dime);
			// excluding already present dimension
			vertDimes.remove(dime);
			isUnique = true;
			for(Dimension vertDime : vertDimes){
				Cell compareCell = DimensionUtil.getCell(sudoku, vertDime);
				if(compareCell.getCellValue() == 0 && compareCell.getPossibleValues().contains(possibleValue)){
					isUnique = false;
					break;					
				}
			}
			if(isUnique){
				cellToUpdate.setCellValue(possibleValue);
				break;
			}
		}
	}
	
	
	
	/**
	 * Checks if any value is zero
	 * @return if any value is empty
	 */
	private static boolean checkIfAnyValueToUpdate(){
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Dimension dime = new Dimension(x,y);
				if(DimensionUtil.getCell(sudoku, dime).getCellValue()==0)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Eliminates impossible values in possible value list for a given cell in the horizontal row
	 * @param row
	 * @param updateCell
	 */
	private static void sweepHorizontalValues(Dimension dime, Cell updateCell){
		// TODO  Have to be upgraded to run out of the checked boxes
		List<Dimension> horiDimes = DimensionUtil.getHorizontalDimensions(dime);
		// excluding already present dimension
		horiDimes.remove(dime);
		for(Dimension horiDime : horiDimes){
			// removing horizontal available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, horiDime).getCellValue()));
		}
	}
	
	/**
	 * Eliminates impossible values in possible value list for a given cell in the Vertical column
	 * @param column
	 * @param updateCell
	 */
	private static void sweepVerticalValues(Dimension dime, Cell updateCell){
		// TODO  Have to be upgraded to run out of the checked boxes
		List<Dimension> vertDimes = DimensionUtil.getVerticalDimensions(dime);
		// excluding already present dimension
		vertDimes.remove(dime);
		for(Dimension vertDime : vertDimes){
			// removing vertical available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, vertDime).getCellValue()));
		}
	}
	
	/**
	 * Eliminates impossible values in possible value list for a given cell in the box
	 * @param column
	 * @param updateCell
	 */
	private static void sweepBoxValues(Dimension dime, Cell updateCell){
		List<Dimension> boxDimes = DimensionUtil.getBoxDimensions(dime);
		// excluding already present dimension
		boxDimes.remove(dime);
		for(Dimension boxDime : boxDimes){
			// removing vertical available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, boxDime).getCellValue()));
		}
	}
}
