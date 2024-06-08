package indexWriter;

import java.util.List;

public class IndexWriter {
	private static final MovieRepository movieRepository = new MovieRepository();
	public static void main(String[] args) {
		List<MovieScript> movieScripts = movieRepository.getMovieScripts();
	}
}
