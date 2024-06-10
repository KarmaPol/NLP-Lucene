import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import searchEngine.SearchEngine;
import searchEngine.SearchResponse;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchEngineTest {

    private SearchEngine searchEngine;

    @BeforeEach
    void setUp() throws IOException {
        String indexDir = "scripts.index"; // 실제 인덱스 경로로 변경
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        searchEngine = new SearchEngine(indexDir);
    }

    @Test
    void testSearch() throws IOException, ParseException {
        String userInput = "What's going on, Adams?"; // 검색할 쿼리 입력

        // 실제 데이터를 사용하여 검색 실행
        List<SearchResponse> actualResponses = searchEngine.search(userInput);

        // 검색 결과 확인
        for (SearchResponse response : actualResponses) {
            System.out.println("Text: " + response.text());
            System.out.println("Character: " + response.character());
            System.out.println("Movie Name: " + response.movieName());
            System.out.println("Movie Author: " + response.movieAuthor());
            System.out.println("Index: " + response.scriptIndex());
        }

        // 검색 결과를 기반으로 한 검증
        int expectedSize = 10;
        assertEquals(expectedSize, actualResponses.size());
    }

    @Test
    void testSynonymSearch() throws IOException, ParseException {
        String userInput = "Wat's going on, Adams?"; // 검색할 쿼리 입력

        // 실제 데이터를 사용하여 검색 실행
        List<SearchResponse> actualResponses = searchEngine.search(userInput);

        // 검색 결과 확인
        for (SearchResponse response : actualResponses) {
            System.out.println("Text: " + response.text());
            System.out.println("Character: " + response.character());
            System.out.println("Movie Name: " + response.movieName());
            System.out.println("Movie Author: " + response.movieAuthor());
            System.out.println("Index: " + response.scriptIndex());
        }

        // 검색 결과를 기반으로 한 검증
        int expectedSize = 10;
        assertEquals(expectedSize, actualResponses.size());
    }
}
