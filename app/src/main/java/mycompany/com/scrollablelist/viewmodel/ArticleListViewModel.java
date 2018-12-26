package mycompany.com.scrollablelist.viewmodel;

import android.app.Application;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import mycompany.com.scrollablelist.model.Article;
import mycompany.com.scrollablelist.repository.ArticleRepositoryXML;

import java.util.List;

public class ArticleListViewModel extends AndroidViewModel {
    private final LiveData<List<Article>> listArticleLiveData;

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        listArticleLiveData = ArticleRepositoryXML.getInstance().getArticleList();
    }

    public LiveData<List<Article>> getArticleListObservable() {
        return listArticleLiveData;
    }
}
