package na.econ2014.emf.edit.demo.data;

import java.util.Calendar;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.tree.TreeFactory;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.emf.examples.extlibrary.Book;
import org.eclipse.emf.examples.extlibrary.BookCategory;
import org.eclipse.emf.examples.extlibrary.EXTLibraryFactory;
import org.eclipse.emf.examples.extlibrary.EXTLibraryPackage;
import org.eclipse.emf.examples.extlibrary.Item;
import org.eclipse.emf.examples.extlibrary.Library;
import org.eclipse.emf.examples.extlibrary.VideoCassette;
import org.eclipse.emf.examples.extlibrary.Writer;

public class MyLibrary {

	private Library library;
	private Resource resource;
	private ResourceSet resourceSet;
	private static EXTLibraryFactory factory;
	
	private MyLibrary(){}
	
	public static MyLibrary create() {
		MyLibrary ret = new MyLibrary();
		ret.init();
		return ret;
	}
	
	private void init() {
		factory = EXTLibraryFactory.eINSTANCE;
		library = factory.createLibrary();
		library.setName("Alexandria");
		library.setAddress("Alexandria, Egypt");
		
		Writer tolkien = factory.createWriter();
		tolkien.setFirstName("J.R.R.");
		tolkien.setLastName("Tolkien");
		library.getWriters().add(tolkien);
		
		Book book = factory.createBook();
		book.setTitle("The Lord of the Ring — The Fellowship of the Ring");
		book.setCategory(BookCategory.SCIENCE_FICTION_LITERAL);
		book.setPages(564);
		book.setAuthor(tolkien);
		Calendar pubdate = Calendar.getInstance();
		pubdate.set(1954, 6, 29);
		book.setPublicationDate(pubdate.getTime());
		library.getBooks().add(book);
		
		book = factory.createBook();
		book.setTitle("The Lord of the Ring — The Two Towers");
		book.setCategory(BookCategory.SCIENCE_FICTION_LITERAL);
		book.setPages(523);
		book.setAuthor(tolkien);
		pubdate = Calendar.getInstance();
		pubdate.set(1954, 10, 11);
		book.setPublicationDate(pubdate.getTime());
		library.getBooks().add(book);
		
		book = factory.createBook();
		book.setTitle("The Lord of the Ring — The Return of the King");
		book.setCategory(BookCategory.SCIENCE_FICTION_LITERAL);
		book.setPages(639);
		book.setAuthor(tolkien);
		pubdate = Calendar.getInstance();
		pubdate.set(1955, 9, 20);
		book.setPublicationDate(pubdate.getTime());
		library.getBooks().add(book);
		
		VideoCassette videoK7 = factory.createVideoCassette();
		videoK7.setTitle("Tapes are vintage, then they are hype");
		videoK7.setDamaged(true);
		videoK7.setCopies(3);
		pubdate = Calendar.getInstance();
		pubdate.set(1983, 3, 7);
		videoK7.setPublicationDate(pubdate.getTime());
		library.getStock().add(videoK7);

		resourceSet = new ResourceSetImpl();
		resource = new ResourceImpl(URI.createURI("demo.xmi"));
		resource.getContents().add(library);
		resourceSet.getResources().add(resource);

		ResourceImpl extlibraryEcore = new ResourceImpl(URI.createURI("extlibrary.ecore"));
		extlibraryEcore.getContents().add(EXTLibraryPackage.eINSTANCE);
		resourceSet.getResources().add(extlibraryEcore);
	}
	
	public Book createBook(String title) {
		Book book = factory.createBook();
		book.setTitle(title);
		library.getBooks().add(book);
		return book;
	}

	public Library getLibrary() {
		return library;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public ResourceSet getResourceSet() {
		return resourceSet;
	}
	
	public Resource getTreeNode() {
		Resource ret = new ResourceImpl(URI.createURI("tree node resource"));
		
		TreeNode libraryNode = TreeFactory.eINSTANCE.createTreeNode();
		libraryNode.setData(library);
		
		ret.getContents().add(libraryNode);
		
		for (Item item : library.getStock()) {
			TreeNode itemNode = TreeFactory.eINSTANCE.createTreeNode();
			itemNode.setData(item);
			libraryNode.getChildren().add(itemNode);
			if (item instanceof Book) {
				TreeNode writerNode = TreeFactory.eINSTANCE.createTreeNode();
				writerNode.setData(((Book) item).getAuthor());
				itemNode.getChildren().add(writerNode);
			}
			
		}
		return ret;
		
	}
}
