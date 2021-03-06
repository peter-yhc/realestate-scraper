package org.pyhc.propertyfinder.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.pyhc.propertyfinder.scraper.realestate.result.SoldPropertyProfile;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ProfileResultEvent {

    private SoldPropertyProfile soldPropertyProfile;

}
