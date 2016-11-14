import java.util.ArrayList;
import java.util.List;

public class Cell implements Cloneable{
	private int cellValue;
	private List<Integer> possibleValues = new ArrayList<Integer>(){
		private static final long serialVersionUID = -1337935474300432552L;

	{
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
			possibleValues.clear();
			return true;
		} else
			return false;
	}
	
	
	protected Cell clone() {
		Cell cellSend = new Cell();
		cellSend.cellValue = new Integer(this.cellValue);
		cellSend.possibleValues = new ArrayList<Integer>(this.possibleValues);
		return cellSend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cellValue;
		result = prime * result + ((possibleValues == null) ? 0 : possibleValues.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (cellValue != other.cellValue)
			return false;
		if (possibleValues == null) {
			if (other.possibleValues != null)
				return false;
		} else if (!possibleValues.equals(other.possibleValues))
			return false;
		return true;
	}

	/**
	 * Workaround for a toString() of sudoku block
	 * @param sudoku
	 */
	public static void displaySudoku(Cell[][] sudoku){
		System.out.println("");
		System.out.println("ITERATION");
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if(y==3||y==6)
					System.out.print(" | ");
				Dimension dime = new Dimension(x,y);
				System.out.print(DimensionUtil.getCell(sudoku, dime).getCellValue());
			}
			System.out.println(" ");
			if(x==2||x==5){
				System.out.print("---------------");
				System.out.println(" ");
			}
		}
	}
}
