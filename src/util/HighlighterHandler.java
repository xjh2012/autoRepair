package util;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class HighlighterHandler {
	public static String getContent(String filePath)
	{
		 String code = new CodeFileReader().getCode(new File(filePath));
	     Analyzer analyzer = new StandardAnalyzer();
	     return code;
	}
	
	
    public static String[] getHightlingterContent(String searchCode,String filePath, String type){
        String text = new CodeFileReader().getCode(new File(filePath));
        Analyzer analyzer = new StandardAnalyzer();
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        QueryParser queryParser = new QueryParser("code", analyzer);
        try {
        
            @SuppressWarnings("static-access")
			Query query = queryParser.parse(queryParser.escape(searchCode));
            @SuppressWarnings("static-access")
			//Query query = queryParser.parse(queryParser.WILDTERM);
             Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
            highlighter.setTextFragmenter(new SimpleFragmenter(text.length()));
            if (text != null) {
                TokenStream tokenStream = analyzer.tokenStream("code", new StringReader(text));
                String highLightText = highlighter.getBestFragment(tokenStream, text);
                if(highLightText!=null){
                    String[] result = highLightText.split("\n");
                    return result;
                }else {
                    return new String[]{};
                }
            }else{
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
