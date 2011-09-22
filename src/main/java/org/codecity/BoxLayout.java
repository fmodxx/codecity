package org.codecity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codecity.SplitNode.SplitStrategy;

public class BoxLayout {
	private LayoutStrategy strategy;
	private SplitNode ptree;
	private Set<SplitNode> used = new HashSet<SplitNode>();
	private Box box;

	public interface LayoutStrategy {
		public Dimension dimension();
		public Box next();
		public SplitNode choose(Box element, List<SplitNode> candidates);
	}
	
	public BoxLayout(SplitStrategy splitStrategy, LayoutStrategy layoutStrategy) {
		this.ptree = new SplitNode(layoutStrategy.dimension(), splitStrategy);
		this.strategy = layoutStrategy;
	}

	public void run() {
		while ((box = strategy.next()) != null) {
			SplitNode node = strategy.choose(box, candidates());
			SplitNode target = node.split(box.dimension);
			used.add(target);
			box.position = target.origin;
		}
	}
	
	private List<SplitNode> candidates() {
		List<SplitNode> list = new ArrayList<SplitNode>();
		findCandidates(ptree, list);
		if (list.isEmpty())
			throw new LayoutException("No candidates for " + box);
		return list;
	}

	private void findCandidates(SplitNode node, List<SplitNode> list) {
		if (!node.isLeaf()) {
			findCandidates(node.left(), list);
			findCandidates(node.right(), list);
		}
		else if (!used.contains(node) && node.dimension.isGreaterEquals(box.dimension)) {
			list.add(node);
		}
	}
}
