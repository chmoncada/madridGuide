package com.charlesmoncada.madridguide.manager.db;


import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface DAOPersistable<T> {
    long insert(@NonNull T data);
    void update(final long id, final @NonNull T data);
    int delete(final long id);
    int deleteAll();
    @Nullable Cursor queryCursor();
    T query(final long id);
    @Nullable List<T> query();
}

