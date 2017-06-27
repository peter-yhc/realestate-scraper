package org.pyhc.propertyfinder.settings;

import java.util.List;
import java.util.UUID;

public interface SearchLocationPort {

    List<SuburbDetails> getPreviousSearches();

    List<SuburbDetails> getSearchableLocations();

    void recordSearch(SuburbDetails suburbDetails);

    void removeSavedLocation(UUID savedLocationId);
}
