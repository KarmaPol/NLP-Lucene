package searchEngine;

public record SearchResponse(

        String text,
        String character,
        String name,
        Integer scriptIndex
) {
}

