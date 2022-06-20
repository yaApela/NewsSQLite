package com.example.newssqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class UserNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_news);

        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<NewsModel> news = db.getNews();

        RecyclerView newsView = findViewById(R.id.list);
        Button btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(view -> {
            Intent auth = new Intent(this, Authorization.class);

            startActivity(auth);
            finish();
        });

        RecycleAdapterNews recycleAdapterNews = new RecycleAdapterNews(this, news);
        newsView.setAdapter(recycleAdapterNews);

        recycleAdapterNews.setOnItemClickListener(new RecycleAdapterNews.OnClickListener() {
            @Override
            public void onItemClick(int position, View view) {  }
        });
    }
}