package com.example.newssqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AdminNews extends AppCompatActivity {
    ArrayList<NewsModel> news;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news);
        userModel = getIntent().getParcelableExtra("user");
        DatabaseHelper db = new DatabaseHelper(this);
        news = db.getNews();
        Button exit = findViewById(R.id.btnExit);
        Button addNews = findViewById(R.id.btnAdd);
        RecyclerView newsView = findViewById(R.id.list);
        RecycleAdapterNews recycleAdapterNews = new RecycleAdapterNews(this, news);
        newsView.setAdapter(recycleAdapterNews);
        recycleAdapterNews.setOnItemClickListener(new RecycleAdapterNews.OnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                NewsModel newsModel = news.get(position);
                Intent editNews = new Intent(AdminNews.this, EditNews.class);
                editNews.putExtra("user", userModel);
                editNews.putExtra("news", newsModel);
                startActivity(editNews);
                finish();
            }
        });
        exit.setOnClickListener(view -> {
            Intent auth = new Intent(this, Authorization.class);
            startActivity(auth);
            finish();
        });
        addNews.setOnClickListener(view -> {
            Intent newNews = new Intent(this, NewNews.class);
            newNews.putExtra("user", userModel);
            startActivity(newNews);
            finish();
        });

    }
}