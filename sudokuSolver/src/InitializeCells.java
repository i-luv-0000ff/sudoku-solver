import java.util.Scanner;

/**
 * @author guna
 *
 */
public class InitializeCells {
	/**
	 * Initialize cells with starting values
	 * @return sudoku cells
	 */
	public Cell[][] createNewCells() {
		Cell[][] sudoku = new Cell[9][9];
		Scanner reader = new Scanner(System.in);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.println("(" + (i + 1) + "," + (j + 1) + "):");
				sudoku[i][j] = new Cell();
				sudoku[i][j].setCellValue(reader.nextInt());
			}
		}
		reader.close();
		Cell.displaySudoku(sudoku);
		return sudoku;
	}
}
