package mycompany.com.scrollablelist.view.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import mycompany.com.scrollablelist.R;
import mycompany.com.scrollablelist.model.Article;

public class ArticleFragment extends Fragment {

    private static final String KEY_ARTICLE_TITLE = "article_title";
    private static final String KEY_ARTICLE_LINK = "article_link";
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_webview, container, false);
        mWebView = (WebView) view.findViewById(R.id.webView_article);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String webViewLink = getArguments().getString(KEY_ARTICLE_LINK);
        String webViewTitle = getArguments().getString(KEY_ARTICLE_TITLE);
        getActivity().setTitle(webViewTitle);
        mWebView.loadUrl(webViewLink);
    }

    public static ArticleFragment setArticleFragment(Article article) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ARTICLE_LINK, article.link);
        args.putString(KEY_ARTICLE_TITLE, article.title);
        fragment.setArguments(args);
        return fragment;
    }
}
