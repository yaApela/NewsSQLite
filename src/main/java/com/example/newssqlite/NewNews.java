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

public class NewNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_news);

        DatabaseHelper db = new DatabaseHelper(this);

        EditText txtLabel = findViewById(R.id.txtLabel);
        EditText txtNews = findViewById(R.id.txtNews);
        Button btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(view -> createNews(db, txtLabel.getText().toString().trim(), txtNews.getText().toString().trim()));
    }

    private void createNews(DatabaseHelper db, String label, String news) {
        UserModel user = getIntent().getParcelableExtra("user");

        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());

        if (label.isEmpty() || news.isEmpty()) {
            Toast.makeText(this, "Пожалуйста заполните поля", Toast.LENGTH_SHORT).show();
        }
        else {
            db.addNews(label, news, user.login, formatForDateNow.format(date));

            Intent adminNews =  new Intent(NewNews.this, AdminNews.class);
            adminNews.putExtra("user", user);

            startActivity(adminNews);
            finish();
        }
    }
}