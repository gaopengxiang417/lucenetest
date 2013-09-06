package com.gao.first;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * User: wangchen.gpx
 * Date: 13-9-6
 * Time: 下午4:02
 */
public class IndexSearch {
    public static void main(String[] args) throws IOException {
        String path = "D:/index";
        FSDirectory directory = FSDirectory.open(new File(path));
        DirectoryReader directoryReader = DirectoryReader.open(directory);

        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        Term china = new Term("content","love");
        TermQuery termQuery = new TermQuery(china);
        TopDocs topDocs = indexSearcher.search(termQuery, 6);

        System.out.println("查询的结果总数:" + topDocs.totalHits + "评分是:" + topDocs.getMaxScore());
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            System.out.println("content====" + document.get("content"));
            System.out.println(scoreDoc.score + "," + scoreDoc.shardIndex );
        }

        directoryReader.close();

    }
}
