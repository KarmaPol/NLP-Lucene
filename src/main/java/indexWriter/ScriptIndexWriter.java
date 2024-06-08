package indexWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class ScriptIndexWriter {
	private static final MovieRepository movieRepository = new MovieRepository();

	public static void main(String[] args) throws IOException {
		List<MovieScript> movieScripts = movieRepository.getMovieScripts();
		writeIndexes(movieScripts);
	}

	private static void writeIndexes(List<MovieScript> movieScripts) throws IOException {
		File scriptsIndex = new File("scripts.index");
		FSDirectory dir = FSDirectory.open(Paths.get(scriptsIndex.toURI()));
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(dir, config);
		writer.deleteAll();

		movieScripts.forEach(movieScript -> {
			try {
				Document doc = new Document();
				doc.add(new StringField("id", movieScript.id().toString(), Field.Store.YES));
				doc.add(new StringField("movieId", movieScript.movieId().toString(), Field.Store.YES));
				doc.add(new TextField("text", movieScript.text(), Field.Store.YES));
				doc.add(new StringField("character", movieScript.character(), Field.Store.YES));
				doc.add(new StringField("index", movieScript.index().toString(), Field.Store.YES));

				writer.addDocument(doc);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		writer.commit();
		writer.close();
	}
}
