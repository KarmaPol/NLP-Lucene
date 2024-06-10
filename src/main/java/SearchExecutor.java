import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import gui.GUI;
import searchEngine.SearchEngine;

public class SearchExecutor {

	public static void main(String[] args) throws IOException {
		SearchEngine searchEngine = getSearchEngine("scripts.index");
		GUI gui = new GUI(searchEngine);
		gui.display();
	}

	private static SearchEngine getSearchEngine(String indexDir) throws IOException {
		Directory directory = FSDirectory.open(Paths.get(indexDir));
		return new SearchEngine(indexDir);
	}

}
