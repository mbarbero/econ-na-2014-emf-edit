package org.eclipse.emf.examples.extlibrary.provider.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ItemProviderDecorator;

public class ItemProviderAdapterDecorator extends ItemProviderDecorator implements Adapter.Internal {

	private List<Notifier> targets;

	public ItemProviderAdapterDecorator(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	public Notifier getTarget() {
		if (targets == null || targets.isEmpty()) {
			return null;
		} else {
			return targets.get(targets.size() - 1);
		}
	}

	public void setTarget(Notifier newTarget) {
		if (targets == null) {
			targets = new ArrayList<Notifier>();
		}
		targets.add(newTarget);
	}

	public void unsetTarget(Notifier oldTarget) {
		if (targets != null) {
	      targets.remove(oldTarget);
	    }
	}
}
