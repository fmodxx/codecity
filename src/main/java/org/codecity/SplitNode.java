package org.codecity;

import static org.codecity.Dimension.dimension;
import static org.codecity.Point.point;

public class SplitNode {
	public final Point origin;
	public final Dimension dimension;
	private SplitNode left;
	private SplitNode right;
	private SplitStrategy strategy;
	
	enum SplitMode {
		VERTICAL,
		HORIZONTAL,
		NONE
	}

	public interface SplitStrategy {
		public SplitMode splitMode(SplitNode node, Dimension target);
	}
	
	public SplitNode(Dimension dimension, SplitStrategy strategy) {
		this(point(0, 0), dimension, strategy);
	}

	SplitNode(Point origin, Dimension dimension, SplitStrategy strategy) {
		this.origin = origin;
		this.dimension = dimension;
		this.strategy = strategy;
	}

	public SplitNode split(Dimension d) {
		if (dimension.isLower(d) || left != null || right != null)
			throw new IllegalStateException();
		
		switch (splitMode(d)) {
		case VERTICAL:
			left = new SplitNode(origin, dimension(d.width, dimension.height), strategy);
			right = new SplitNode(point(d.width + origin.x, origin.y), dimension(dimension.width - d.width, dimension.height), strategy);
			return left.split(d);
		case HORIZONTAL:
			left = new SplitNode(origin, dimension(dimension.width, d.height), strategy);
			right = new SplitNode(point(origin.x, d.height + origin.y), dimension(dimension.width, dimension.height - d.height), strategy);
			return left.split(d);
		default:
			return this;
		}
	}
	
	private SplitMode splitMode(Dimension d) {
		if (dimension.equals(d))
			return SplitMode.NONE;
		if (dimension.width == d.width)
			return SplitMode.HORIZONTAL;
		if (dimension.height == d.height)
			return SplitMode.VERTICAL;
		return strategy.splitMode(this, d);
	}

	public SplitNode left() {
		return left;
	}
	
	public SplitNode right() {
		return right;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SplitNode))
			return false;
		SplitNode other = (SplitNode) obj;
		return origin.equals(other.origin) && dimension.equals(other.dimension);
	}
	
	@Override
	public int hashCode() {
		return origin.hashCode() ^ dimension.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("Region[ %s %s ]", origin, dimension);
	}

	public boolean isLeaf()
	{
		return left == null && right == null;
	}
}