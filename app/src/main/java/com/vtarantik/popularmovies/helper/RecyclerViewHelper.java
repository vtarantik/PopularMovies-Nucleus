package com.vtarantik.popularmovies.helper;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.vtarantik.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vtarantik on 10.1.2017.
 */

public class RecyclerViewHelper extends Fragment {
    public static final String TAG = RecyclerViewHelper.class.getName();

    private static final String FRAG_TAG = RecyclerViewHelper.class.getCanonicalName();
    @BindView(android.R.id.list)
    RecyclerView mRecyclerView;

    @BindView(android.R.id.progress)
    View mProgressView;

    @BindView(android.R.id.empty)
    FrameLayout mEmpty;

    private OnScrolledToBottomListener onScrolledToBottomListener;

    private boolean isLoading = false;

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public interface RecyclerViewHelperInterface {
        public RecyclerView.LayoutManager getLayoutManager();

        RecyclerView.Adapter getAdapter();
    }

    public static <ParentFrag extends Fragment & RecyclerViewHelperInterface> RecyclerViewHelper attach(ParentFrag parent) {
        return attach(parent.getChildFragmentManager());
    }

    public static <ParentActivity extends AppCompatActivity & RecyclerViewHelperInterface> RecyclerViewHelper attach(ParentActivity parent) {
        return attach(parent.getSupportFragmentManager());
    }

    private static RecyclerViewHelper attach(FragmentManager fragmentManager) {
        RecyclerViewHelper frag = (RecyclerViewHelper) fragmentManager.findFragmentByTag(FRAG_TAG);
        if (frag == null) {
            frag = new RecyclerViewHelper();
            fragmentManager.beginTransaction().add(frag, FRAG_TAG).commit();
            fragmentManager.executePendingTransactions();
        }
        return frag;
    }

    private RecyclerViewHelperInterface getParent() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof RecyclerViewHelperInterface) {
            return (RecyclerViewHelperInterface) parentFragment;
        } else {
            Activity activity = getActivity();
            if (activity instanceof RecyclerViewHelperInterface) {
                return (RecyclerViewHelperInterface) activity;
            }
        }
        return null;
    }

    public void onViewCreated(View v) {
        ButterKnife.bind(this, v);
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (getParent() == null) { //delay a little this initialization because fragment transaction is not being processed right away
            new Handler().post(this::initRecyclerView);
            return;
        }
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(getParent().getLayoutManager());
        mRecyclerView.setAdapter(getParent().getAdapter());
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (layoutManager.findLastCompletelyVisibleItemPosition() != -1 && layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    if (onScrolledToBottomListener != null) {
                        if (!isLoading) {
                            isLoading = true;
                            onScrolledToBottomListener.onScrolledToBottom(new OnLoadingNewPageCompleteCallback() {
                                @Override
                                public void onLoadingNewPageFinished() {
                                    isLoading = false;
                                }
                            });

                        }
                    }
                }
            }
        });

    }

    public void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(!show ? View.VISIBLE : View.GONE);
    }

    public void setOnItemClickListener(ItemClickSupport.OnItemClickListener listener) {
        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemClickListener(listener);
    }

    public void setEmptyResId(int layoutResId) {
        mEmpty.removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutResId, mEmpty);
    }

    public void showEmpty(boolean show) {
        mEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void scrollToPosition(int position) {
        scrollToPosition(position, false);
    }

    public void scrollToPosition(int position, boolean animate) {
        if (animate) {
            mRecyclerView.smoothScrollToPosition(0);
        } else {
            mRecyclerView.scrollToPosition(position);
        }
    }

    public void setOnScrolledToBottomListener(OnScrolledToBottomListener onScrolledToBottomListener) {
        this.onScrolledToBottomListener = onScrolledToBottomListener;
    }

    public interface OnScrolledToBottomListener {
        void onScrolledToBottom(OnLoadingNewPageCompleteCallback onLoadingNewPageCompleteCallback);
    }

    public interface OnLoadingNewPageCompleteCallback {
        void onLoadingNewPageFinished();
    }
}
