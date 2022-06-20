package com.example.newssqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleAdapterNews extends RecyclerView.Adapter<RecycleAdapterNews.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<NewsModel> news;
    private static OnClickListener onClickListener;

    public interface OnClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnClickListener onClickListener) {
        RecycleAdapterNews.onClickListener = onClickListener;
    }

    public RecycleAdapterNews(Context context, List<NewsModel> news) {
        this.inflater = LayoutInflater.from(context);
        this.news = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_recycle_view_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel newsModel = news.get(position);

        holder.label.setText(newsModel.label);
        holder.news.setText(newsModel.news);
        holder.author.setText("Автор: " +  newsModel.author);
        holder.date.setText("Дата: " + newsModel.date);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView label;
        final TextView news;
        final TextView author;
        final TextView date;

        public ViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
            label = view.findViewById(R.id.label);
            news = view.findViewById(R.id.news);
            author = view.findViewById(R.id.author);
            date = view.findViewById(R.id.date);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(getAdapterPosition(), view);
        }
    }
}
