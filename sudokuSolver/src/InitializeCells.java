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
		int count =0;
		char[] charArr = reader.nextLine().toCharArray();
		reader.close();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.println("(" + (i + 1) + "," + (j + 1) + "):"+charArr[count]);
				sudoku[i][j] = new Cell();
				sudoku[i][j].setCellValue(Integer.parseInt(charArr[count]+""));
				if(Integer.parseInt(charArr[count]+"")!=0){
					sudoku[i][j].getPossibleValues().clear();
				}
				count++;
			}
		}
		return sudoku;
	}
}
