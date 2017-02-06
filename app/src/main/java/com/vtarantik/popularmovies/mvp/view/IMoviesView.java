package com.vtarantik.popularmovies.mvp.view;

import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.mvp.view.base.IListView;
import com.vtarantik.popularmovies.mvp.view.base.IProgressView;

/**
 * Created by vtarantik on 10.1.2017.
 */

public interface IMoviesView extends IListView<Movie>,IProgressView {
}
