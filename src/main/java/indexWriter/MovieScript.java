package indexWriter;

public record MovieScript(Long id,
						  Long movieId,
						  String text,
						  String character,
						  Integer index,
						  String movieName,
						  String movieAuthor) {
}
