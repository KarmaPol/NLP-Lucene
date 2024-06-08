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
    public IndexSearcher searcher;
    private QueryParserUtil queryParserUtil;

    public SearchEngine(String indexDir) throws IOException {
        // 주어진 경로로부터 루씬 인덱스 open
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDir));
        DirectoryReader reader = DirectoryReader.open(indexDirectory);
        this.searcher = new IndexSearcher(reader);
        this.queryParserUtil = new QueryParserUtil();
    }

    // 검색 메소드
    public List<SearchResponse> search(String userInput) throws IOException, ParseException {
        return null;
    }

    public void setQueryParserUtil(QueryParserUtil queryParserUtil) {
    }
}
