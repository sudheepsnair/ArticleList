package mycompany.com.scrollablelist.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import mycompany.com.scrollablelist.R;
import mycompany.com.scrollablelist.databinding.ArticleFragmentBinding;
import mycompany.com.scrollablelist.model.Article;
import mycompany.com.scrollablelist.view.adapter.ArticleListAdapter;
import mycompany.com.scrollablelist.view.callback.ArticleClickCallback;
import mycompany.com.scrollablelist.viewmodel.ArticleListViewModel;


import java.util.List;

public class ArticleListFragment extends Fragment {
    public static final String TAG = "ArticleListFragment";

    private ArticleListAdapter articleAdapter;
    private ArticleFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.article_fragment, container, false);
        articleAdapter = new ArticleListAdapter(articleClickCallback);

        final int columnCount = getResources().getBoolean(R.bool.isTablet) ? 3 : 2;


        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), columnCount, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (articleAdapter.getItemViewType(position)) {
                    case ArticleListAdapter.TYPE_FIRST_ITEM:
                        return columnCount;
                    case ArticleListAdapter.TYPE_GRID_ITEM:
                        return 1;
                    default:
                        return 1;
                }
            }
        });

        binding.articleRecyclerView.setAdapter(articleAdapter);
        binding.articleRecyclerView.setLayoutManager(mLayoutManager);
        binding.setIsLoadingArticles(true);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ArticleListViewModel viewModel =
                ViewModelProviders.of(this).get(ArticleListViewModel.class);
        observeViewModel(viewModel);
    }

    private void observeViewModel(ArticleListViewModel viewModel) {
        viewModel.getArticleListObservable().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    binding.setIsLoadingArticles(false);
                    articleAdapter.setArticleList(articles);
                }
            }
        });
    }

    private final ArticleClickCallback articleClickCallback = new ArticleClickCallback() {
        @Override
        public void onClick(Article article) {
            if (article == null) return;
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(article);
            }
        }
    };
}
