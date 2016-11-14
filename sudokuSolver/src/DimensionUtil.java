import java.util.ArrayList;
import java.util.List;

/**
 * @author guna
 *
 */
public class DimensionUtil {
	/**
	 * Get dimensions of the row for the given cell
	 * 
	 * @param dime
	 * @return list of dimension in the horizontal row
	 */
	public static List<Dimension> getHorizontalDimensions(Dimension dime) {
		List<Dimension> boxDimes = new ArrayList<Dimension>();
		for (int y = 0; y < 9; y++) {
			Dimension dimeCurrent = new Dimension(dime.x, y);
			boxDimes.add(dimeCurrent);
		}
		return boxDimes;
	}

	/**
	 * Get dimensions of the column for the given cell
	 * 
	 * @param dime
	 * @return list of dimension in the vertical column
	 */
	public static List<Dimension> getVerticalDimensions(Dimension dime) {
		List<Dimension> boxDimes = new ArrayList<Dimension>();
		for (int x = 0; x < 9; x++) {
			Dimension dimeCurrent = new Dimension(x, dime.y);
			boxDimes.add(dimeCurrent);
		}
		return boxDimes;
	}

	/**
	 * Get dimensions of the 9boxes of the home box for a given cell
	 * 
	 * @param dime
	 * @return list of dimension in the box
	 */
	public static List<Dimension> getBoxDimensions(Dimension dime) {
		List<Dimension> boxDimes = new ArrayList<Dimension>();
		Dimension startDime = findBoxStartDime(dime);
		for (int x = startDime.x; x < startDime.x + 3; x++) {
			for (int y = startDime.y; y < startDime.y + 3; y++) {
				Dimension dimeCurrent = new Dimension(x, y);
				boxDimes.add(dimeCurrent);
			}
		}
		return boxDimes;
	}

	/**
	 * Get a cell value for a given dimension in the given sudoku matrix
	 * 
	 * @param sudoku
	 * @param dime
	 * @return cell dimensions
	 */
	public static Cell getCell(Cell[][] sudoku, Dimension dime) {
		return sudoku[dime.x][dime.y];
	}

	/**
	 * Finds the starting (top left) dimension value of a box for a given
	 * dimension value
	 * 
	 * @param Dimension
	 *            dime
	 * @return box staring value
	 */
	private static Dimension findBoxStartDime(Dimension dime) {
		// divide by zero adjustment
		dime.x = dime.x + 1;
		dime.y = dime.y + 1;
		Dimension boxDime = new Dimension();
		if (dime.x % 3 == 0)
			boxDime.x = dime.x / 3;
		else
			boxDime.x = (dime.x / 3) + 1;
		if (dime.y % 3 == 0)
			boxDime.y = dime.y / 3;
		else
			boxDime.y = (dime.y / 3) + 1;
		boxDime.x = (boxDime.x * 3) - 3;
		boxDime.y = (boxDime.y * 3) - 3;
		// revert divide by zero adjustment
		dime.x = dime.x - 1;
		dime.y = dime.y - 1;
		return boxDime;
	}
}
