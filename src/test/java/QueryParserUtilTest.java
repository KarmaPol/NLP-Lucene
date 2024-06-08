import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.junit.jupiter.api.Test;
import queryParser.QueryParserUtil;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryParserUtilTest {

    @Test
    public void testParseQuery() throws ParseException {

        QueryParserUtil queryParserUtil = new QueryParserUtil();
        String userInput = "example search text";

        Query query = queryParserUtil.parseQuery(userInput);

        assertNotNull(query);
        assertTrue(query.toString().contains("example"));
    }
}
