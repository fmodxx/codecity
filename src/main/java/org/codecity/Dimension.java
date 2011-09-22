package org.codecity;

public class Dimension {
	public final int width;
	public final int height;
	
	public static Dimension dimension(int w, int h) {
		return new Dimension(w, h);
	}
	
	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean isGreater(Dimension other) {
		return width > other.width && height > other.height;
	}

	public boolean isLower(Dimension other) {
		return width < other.width || height < other.height;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Dimension))
			return false;
		Dimension other = (Dimension) obj;
		return width == other.width && height == other.height;
	}
	
	@Override
	public String toString() {
		return String.format("[%d, %d]", width, height);
	}
}