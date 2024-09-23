package com.jesusfc.springbootartificialintelligence.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@Component
public class LoadArenaGSMVectorStore implements CommandLineRunner {

    final VectorStore vectorStoreArenaGSM;
    final VectorStoreProperties vectorStoreProperties;

    public LoadArenaGSMVectorStore(@Qualifier("vectorStore") VectorStore vectorStoreArenaGSM, VectorStoreProperties vectorStoreProperties) {
        this.vectorStoreArenaGSM = vectorStoreArenaGSM;
        this.vectorStoreProperties = vectorStoreProperties;
    }

    @Override
    public void run(String... args) throws Exception {

  //      if (vectorStoreArenaGSM.similaritySearch("Sportsman").isEmpty()) {

            System.out.println("Loading documents into vector store");

            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                System.out.println("Loading document: " + document.getFilename());

                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();

                TextSplitter textSplitter = new TokenTextSplitter();

                List<Document> splitDocuments = textSplitter.apply(documents);

                vectorStoreArenaGSM.add(splitDocuments);
            });
        //}

    }

}
