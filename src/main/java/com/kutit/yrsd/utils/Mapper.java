package com.kutit.yrsd.utils;

import com.kutit.yrsd.data.models.Entry;
import com.kutit.yrsd.dtos.responses.AddLinkResponse;

public class Mapper {


    public static AddLinkResponse map(Entry entry) {
        AddLinkResponse response = new AddLinkResponse();
        response.setShortened(entry.getShortened());
        return response;
    }

    public static Entry map(AddLinkRequest request) {
        Entry entry = new Entry();
        entry.setOriginal(request.getOriginal());
        return entry;
    }
}
