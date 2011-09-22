package org.codecity;

import static org.codecity.Dimension.dimension;
import static org.codecity.Point.point;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.codecity.SplitNode.SplitStrategy;
import org.junit.Before;
import org.junit.Test;

public class SplitNodeTest {
	private SplitNode root;
	private SplitStrategy strategy;

	@Before
	public void setUp() {
		strategy = SplitStrategies.MAXIMIZE; 
		root = new SplitNode(dimension(9, 9), strategy);
	}
	
	@Test
	public void splitingToFullSizeReturnsSame() {
		SplitNode split = root.split(dimension(9, 9));
		assertSame(root, split);
	}
	
	@Test(expected=IllegalStateException.class)
	public void splitBiggerSizeThrowsException() {
		root.split(dimension(10, 9));
	}
	
	@Test
	public void splittingPerfectWidthSubdivideRegion() {
		SplitNode split = root.split(dimension(9, 4));
		assertSame(root.left(), split);
		assertEquals(splitNode(point(0, 0), dimension(9, 4)), root.left());
		assertEquals(splitNode(point(0, 4), dimension(9, 5)), root.right());
	}
	
	@Test
	public void splittingPerfectHeightSubdivideRegion() {
		SplitNode split = root.split(dimension(4, 9));
		assertSame(root.left(), split);
		assertEquals(splitNode(point(0, 0), dimension(4, 9)), root.left());
		assertEquals(splitNode(point(4, 0), dimension(5, 9)), root.right());
	}
	
	@Test
	public void splittingHorizontalSubdividesThreeRegion() {
		SplitNode split = root.split(dimension(3, 4));
		assertSame(split, root.left().left());
		assertEquals(splitNode(point(0, 0), dimension(9, 4)), root.left());
		assertEquals(splitNode(point(0, 0), dimension(3, 4)), root.left().left());
		assertEquals(splitNode(point(3, 0), dimension(6, 4)), root.left().right());
		assertEquals(splitNode(point(0, 4), dimension(9, 5)), root.right());
	}
	
	@Test
	public void splittingVerticalSubdividesThreeRegion() {
		SplitNode split = root.split(dimension(5, 3));
		assertSame(split, root.left().left());
		assertEquals(splitNode(point(0, 0), dimension(5, 9)), root.left());
		assertEquals(splitNode(point(0, 0), dimension(5, 3)), root.left().left());
		assertEquals(splitNode(point(0, 3), dimension(5, 6)), root.left().right());
		assertEquals(splitNode(point(5, 0), dimension(4, 9)), root.right());
	}
	
	@Test(expected=IllegalStateException.class)
	public void splitWorksFirstTime() {
		root.split(dimension(5, 3));
		root.split(dimension(2, 3));
	}
	
	private SplitNode splitNode(Point point, Dimension dimension) {
		return new SplitNode(point, dimension, strategy);
	}
}
