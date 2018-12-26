package mycompany.com.scrollablelist.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import mycompany.com.scrollablelist.model.Article;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleRepositoryXML {

    private static ArticleRepositoryXML articleRepository;

    public ArticleRepositoryXML(){

    }

    public  synchronized  static ArticleRepositoryXML getInstance() {

        if (articleRepository == null) {
            articleRepository = new ArticleRepositoryXML();
        }
        return articleRepository;
    }

    public LiveData<List<Article>> getArticleList() {
        final MutableLiveData<List<Article>> data = new MutableLiveData<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                XMLArticleParser xmlArticleParser= new XMLArticleParser();
                xmlArticleParser.getAndParseArticle();
                data.postValue(xmlArticleParser.getArticlesList());
            }
        });

        return data;
    }
}
