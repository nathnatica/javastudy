package study.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum HierarchicalEnum {
	OS(null),
		Windows(OS),
			WindowsNT(Windows),
				WindowsNTWorkstation(WindowsNT),
				WindowsNTServer(WindowsNT),
			Windows2000(Windows),
			WindowsXP(Windows),
		Unix(OS) {
			@Override
			public boolean supportsXWindowSystem() {
				return true;
			}
		},
			Linux(Unix),
			AIX(Unix),
			HpUx(Unix),
			SunOs(Unix);

	private final HierarchicalEnum parent;
	private final List<HierarchicalEnum> children = new ArrayList<HierarchicalEnum>();
	private final List<HierarchicalEnum> allChildren = new ArrayList<HierarchicalEnum>();

	HierarchicalEnum(HierarchicalEnum parent) {
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
	}

	public HierarchicalEnum parent( ) {
		return parent;
	}

	public boolean is(HierarchicalEnum other) {
		if (other == null) {
			return false;
		}

		for (HierarchicalEnum osType = this; osType != null; osType = osType.parent()) {
			if (other == osType) {
				return true;
			}
		}
		return false;
	}

	public List<? extends HierarchicalEnum> allChildren() {
		return Collections.unmodifiableList(allChildren);
	}

	private void addChild (HierarchicalEnum child) {
		this.children.add(child);

		List<HierarchicalEnum> greatChildren = new ArrayList<HierarchicalEnum>();
		greatChildren.add(child);
		greatChildren.addAll(child.allChildren());

		HierarchicalEnum currentAncestor = this;
		while (currentAncestor != null) {
			currentAncestor.allChildren.addAll(greatChildren);
			currentAncestor = currentAncestor.parent;
		}
	}

	public boolean supportsXWindowSystem() {
		if (parent == null) {
			return false;
		}
		return parent.supportsXWindowSystem();
	}


}