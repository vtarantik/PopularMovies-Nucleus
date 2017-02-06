package com.vtarantik.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vtarantik.popularmovies.R;
import com.vtarantik.popularmovies.mvp.presenter.MainPresenter;
import com.vtarantik.popularmovies.mvp.view.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by vtarantik on 10.1.2017.
 */

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainPresenter> implements IMainView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if (savedInstanceState == null) {
            replaceFragment(com.vtarantik.popularmovies.ui.fragment.MoviesFragment.class.getName());
        }

        setTitle("");
    }

    private void replaceFragment(String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(fragmentName);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentName);
        }
        fm.beginTransaction().replace(R.id.mainactivity_fragment_container, fragment, fragmentName).commit();
    }
}
