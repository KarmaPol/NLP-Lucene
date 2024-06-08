package searchEngine;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;

import lombok.val;

public class SynonymAnalyzer extends Analyzer {
	private SynonymMap synonymMap;

	public SynonymAnalyzer(SynonymMap synonymMap) {
		this.synonymMap = synonymMap;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		StandardTokenizer tokenizer = new StandardTokenizer();
		SynonymGraphFilter synonymGraphFilter = new SynonymGraphFilter(tokenizer, synonymMap, true);
		LowerCaseFilter lowerCaseFilter = new LowerCaseFilter(synonymGraphFilter);
		return new TokenStreamComponents(tokenizer, lowerCaseFilter);
	}
}
