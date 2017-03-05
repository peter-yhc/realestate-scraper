package org.pyhc.propertyfinder.scraper;

import org.pyhc.propertyfinder.scraper.realestate.result.PropertyProfile;
import org.pyhc.propertyfinder.scraper.realestate.query.Query;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Scraper {

    CompletableFuture<List<Query>> search(SearchOptions query);

    CompletableFuture<PropertyProfile> queryProfilePage(Query query);

}
