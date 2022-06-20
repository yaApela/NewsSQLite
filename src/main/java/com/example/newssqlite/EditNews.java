package com.example.newssqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        DatabaseHelper db = new DatabaseHelper(this);

        NewsModel newsModel = getIntent().getParcelableExtra("news");
        UserModel userModel = getIntent().getParcelableExtra("user");

        EditText txtLabel = findViewById(R.id.txtLabel);
        EditText txtNews = findViewById(R.id.txtNews);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);

        txtLabel.setText(newsModel.label);
        txtNews.setText(newsModel.news);

        btnDelete.setOnClickListener(view -> deleteNews(db, newsModel, userModel));

        btnEdit.setOnClickListener(view -> updateNews(db, newsModel, userModel, txtLabel.getText().toString().trim(),
                txtNews.getText().toString().trim()));
    }

    private void updateNews(DatabaseHelper db, NewsModel newsModel, UserModel userModel, String label, String news) {
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());

        if (label.isEmpty() || news.isEmpty()) {
            Toast.makeText(this, "Пожалуйста заполните поля", Toast.LENGTH_SHORT).show();
        }
        else {
            newsModel.label = label;
            newsModel.news = news;
            newsModel.date = formatForDateNow.format(date);
            db.updateNews(newsModel);

            Intent adminNews =  new Intent(EditNews.this, AdminNews.class);
            adminNews.putExtra("user", userModel);

            startActivity(adminNews);
            finish();
        }
    }

    private void deleteNews(DatabaseHelper db, NewsModel newsModel, UserModel userModel) {
        db.deleteNews(newsModel);

        Intent adminNews = new Intent(EditNews.this, AdminNews.class);
        adminNews.putExtra("user", userModel);

        startActivity(adminNews);
        finish();
    }
}