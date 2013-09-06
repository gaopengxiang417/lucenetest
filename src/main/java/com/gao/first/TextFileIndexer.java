package com.gao.first;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.nio.file.*;

/**
 * User: wangchen.gpx
 * Date: 13-9-4
 * Time: 下午9:29
 */
public class TextFileIndexer {
    public static void main(String[] args) throws IOException {
        //指文件夹的位置
        Path fileDir = Paths.get("D:/test");

        //存放索引的位置
        Path indexDir = Paths.get("D:/index");

        //建立一个标准的分析器
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_44);
        //创建一个索引器
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, standardAnalyzer);
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir.toFile()), indexWriterConfig);

        //列出所有文件
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fileDir);
        long start = System.currentTimeMillis();

        //添加document到索引中
        for (Path path : directoryStream) {
            if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS) && path.getFileName().toString().endsWith(".txt")) {
                System.out.println("File:" + path.toAbsolutePath() + "正在被索引");
                String temp = readAllLine(path, "gbk");
                System.out.println(temp);

                //new一个document
                Document document = new Document();
                //new一个字段
                StringField fieldPath = new StringField("path", path.toAbsolutePath().toString(), Field.Store.YES);
                StringField bodyPath = new StringField("body", temp, Field.Store.YES);

                document.add(fieldPath);
                document.add(bodyPath);

                indexWriter.addDocument(document);
            }
        }

        indexWriter.close();

        long end = System.currentTimeMillis();
        System.out.println("话费了" + (end -start) + "毫秒来执行文件添加到索引:" + fileDir);


    }

    private static String readAllLine(Path path, String charset) {
//        InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
        String temp = "";
        try (InputStream inputStream = Files.newInputStream(path , StandardOpenOption.READ);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream , charset))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                temp += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
