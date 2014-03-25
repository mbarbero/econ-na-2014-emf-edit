package na.econ2014.emf.edit.demo.swt;

import na.econ2014.emf.edit.demo.data.MyLibrary;

import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.examples.extlibrary.provider.EXTLibraryItemProviderAdapterFactory;
import org.eclipse.emf.examples.extlibrary.provider.decorator.DecoratorEXTLibraryItemProviderAdapterFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NotYourFathersSWT extends DemoSWT {

	public static void main(String[] args) {
		NotYourFathersSWT swt = new NotYourFathersSWT();
		swt.start(args);
	}

	private void start(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout(new FillLayout());
		shell.setText ("Shell");
		shell.setSize (640, 480);
		
		createControl(shell);
		
		setInput(MyLibrary.create());
		
		shell.open();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

	protected TreeViewer createViewer(Composite composite) {
		TreeViewer treeViewer = new TreeViewer(composite);
		treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
		adapterFactory.addAdapterFactory(new DecoratorEXTLibraryItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EXTLibraryItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		//adapterFactory.addAdapterFactory(new TreeItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		
		treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		
		return treeViewer;
	}
}
