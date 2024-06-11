package searchEngine;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import queryParser.QueryParserUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
    private IndexSearcher searcher;
    private QueryParserUtil queryParserUtil;

    public SearchEngine(String indexDir) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDir));
        DirectoryReader reader = DirectoryReader.open(indexDirectory);
        this.searcher = new IndexSearcher(reader);
        this.queryParserUtil = new QueryParserUtil();
    }

    // QueryParserUtil setter 메서드 추가
    public void setQueryParserUtil(QueryParserUtil queryParserUtil) {
        this.queryParserUtil = queryParserUtil;
    }

    public List<SearchResponse> search(String userInput) throws IOException, ParseException {
        Query query = queryParserUtil.parseQuery(userInput);
        TopDocs result = searcher.search(query, 15);
        List<SearchResponse> searchResponses = new ArrayList<>();
        for (ScoreDoc scoreDoc : result.scoreDocs) {
            Document document = searcher.doc(scoreDoc.doc);
            searchResponses.add(new SearchResponse(
                    document.get("text"),
                    document.get("character"),
                    document.get("movieName"),
                    document.get("movieAuthor"),
                    Integer.parseInt(document.get("index"))
            ));
        }
        return searchResponses;
    }
}
