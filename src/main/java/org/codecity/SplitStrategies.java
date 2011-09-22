package org.codecity;

import org.codecity.SplitNode.SplitMode;
import org.codecity.SplitNode.SplitStrategy;

public class SplitStrategies {
	public static final SplitStrategy MAXIMIZE = new SplitStrategy() {
		@Override
		public SplitMode splitMode(SplitNode node, Dimension target) {
			int areaA = (node.dimension.width - target.width) * target.height;
			int areaB = target.width * (node.dimension.height - target.height);
			return areaA > areaB ? SplitMode.HORIZONTAL : SplitMode.VERTICAL;
		}
	};
}