package org.pyhc.propertyfinder.scraper;

import org.pyhc.propertyfinder.archive.PropertyArchiverPort;
import org.pyhc.propertyfinder.scraper.model.Query;
import org.pyhc.propertyfinder.scraper.realestate.RealEstateProfileParser;
import org.pyhc.propertyfinder.scraper.realestate.RealEstateSearchParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class HttpWebScraper implements WebScraper {

    @Autowired
    private CompletableRestTemplate completableRestTemplate;

    @Autowired
    private PropertyArchiverPort propertyArchiverPort;

    @Override
    public CompletableFuture<List<Query>> search(Query query) {
        return completableRestTemplate.performGet(query)
                .thenApply(RealEstateSearchParser::parse)
                .thenApply(searchResult -> {
                    List<Query> queries = searchResult.getProfileLinks().stream().collect(Collectors.toList());
                    if (searchResult.hasNextPageLink()) {
                        search(searchResult.getNextPageLink()).thenAccept(queries::addAll).join();
                    }
                    return queries;
                });
    }

    @Override
    public void queryProfilePage(Query query) {
        try {
            completableRestTemplate.performGet(query)
                    .thenApply(document -> RealEstateProfileParser.parse(document, query.toString()))
                    .thenAccept(profile -> propertyArchiverPort.archive(profile))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}