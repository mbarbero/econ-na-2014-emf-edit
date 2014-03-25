package org.eclipse.emf.examples.extlibrary.provider.decorator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.examples.extlibrary.Item;
import org.eclipse.emf.examples.extlibrary.provider.util.ItemProviderAdapterDecorator;

public class ItemItemProviderDecorator 
	extends ItemProviderAdapterDecorator 
	implements
		IEditingDomainItemProvider,  
		IStructuredItemContentProvider,  
		ITreeItemContentProvider,  
		IItemLabelProvider,  
		IItemPropertySource {

	public ItemItemProviderDecorator(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public String getText(Object object) {
		return super.getText(object) + " [published on " + ((Item) object).getPublicationDate() + "]";
	}
}
