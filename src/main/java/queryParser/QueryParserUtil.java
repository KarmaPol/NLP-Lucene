package queryParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.CharsRef;

import searchEngine.SynonymAnalyzer;

public class QueryParserUtil {

    private static final String synonymFilePath = "synonym.csv";
    private QueryParser parser;

    public QueryParserUtil() {
        try {
            SynonymAnalyzer snonymAnalyzer = getSynonymAnalyzer();
            this.parser = new QueryParser("text", snonymAnalyzer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 입력받은 문장을 하나의 검색어로 파싱
    public Query parseQuery(String userInput) throws ParseException {
        return parser.parse(userInput);
    }

    public static SynonymAnalyzer getSynonymAnalyzer() throws IOException {
        SynonymMap synonymMap = getSynonymMap();
        return new SynonymAnalyzer(synonymMap);
    }

    // 동의어 사전 설정
    private static SynonymMap getSynonymMap() throws IOException {

        List<SynonymTokens> synonymTokensList = getSynonymTokens();

        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        synonymTokensList.forEach(synonymTokens ->
            builder.add(new CharsRef(synonymTokens.token1), new CharsRef(synonymTokens.token2), true));

        return builder.build();
    }

    // csv 파일에서 동의어 정보 얻기
    private static List<SynonymTokens> getSynonymTokens() {
        List<SynonymTokens> synonymTokensList = new ArrayList<>();

        try (Reader reader = new FileReader(synonymFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String token1 = csvRecord.get("token1");
                String token2 = csvRecord.get("token2");

                SynonymTokens synonymTokens = new SynonymTokens(token1, token2);
                synonymTokensList.add(synonymTokens);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return synonymTokensList;
    }

    private record SynonymTokens(String token1,
                                        String token2) {
    }
}
