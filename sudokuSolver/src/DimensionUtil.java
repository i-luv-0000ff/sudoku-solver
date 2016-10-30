import java.util.ArrayList;
import java.util.List;

public class DimensionUtil {
	//work around testing
	public static void main(String args[]){
		Dimension dimeCurrent = new Dimension();
		dimeCurrent.x = 5;
		dimeCurrent.y = 5;
		getBoxDimensions(dimeCurrent);
	}
	
	public static List<Dimension> getBoxDimensions(Dimension dime) {
		List<Dimension> boxDimes = new ArrayList<Dimension>();
		Dimension startDime = findBoxStartDime(dime);
		for(int x=startDime.x; x<startDime.x+3;x++){
			for(int y=startDime.y; y<startDime.y+3;y++){
				Dimension dimeCurrent = new Dimension();
				dimeCurrent.x = x;
				dimeCurrent.y = y;
				boxDimes.add(dimeCurrent);
			}
		}
		return boxDimes;

	}

	public static Cell getCell(Cell[][] sudoku, Dimension dime) {
		return sudoku[dime.x][dime.y];
	}

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
		// divide by zero adjustment
		boxDime.x = boxDime.x - 1;
		boxDime.y = boxDime.y - 1;
		return boxDime;
	}
}
