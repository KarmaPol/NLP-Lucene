package searchEngine;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.core.UnicodeWhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;

public class SynonymAnalyzer extends Analyzer {
	private SynonymMap synonymMap;

	public SynonymAnalyzer(SynonymMap synonymMap) {
		this.synonymMap = synonymMap;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		UnicodeWhitespaceTokenizer tokenizer = new UnicodeWhitespaceTokenizer();
		SynonymGraphFilter synonymGraphFilter = new SynonymGraphFilter(tokenizer, synonymMap, true);
		LowerCaseFilter lowerCaseFilter = new LowerCaseFilter(synonymGraphFilter);
		return new TokenStreamComponents(tokenizer, lowerCaseFilter);
	}
}
