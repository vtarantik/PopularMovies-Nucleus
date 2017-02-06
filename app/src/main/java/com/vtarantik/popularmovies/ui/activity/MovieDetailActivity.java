package com.vtarantik.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.vtarantik.popularmovies.R;
import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.mvp.presenter.MovieDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vtarantik on 10.1.2017.
 */

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @BindView(R.id.mainactivity_toolbar_title)
    TextView toolbarTitle;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState == null) {
            Intent startIntent = getIntent();
            if(startIntent!=null){
                movie = startIntent.getParcelableExtra(MovieDetailPresenter.MOVIE_KEY);
            replaceFragment(com.vtarantik.popularmovies.ui.fragment.MovieDetailFragment.class.getName(),getIntent().getExtras());
            }
        }else{
            movie = savedInstanceState.getParcelable(MovieDetailPresenter.MOVIE_KEY);
        }

        setTitle("");
        toolbarTitle.setText(movie.getTitle());
    }

    private void replaceFragment(String fragmentName,Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(fragmentName);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentName,args);
        }
        fm.beginTransaction().replace(R.id.detailactivity_fragment_container, fragment, fragmentName).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MovieDetailPresenter.MOVIE_KEY,movie);

        super.onSaveInstanceState(outState);
    }

}
