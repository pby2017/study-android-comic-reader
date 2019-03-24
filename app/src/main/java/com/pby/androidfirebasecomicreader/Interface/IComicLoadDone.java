package com.pby.androidfirebasecomicreader.Interface;

import com.pby.androidfirebasecomicreader.Model.Comic;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<Comic> comics);
}

