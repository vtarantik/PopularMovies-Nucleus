package com.vtarantik.popularmovies.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.vtarantik.popularmovies.R;
import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.helper.RecyclerViewHelper;
import com.vtarantik.popularmovies.mvp.presenter.MovieDetailPresenter;
import com.vtarantik.popularmovies.mvp.presenter.MoviesPresenter;
import com.vtarantik.popularmovies.mvp.view.IMoviesView;
import com.vtarantik.popularmovies.ui.activity.MovieDetailActivity;
import com.vtarantik.popularmovies.ui.adapter.MoviesAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by vtarantik on 10.1.2017.
 */

@RequiresPresenter(MoviesPresenter.class)
public class MoviesFragment extends NucleusSupportFragment<MoviesPresenter> implements RecyclerViewHelper.RecyclerViewHelperInterface, IMoviesView{
    private static final String TAG = MoviesFragment.class.getSimpleName();

    private static final long SEARCH_DELAY_MILLIS = 300;

    protected RecyclerViewHelper recyclerViewHelper;

    protected MoviesAdapter adapter;

    private Subscription searchSubscribtion;

    @BindView(R.id.moviesfragment_edittext_search)
    EditText editTextSearch;

    @BindView(R.id.moviesfragment_load_more_layout)
    LinearLayout loadMoreLayout;

    private RecyclerViewHelper.OnLoadingNewPageCompleteCallback onLoadingNewPageCompleteCallback = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies,container,false);

        ButterKnife.bind(this,v);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        initSearchView();
    }

    private void initRecyclerView(View view) {
        adapter = new MoviesAdapter(null);

        recyclerViewHelper = RecyclerViewHelper.attach(this);
        recyclerViewHelper.onViewCreated(view);
        recyclerViewHelper.setEmptyResId(R.layout.view_emtpy_list);

        recyclerViewHelper.setOnItemClickListener((recyclerView, position, v) -> {
            Intent i = new Intent(getActivity(), MovieDetailActivity.class);
            i.putExtras(MovieDetailPresenter.getArguments(adapter.getItem(position)));
            startActivity(i);
        });

        enableRecyclerScrollListener(true);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void showData(List<Movie> data) {
        adapter.setData(data);
        if(onLoadingNewPageCompleteCallback!=null){
            showLoadMoreLayout(false);
            onLoadingNewPageCompleteCallback.onLoadingNewPageFinished();
            onLoadingNewPageCompleteCallback=null;
        }
    }

    @Override
    public void showProgress(boolean show) {
        recyclerViewHelper.showProgress(show);
    }

    @Override
    public void showEmpty(boolean show) {
        recyclerViewHelper.showEmpty(show);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchSubscribtion != null && !searchSubscribtion.isUnsubscribed()) {
            searchSubscribtion.unsubscribe();
        }
    }

    private void initSearchView() {
        searchSubscribtion = RxTextView
                .textChangeEvents(editTextSearch)
                .doOnNext(event -> {
                    if(TextUtils.isEmpty(event.text())){
                        enableRecyclerScrollListener(true);
                        getPresenter().viewStoredMovies();
                    }else{
                        enableRecyclerScrollListener(false);
                    }
                })
                .debounce(SEARCH_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onTextChangeEvent -> {
                    Log.d(TAG, "query");
                    boolean emptyQuery = TextUtils.isEmpty(onTextChangeEvent.text());
                    if (!emptyQuery) {
                        getPresenter().onSearchQuery(onTextChangeEvent.text().toString());
                    }
                });

    }

    private void showLoadMoreLayout(boolean show){
        if(loadMoreLayout!=null){
            loadMoreLayout.setVisibility(show?View.VISIBLE:View.GONE);
        }
    }

    private void enableRecyclerScrollListener(boolean enable){
        if(recyclerViewHelper!=null){
            if(enable){
                recyclerViewHelper.setOnScrolledToBottomListener(new RecyclerViewHelper.OnScrolledToBottomListener() {
                    @Override
                    public void onScrolledToBottom(RecyclerViewHelper.OnLoadingNewPageCompleteCallback onLoadingNewPageCompleteCallback) {
                        Log.d(TAG,"onScrolledToBottom");
                        showLoadMoreLayout(true);
                        MoviesFragment.this.onLoadingNewPageCompleteCallback = onLoadingNewPageCompleteCallback;
                        getPresenter().downloadNextPage();
                    }
                });
            }else{
                recyclerViewHelper.setOnScrolledToBottomListener(null);
            }
        }
    }
}
