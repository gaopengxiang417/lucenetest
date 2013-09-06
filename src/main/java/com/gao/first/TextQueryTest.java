package com.gao.first;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.CheckHits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-9-4
 * Time: 下午10:38
 */
public class TextQueryTest {
    public static void main(String[] args) throws IOException, ParseException {
        FSDirectory directory = FSDirectory.open(Paths.get("D:/index").toFile());
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        String queryString = "全国";
        Query query = null;
        TopDocs topDocs = null;

        StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_44);

        QueryParser queryParser = new QueryParser(Version.LUCENE_44, "body", standardAnalyzer);
        query = queryParser.parse(queryString);

        if (indexSearcher != null) {
            topDocs = indexSearcher.search(query, 100);
        }

        int totalHits = topDocs.totalHits;
        System.out.println("总共有多少命中率:" + totalHits);
    }
}
