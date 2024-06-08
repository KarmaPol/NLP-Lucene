package queryParser;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.CharsRef;

import searchEngine.SynonymAnalyzer;

public class QueryParserUtil {

    private QueryParser parser;

    public QueryParserUtil() {
        try {
            SynonymAnalyzer snonymAnalyzer = getSynonymAnalyzer();
            this.parser = new QueryParser("text", snonymAnalyzer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SynonymAnalyzer getSynonymAnalyzer() throws IOException {
        SynonymMap synonymMap = getSynonymMap();
        return new SynonymAnalyzer(synonymMap);
    }

    // 입력받은 문장을 하나의 검색어로 파싱
    public Query parseQuery(String userInput) throws ParseException {
        return parser.parse(userInput);
    }

    // 동의어 사전 설정
    private static SynonymMap getSynonymMap() throws IOException {
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        builder.add(new CharsRef("hey"), new CharsRef("hi"), true);
        builder.add(new CharsRef("hey"), new CharsRef("heh"), true);
        return builder.build();
    }
}
