package org.codecity;

import static java.util.Arrays.*;
import static org.codecity.Box.*;
import static org.codecity.Dimension.*;
import static org.codecity.Point.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.codecity.BoxLayout.LayoutStrategy;
import org.codecity.SplitNode.SplitStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class BoxLayoutTest {
	private SplitStrategy split;
	private LayoutStrategy strategy;
	private Answer<SplitNode> choose;
	
	@Before
	public void setUp() {
		split = SplitStrategies.MAXIMIZE;
		strategy = mock(LayoutStrategy.class);
		choose = new Answer<SplitNode>() {
			@Override @SuppressWarnings("unchecked")
			public SplitNode answer(InvocationOnMock invocation) throws Throwable {
				List<SplitNode> candidates = (List<SplitNode>) invocation.getArguments()[1];
				return candidates.get(0);
			}
		};
	}
	
	@Test
	public void shouldFinishLayoutWhenNull() {
		when(strategy.dimension()).thenReturn(dimension(16, 16));
		when(strategy.next()).thenReturn(box(4, 5)).thenReturn(null);
		when(strategy.choose(any(Box.class), anyListOf(SplitNode.class))).thenAnswer(choose);
		
		BoxLayout layout = new BoxLayout(split, strategy);
		layout.run();
		
		verify(strategy, times(2)).next();
	}

	@Test
	public void shouldLayoutOneElement() {
		LayoutStrategy strategy = mock(LayoutStrategy.class);
		
		Box box = box(4, 5);
		when(strategy.dimension()).thenReturn(dimension(16, 16));
		when(strategy.next()).thenReturn(box).thenReturn(null);
		when(strategy.choose(any(Box.class), anyListOf(SplitNode.class))).thenAnswer(choose);

		BoxLayout layout = new BoxLayout(split, strategy);
		layout.run();
		
		verify(strategy).choose(box, Arrays.asList(splitNode(0, 0, 16, 16)));
		assertEquals(point(0, 0), box.position);
	}
	
	@Test
	public void shouldProvideValidRegions() {
		LayoutStrategy strategy = mock(LayoutStrategy.class);
		
		Box box45 = box(4, 5);
		Box box23 = box(2, 3);
		when(strategy.dimension()).thenReturn(dimension(16, 16));
		when(strategy.next()).thenReturn(box45, box23, null);
		when(strategy.choose(any(Box.class), anyListOf(SplitNode.class))).thenAnswer(choose);

		BoxLayout layout = new BoxLayout(split, strategy);
		layout.run();
		
		verify(strategy).choose(box45, asList(splitNode(0, 0, 16, 16)));
		verify(strategy).choose(box23, asList(splitNode(4, 0, 12, 5), splitNode(0, 5, 16, 11)));
		
		assertEquals(point(0, 0), box45.position);
		assertEquals(point(4, 0), box23.position);
	}
	
	@Test(expected=LayoutException.class)
	public void shouldFailIfNotValidRegions() {
		when(strategy.dimension()).thenReturn(dimension(4, 4));
		when(strategy.next()).thenReturn(box(4, 5)).thenReturn(null);
		when(strategy.choose(any(Box.class), anyListOf(SplitNode.class))).thenAnswer(choose);
		
		BoxLayout layout = new BoxLayout(split, strategy);
		layout.run();
	}
	
	@Test
	public void perfectFit() {
		LayoutStrategy strategy = mock(LayoutStrategy.class);
		
		Box box1 = box(2, 4);
		Box box2 = box(2, 2);
		Box box3 = box(2, 2);
		when(strategy.dimension()).thenReturn(dimension(4, 4));
		when(strategy.next()).thenReturn(box1, box2, box3, null);
		when(strategy.choose(any(Box.class), anyListOf(SplitNode.class))).thenAnswer(choose);

		BoxLayout layout = new BoxLayout(split, strategy);
		layout.run();
		
		assertEquals(point(0, 0), box1.position);
		assertEquals(point(2, 0), box2.position);
		assertEquals(point(2, 2), box3.position);
	}
	
	private SplitNode splitNode(int x, int y, int w, int h) {
		return new SplitNode(point(x, y), dimension(w, h), split);
	}
}
