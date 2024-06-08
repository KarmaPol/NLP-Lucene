package searchEngine;

public record SearchResponse(

        String text,
        String character,
        String movieName,
        String movieAuthor,
        Integer scriptIndex
) {
}

