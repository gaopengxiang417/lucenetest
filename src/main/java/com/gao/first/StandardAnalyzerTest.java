package com.gao.first;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;

/**
 * User: wangchen.gpx
 * Date: 13-9-4
 * Time: 下午11:13
 */
public class StandardAnalyzerTest {
    public static void main(String[] args) throws IOException {
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_44);

        StringReader stringReader = new StringReader("lighter hello world is open");

        TokenStream tokenStream = standardAnalyzer.tokenStream("", stringReader);

        int i = 0;
        boolean next = tokenStream.incrementToken();
        while (next){
            CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            String s = termAttribute.toString();
            i++;
            System.out.println("第" + i + "行的文本为:" + s);
            next = tokenStream.incrementToken();
        }
    }
}
