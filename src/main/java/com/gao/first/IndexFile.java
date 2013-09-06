package com.gao.first;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * User: wangchen.gpx
 * Date: 13-9-6
 * Time: 下午3:53
 */
public class IndexFile {
    static Directory dir;
    public static void main(String[] args) throws IOException {

        String[] ids = {"1", "2"};
        String[] content = {"i love this book", "china is a this cuntory"};
        String[] city = {"austrinan", "england"};


        dir = FSDirectory.open(new File("D:/index"));

        IndexWriter indexWriter = getIndexWriter();

        for (int i = 0 ;i < ids.length ; i++){
            Document document = new Document();
            document.add(new StringField("id" , ids[i] , Field.Store.YES));
            document.add(new TextField("content" , content[i] , Field.Store.YES));
            document.add(new StringField("city" , city[i] , Field.Store.YES));
            indexWriter.addDocument(document);
        }

        System.out.println("complete...");
        indexWriter.close();

    }

    public static IndexWriter getIndexWriter() throws IOException {
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_44);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, standardAnalyzer);

        return new IndexWriter(dir, indexWriterConfig);
    }
}
