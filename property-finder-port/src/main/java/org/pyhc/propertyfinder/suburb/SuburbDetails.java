package org.pyhc.propertyfinder.suburb;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuburbDetails {

    @NotNull
    private String suburbName;

    @NotNull
    private String state;

    @NotNull
    private Integer postcode;

    @NotNull
    private UUID uuid;

}