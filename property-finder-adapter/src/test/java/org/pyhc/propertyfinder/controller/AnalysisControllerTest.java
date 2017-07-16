package org.pyhc.propertyfinder.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pyhc.propertyfinder.configuration.AdapterConfiguration;
import org.pyhc.propertyfinder.settings.SearchLocationPort;
import org.pyhc.propertyfinder.settings.SuburbDetails;
import org.pyhc.propertyfinder.settings.TestSuburbDetailsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.pyhc.propertyfinder.settings.TestSuburbDetailsBuilder.randomSuburbDetails;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdapterConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class AnalysisControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SearchLocationPort searchLocationPort;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void canRetrievePreviousSearches() throws Exception {
        SuburbDetails suburbDetails = randomSuburbDetails();
        when(searchLocationPort.getPreviousSearches()).thenReturn(
                singletonList(suburbDetails)
        );

        mockMvc.perform(get("/analysis").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.suburbs[0].suburbName", is(suburbDetails.getSuburbName())))
                .andExpect(jsonPath("$.suburbs[0].state", is(suburbDetails.getState())))
                .andExpect(jsonPath("$.suburbs[0].postcode", is(suburbDetails.getPostcode())))
                .andExpect(jsonPath("$.suburbs[0].uuid", is(suburbDetails.getUuid().toString())));
    }
}