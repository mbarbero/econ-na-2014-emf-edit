package na.econ2014.emf.edit.demo.swt;

import java.util.Iterator;

import na.econ2014.emf.edit.demo.data.MyLibrary;

import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.emf.examples.extlibrary.Book;
import org.eclipse.emf.examples.extlibrary.Writer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class DemoSWT {

	private MyLibrary myLibrary;
	
	private TreeViewer treeViewer;
	
	private Button addBookButton;
	private Button removeBookButton;
	private Button capitalizeButton;
	
	protected Composite createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		
		treeViewer = createViewer(composite);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChanged(event);
			}
		});
		
		Composite actionArea = new Composite(composite, SWT.NONE);
		actionArea.setLayout(new GridLayout(1, true));
		actionArea.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
		
		addBookButton = new Button(actionArea, SWT.NONE);
		addBookButton.setText("Add Book");
		addBookButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		addBookButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleAddBookButtonSelected();
			}
		});
		
		removeBookButton = new Button(actionArea, SWT.NONE);
		removeBookButton.setText("Remove");
		removeBookButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		removeBookButton.setEnabled(false);
		removeBookButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (treeViewer.getSelection() instanceof IStructuredSelection) {
					handleRemoveButtonSelected((IStructuredSelection) treeViewer.getSelection());
				}
			}
		});
		
		capitalizeButton = new Button(actionArea, SWT.NONE);
		capitalizeButton.setText("(Un)capitalize");
		capitalizeButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		capitalizeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (treeViewer.getSelection() instanceof IStructuredSelection) {
					handleCapitalizeButtonSelected((IStructuredSelection) treeViewer.getSelection());
				}
			}
		});
		
		return composite;
	}
	
	protected abstract TreeViewer createViewer(Composite composite);
	
	protected final void setInput(MyLibrary library) {
		myLibrary = library;
		//treeViewer.setInput(library.getResourceSet());
		treeViewer.setInput(library.getTreeNode());
	}
	
	private void handleSelectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (!(next instanceof Book)) {
				removeBookButton.setEnabled(false);
				return;
			}
		}
		removeBookButton.setEnabled(true);
	}

	private void handleRemoveButtonSelected(IStructuredSelection selection) {
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof Book) {
				myLibrary.getLibrary().getBooks().remove(next);
			}
		}
	}
	
	private void handleCapitalizeButtonSelected(IStructuredSelection selection) {
		Object element = selection.getFirstElement();
		Book book = null;
		if (element instanceof Book) {
			book = (Book) element;
		} else if (element instanceof TreeNode && ((TreeNode) element).getData() instanceof Book) {
			book = (Book) ((TreeNode) element).getData();
		}
		capitalize(book);
		
		Writer writer = null;
		if (element instanceof Writer) {
			writer = (Writer) element;
		} else if (element instanceof TreeNode && ((TreeNode) element).getData() instanceof Writer) {
			writer = (Writer) ((TreeNode) element).getData();
		}
		capitalize(writer);
	}

	private void capitalize(Writer writer) {
		if (writer != null) {
			if (writer.getName().equals(writer.getName().toUpperCase())) {
				writer.setName(writer.getName().toLowerCase());
			} else {
				writer.setName(writer.getName().toUpperCase());
			}
		}
	}

	private void capitalize(Book book) {
		if (book != null) {
			if (book.getTitle().equals(book.getTitle().toUpperCase())) {
				book.setTitle(book.getTitle().toLowerCase());
			} else {
				book.setTitle(book.getTitle().toUpperCase());
			}
		}
	}

	private void handleAddBookButtonSelected() {
		myLibrary.createBook("NewBook-"
				+ myLibrary.getLibrary().getBooks().size());
	}
}
