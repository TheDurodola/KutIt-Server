package com.kutit.yrsd.dtos.responses;
import com.kutit.yrsd.data.models.Entry;
import lombok.Data;

import java.util.List;

@Data
public class GetAllUserEntriesResponse {
    List<Entry> entries;
}
