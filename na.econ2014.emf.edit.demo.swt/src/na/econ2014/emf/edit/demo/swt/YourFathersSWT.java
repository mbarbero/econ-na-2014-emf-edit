package na.econ2014.emf.edit.demo.swt;

import na.econ2014.emf.edit.demo.data.MyLibrary;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.examples.extlibrary.Book;
import org.eclipse.emf.examples.extlibrary.Library;
import org.eclipse.emf.examples.extlibrary.Writer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class YourFathersSWT extends DemoSWT {

	public static void main(String[] args) {
		YourFathersSWT swt = new YourFathersSWT();
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
		
		treeViewer.setContentProvider(new MyTreeContentProvider());
		treeViewer.setLabelProvider(new MyTreeLabelProvider());
		
		return treeViewer;
	}

	private static final class MyTreeContentProvider implements ITreeContentProvider {
		
		private static final Object[] NO_OBJ = new Object[0];
		
		private final Adapter modelListener = new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);
				viewer.refresh();
			}
		};
		
		private Viewer viewer;
		
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.viewer = viewer;
			
			if (oldInput != newInput) {
				if (oldInput instanceof Notifier) {
					((Notifier) oldInput).eAdapters().remove(modelListener);
				}
				if (newInput instanceof Notifier) {
					((Notifier) newInput).eAdapters().add(modelListener);
				}
			}
		}
	
		@Override
		public void dispose() {
			if (viewer != null) {
				Object input = viewer.getInput();
				if (input instanceof EObject) {
					((EObject) input).eAdapters().remove(this);
				}
			}
		}
	
		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	
		@Override
		public Object getParent(Object element) {
			if (element instanceof EObject)
				return ((EObject) element).eContainer();
			else 
				return null;
		}
	
		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}
	
		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ResourceSet)
				return ((ResourceSet) parentElement).getResources().toArray();
			else if (parentElement instanceof Resource)
				return ((Resource) parentElement).getContents().toArray();
			else if (parentElement instanceof EObject) 
				return ((EObject) parentElement).eContents().toArray();
			else 
				return NO_OBJ;
		}
	}

	private static final class MyTreeLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof Resource) {
				return ((Resource) element).getURI().toString();
			} else if (element instanceof EObject) {
				EObject eObject = (EObject) element;
				EClass eClass = eObject.eClass();
				String ret = eClass.getName() + " ";
				if (element instanceof Library) {
					ret += ((Library) element).getName();
				} else if (element instanceof Book) {
					ret += ((Book) element).getTitle();
				} else if (element instanceof Writer) {
					ret += ((Writer) element).getName();
				} else {
					ret += " [" + element.toString() + "]";
				}
				return ret;
			}
			return super.getText(element);
		}
		
		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}
	}
	
	
}
