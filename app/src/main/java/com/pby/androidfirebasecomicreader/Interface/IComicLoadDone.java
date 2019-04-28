package com.pby.androidfirebasecomicreader.Interface;

import com.pby.androidfirebasecomicreader.model.Comic;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<Comic> comics);
}

