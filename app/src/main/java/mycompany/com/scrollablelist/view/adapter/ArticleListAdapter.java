package mycompany.com.scrollablelist.view.adapter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import mycompany.com.scrollablelist.R;
import mycompany.com.scrollablelist.model.Article;
import mycompany.com.scrollablelist.view.callback.ArticleClickCallback;
import mycompany.com.scrollablelist.view.ui.GlideApp;
import mycompany.com.scrollablelist.view.ui.util.UiUtils;

import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_FIRST_ITEM = 0;
    public static final int TYPE_GRID_ITEM = 1;
    List<? extends Article> mArticleList;

    @Nullable
    private final ArticleClickCallback mArticleClickCallback;

    public ArticleListAdapter(@Nullable ArticleClickCallback articleClickCallback) {
        mArticleClickCallback = articleClickCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_FIRST_ITEM) {
            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_prominant, parent, false);
            final ProminentViewHolder prominentViewHolder = new ProminentViewHolder(view);
            return prominentViewHolder;

        } else if (viewType == TYPE_GRID_ITEM) {
            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            GridViewHolder gridViewHolder = new GridViewHolder(view);
            return gridViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Article article = mArticleList.get(position);
        int cardWidth = UiUtils.getDisplayWidth(holder.itemView.getContext());
        switch (holder.getItemViewType()) {
            case TYPE_FIRST_ITEM:
                ProminentViewHolder prominentViewHolder = (ProminentViewHolder) holder;
                GlideApp.with(prominentViewHolder.mImageViewArticleImage.getContext())
                        .load(article.imageURL)
                        .override(cardWidth, cardWidth)
                        .placeholder(R.drawable.article_image_placeholder)
                        .error(R.drawable.article_image_error)
                        .into(prominentViewHolder.mImageViewArticleImage);
                prominentViewHolder.mTextViewArticleTitle.setText(article.title);
                prominentViewHolder.mTextViewArticleDescription.setText(article.description);
                if (mArticleList.size() > 1) prominentViewHolder.mPreviousArticleHeader.setVisibility(View.VISIBLE);
                prominentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        mArticleClickCallback.onClick(article);

                    }
                });
                break;
            case TYPE_GRID_ITEM:
                GridViewHolder gridViewHolder = (GridViewHolder) holder;

                GlideApp.with(gridViewHolder.mImageViewArticleImage.getContext())
                        .load(article.imageURL)
                        .override(cardWidth / 2, cardWidth / 2)
                        .placeholder(R.drawable.article_image_placeholder)
                        .error(R.drawable.article_image_error)
                        .into(gridViewHolder.mImageViewArticleImage);

                gridViewHolder.mTextViewArticleTitle.setText(article.title);
                gridViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        mArticleClickCallback.onClick(article);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList == null ? 0 : mArticleList.size();
    }

    public void setArticleList(final List<? extends Article> articleList) {
        if (this.mArticleList == null) {
            this.mArticleList = articleList;
            notifyItemRangeInserted(0, articleList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ArticleListAdapter.this.mArticleList.size();
                }

                @Override
                public int getNewListSize() {
                    return articleList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ArticleListAdapter.this.mArticleList.get(oldItemPosition).title.equals(articleList.get(newItemPosition).title);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Article article = articleList.get(newItemPosition);
                    Article old = articleList.get(oldItemPosition);
                    return article.title.equals(old.title)
                            && article.link.equals(old.link);
                }
            });
            this.mArticleList = articleList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST_ITEM;
        } else return TYPE_GRID_ITEM;
    }

    final class ProminentViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewArticleTitle;
        public TextView mTextViewArticleDescription;
        public ImageView mImageViewArticleImage;
        public TextView mPreviousArticleHeader;

        public ProminentViewHolder(View view) {
            super(view);
            mTextViewArticleTitle = view.findViewById(R.id.article_title_prominant);
            mTextViewArticleDescription = view.findViewById(R.id.article_description_prominant);
            mImageViewArticleImage = view.findViewById(R.id.article_image_prominant);
            mPreviousArticleHeader = view.findViewById(R.id.previous_article_header);
        }
    }

    final class GridViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewArticleTitle;
        public ImageView mImageViewArticleImage;

        public GridViewHolder(View view) {
            super(view);
            mTextViewArticleTitle = view.findViewById(R.id.article_title);
            mImageViewArticleImage = view.findViewById(R.id.article_image);
        }
    }
}
