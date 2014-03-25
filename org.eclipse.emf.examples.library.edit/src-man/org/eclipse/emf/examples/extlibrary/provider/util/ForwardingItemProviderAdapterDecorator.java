package org.eclipse.emf.examples.extlibrary.provider.util;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

public class ForwardingItemProviderAdapterDecorator 
	extends ItemProviderAdapterDecorator 
	implements  
		IEditingDomainItemProvider,  
		IStructuredItemContentProvider,  
		ITreeItemContentProvider,  
		IItemLabelProvider,  
		IItemPropertySource {

	public ForwardingItemProviderAdapterDecorator(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

}
