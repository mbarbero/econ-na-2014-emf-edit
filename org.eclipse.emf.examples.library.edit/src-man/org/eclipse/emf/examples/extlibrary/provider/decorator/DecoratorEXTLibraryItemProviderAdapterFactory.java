package org.eclipse.emf.examples.extlibrary.provider.decorator;

import org.eclipse.emf.edit.provider.DecoratorAdapterFactory;
import org.eclipse.emf.edit.provider.IItemProviderDecorator;
import org.eclipse.emf.examples.extlibrary.Item;
import org.eclipse.emf.examples.extlibrary.provider.EXTLibraryItemProviderAdapterFactory;
import org.eclipse.emf.examples.extlibrary.provider.util.ForwardingItemProviderAdapterDecorator;

public class DecoratorEXTLibraryItemProviderAdapterFactory extends
		DecoratorAdapterFactory {

	public DecoratorEXTLibraryItemProviderAdapterFactory() {
		super(new EXTLibraryItemProviderAdapterFactory());
	}

	@Override
	protected IItemProviderDecorator createItemProviderDecorator(Object target, Object Type) {
		if (target instanceof Item) {
			return new ItemItemProviderDecorator(this);
		}
		return new ForwardingItemProviderAdapterDecorator(this);
	}

}
