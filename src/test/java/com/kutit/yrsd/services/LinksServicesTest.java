package com.kutit.yrsd.services;

import com.kutit.yrsd.data.models.Link;
import com.kutit.yrsd.data.repositories.Links;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinksServicesTest {

    @Mock
    Links links;

    @InjectMocks
    LinksServices linksServices;


    @Test
    void addALink_LinksCountIs1() {

        Link link1 = new Link();
        link1.setOriginal("https://chatgpt.com/c/68dfcd5d-a50c-8326-b750-1887623a4713");

        when(links.count()).thenReturn(1l);


        long result = linksServices.count();
        assertEquals(1l, result);

        verify(links).findById("1");



    }
}