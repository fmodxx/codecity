package org.codecity;

import static org.codecity.Dimension.*;
import static org.codecity.Point.*;

public class Box {
	public final Dimension dimension;
	public Point position;
	
	public static Box box(int width, int height) {
		return new Box(dimension(width, height));
	}

	public Box(Dimension dimension) {
		this.dimension = dimension;
		this.position = point(0, 0);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Box))
			return false;
		Box other = (Box) obj;
		return dimension.equals(other.dimension) && position.equals(other.position);
	}
	
	@Override
	public int hashCode() {
		return dimension.hashCode() ^ position.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("Box[%d, %d]", dimension.width, dimension.height);
	}
}