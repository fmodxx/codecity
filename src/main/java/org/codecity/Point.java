package org.codecity;

public class Point {
	public final int x;
	public final int y;
	
	public static Point point(int x, int y) {
		return new Point(x, y);
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point))
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}
	
	@Override
	public int hashCode() {
		return x << 16 ^ y;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
}