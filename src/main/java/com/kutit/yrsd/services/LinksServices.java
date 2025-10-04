package com.kutit.yrsd.services;

import com.kutit.yrsd.data.repositories.Links;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LinksServices {

    @Autowired
    Links links;

    public long count() {
        return links.count();
    }
}
