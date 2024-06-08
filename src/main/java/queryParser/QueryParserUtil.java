package queryParser;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

public class QueryParserUtil {

    private QueryParser parser;

    public QueryParserUtil() {
        this.parser = new QueryParser("text", new StandardAnalyzer());
    }

    // 입력받은 문장을 하나의 검색어로 파싱
    public Query parseQuery(String userInput) throws ParseException {
        return parser.parse(userInput);
    }
}
