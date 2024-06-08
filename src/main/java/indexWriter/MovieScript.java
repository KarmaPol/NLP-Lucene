package indexWriter;

public class MovieScript {
	private Long id;
	private Long movieId;
	private String text;
	private String character;
	private Integer index;

	public MovieScript(Long id, Long movieId, String text, String character, Integer index) {
		this.id = id;
		this.movieId = movieId;
		this.text = text;
		this.character = character;
		this.index = index;
	}
}
