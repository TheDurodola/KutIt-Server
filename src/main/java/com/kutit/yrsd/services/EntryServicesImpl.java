package com.kutit.yrsd.services;

import com.kutit.yrsd.data.models.Entry;
import com.kutit.yrsd.data.repositories.Entries;
import com.kutit.yrsd.dtos.requests.CreateEntryRequest;
import com.kutit.yrsd.dtos.requests.GetEntryRequest;
import com.kutit.yrsd.dtos.responses.CreateEntryResponse;
import com.kutit.yrsd.dtos.responses.GetEntryResponse;
import com.kutit.yrsd.exceptions.InvalidShortenLinkException;
import com.kutit.yrsd.utils.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.kutit.yrsd.utils.Mappers.map;
import static com.kutit.yrsd.utils.Mappers.map;
import static com.kutit.yrsd.utils.Validator.validate;

@Service
public class EntryServicesImpl implements EntryServices {

    @Autowired
    Entries entries;

    @Override
    public CreateEntryResponse createdEntry(CreateEntryRequest request) {
        validate(request);
        if (entries.findByOriginalLink(request.getOriginalLink()).isPresent()){
            Entry entry = entries.findByOriginalLink(request.getOriginalLink()).get();
            return Mappers.map(entries.save(entry));
        }
        return Mappers.map(entries.save(map(request)));
    }

    @Override
    public GetEntryResponse getEntry(GetEntryRequest request) {
        if(entries.findByShortenedLink(request.getShortendLink()).isPresent()){
            GetEntryResponse response = new GetEntryResponse();
            Entry entry = entries.findByShortenedLink(request.getShortendLink()).get();
            entry.setClick(entry.getClick()+1);
            entries.save(entry);
            response.setOriginalLink(entry.getOriginalLink());
            response.setClicks(entry.getClick());
            return response;
        }

        throw new InvalidShortenLinkException("Shorten Link doesn't exists");
    }


}
