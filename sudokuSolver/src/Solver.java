
/**
 * @author guna
 *
 */
public class Solver {
	
	private static Cell[][] sudoku;
	
	public static void main(String args[]) {
		InitializeCells ic = new InitializeCells();
		sudoku = ic.createNewCells();
		updatePossibleValues();
	}
	
	/**
	 * Updates the possible values for a cell
	 */
	public static void updatePossibleValues(){
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				sudoku.
			}
		}
	}
	
	/**
	 * Eliminates possible values for a given cell already present in the given horizontal row
	 * @param row
	 * @param updateCell
	 */
	private static void sweepHorizontalValues(Dimension dime, Cell updateCell){
		// TODO  Have to be upgraded to run out of the checked boxes
		for(Dimension horiDime : DimensionUtil.getHorizontalDimensions(dime)){
			// removing horizontal available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, horiDime).getCellValue()));
		}
	}
	
	/**
	 * Eliminates possible values for a given cell already present in the given Vertical column
	 * @param column
	 * @param updateCell
	 */
	private static void sweepVerticalValues(Dimension dime, Cell updateCell){
		// TODO  Have to be upgraded to run out of the checked boxes
		for(Dimension vertDime : DimensionUtil.getVerticalDimensions(dime)){
			// removing vertical available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, vertDime).getCellValue()));
		}
	}
	
	/**
	 * Eliminates possible values for a given cell already present in the given current box
	 * @param column
	 * @param updateCell
	 */
	private static void sweepBoxValues(Dimension dime, Cell updateCell){
		for(Dimension boxDime : DimensionUtil.getBoxDimensions(dime)){
			// removing vertical available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, boxDime).getCellValue()));
		}
	}
}
