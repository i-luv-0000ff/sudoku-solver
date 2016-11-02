
/**
 * @author guna
 *
 */
public class Dimension {
	public int x;
	public int y;
	
	public Dimension() {
		super();
	}

	public Dimension(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		int hash = 3;
	    hash = 53 * hash + this.x;
	    hash = 53 * hash + this.y;
	    return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
	        return false;
	    }
		if (!Dimension.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final Dimension other = (Dimension) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dimension [x=" + x + ", y=" + y + "]";
	}
}
