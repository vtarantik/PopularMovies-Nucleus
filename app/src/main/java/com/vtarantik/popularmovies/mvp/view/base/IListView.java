package com.vtarantik.popularmovies.mvp.view.base;

import java.util.List;

/**
 * Created by vtarantik on 10.1.2017.
 */

public interface IListView<T> {

    void showData(List<T> data);
}
