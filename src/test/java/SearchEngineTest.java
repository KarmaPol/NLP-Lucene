import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import queryParser.QueryParserUtil;
import searchEngine.SearchEngine;
import searchEngine.SearchResponse;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchEngineTest {

    private SearchEngine searchEngine;
    private QueryParserUtil queryParserUtil;

    @BeforeEach
    void setUp() {
        try {
            // SearchEngine 생성자 호출
            searchEngine = new SearchEngine("/path/to/indexDir");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Mock QueryParserUtil 객체 생성
        queryParserUtil = mock(QueryParserUtil.class);
        // SearchEngine에 Mock QueryParserUtil 설정
        searchEngine.setQueryParserUtil(queryParserUtil);
    }

    @Test
    void testSearch() throws IOException, ParseException {
        // 가짜 쿼리 생성
        String userInput = "test query";
        Query fakeQuery = mock(Query.class);
        // 가짜 쿼리 결과 생성
        TopDocs fakeResult = mock(TopDocs.class);
        // SearchEngine이 반환할 가짜 검색 결과 설정
        when(searchEngine.searcher.search(fakeQuery, 10)).thenReturn(fakeResult);
        // 가짜 검색 결과 생성
        List<SearchResponse> fakeResponses = List.of(
                new SearchResponse("text1", "character1", "name1", 1),
                new SearchResponse("text2", "character2", "name2", 2)
        );
        // 가짜 검색 결과 반환 설정
        when(searchEngine.searcher.search(fakeQuery, 10)).thenReturn(fakeResult);
        // 가짜 검색 결과에서 가짜 응답 생성
        // ...
        // 실제 검색 수행
        List<SearchResponse> actualResponses = searchEngine.search(userInput);
        // 결과 검증
        assertEquals(fakeResponses.size(), actualResponses.size());
        // 각 응답의 필드 비교
        for (int i = 0; i < fakeResponses.size(); i++) {
            SearchResponse expected = fakeResponses.get(i);
            SearchResponse actual = actualResponses.get(i);
            assertEquals(expected.text(), actual.text());
            assertEquals(expected.character(), actual.character());
            assertEquals(expected.name(), actual.name());
            assertEquals(expected.scriptIndex(), actual.scriptIndex());
        }
    }
}
