
public class Solver {
	
	private static Cell[][] sudoku;
	
	public static void main(String args[]) {
		InitializeCells ic = new InitializeCells();
		sudoku = ic.createNewCells();
		updatePossibleValues();
	}
	
	public static void updatePossibleValues(){
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				sudoku.
			}
		}
	}
	
	private static void sweepHorizontalValues(int row, Cell updateCell){
		// Have to be upgraded to run out of the checked boxes
		for(int y=0;y<9;y++){
			Dimension dime = new Dimension();
			dime.x = row;
			dime.y = y;
			// removing horizontal available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, dime).getCellValue()));
		}
	}
	
	private static void sweepVerticalValues(int column, Cell updateCell){
		// Have to be upgraded to run out of the checked boxes
		for(int x=0;x<9;x++){
			Dimension dime = new Dimension();
			dime.x = x;
			dime.y = column;
			// removing vertical available values from possible values
			updateCell.getPossibleValues().remove(new Integer(DimensionUtil.getCell(sudoku, dime).getCellValue()));
		}
	}
	
	private static void sweepBoxValues(int column, Cell updateCell){
		
	}
}
