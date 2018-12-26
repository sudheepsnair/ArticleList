package mycompany.com.scrollablelist.view.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import mycompany.com.scrollablelist.R;
import mycompany.com.scrollablelist.model.Article;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ArticleListFragment fragment = new ArticleListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, fragment, ArticleListFragment.TAG).commit();
        }
    }

    public void show(Article article) {
        ArticleFragment articleFragment = ArticleFragment.setArticleFragment(article);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("article")
                .replace(R.id.fragment_holder,
                        articleFragment, null).commit();
    }
}
