package com.example.newssqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Planet.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Users(login TEXT PRIMARY KEY, password TEXT, role TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE News(id INTEGER PRIMARY KEY, label TEXT, main TEXT, author TEXT, date TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS News;");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users;");
    }


    public Cursor getData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + table + ";", null);
    }

    public void addUser(String login, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("login", login);
        contentValues.put("password", password);
        contentValues.put("role", role);

        db.insert("Users", null, contentValues);
    }

    public UserModel findUser(String login, String password) {
        Cursor data = getData("Users");

        while (data.moveToNext()) {
            String findedLogin = data.getString(0);
            String findedPassword = data.getString(1);

            if (login.equals(findedLogin) && password.equals(findedPassword)) {
                Boolean isAdmin = data.getString(2).equals("Admin");
                return new UserModel(findedLogin, findedPassword, isAdmin);
            }
        }
        data.close();

        return null;
    }

    public void addNews(String label, String news, String author, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("label", label);
        contentValues.put("main", news);
        contentValues.put("author", author);
        contentValues.put("date", date);

        db.insert("News", null, contentValues);
    }

    public void updateNews(NewsModel newsModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("label", newsModel.label);
        contentValues.put("main", newsModel.news);
        contentValues.put("author", newsModel.author);
        contentValues.put("date", newsModel.date);

        db.update("News", contentValues, "id = " + newsModel.id, null);
    }

    public void deleteNews(NewsModel newsModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("News", "id = " + newsModel.id, null);
    }

    public ArrayList<NewsModel> getNews() {
        ArrayList<NewsModel> news = new ArrayList<>();
        Cursor data = getData("News");

        while (data.moveToNext()) {
            int id = data.getInt(0);
            String label = data.getString(1);
            String main = data.getString(2);
            String author = data.getString(3);
            String date = data.getString(4);

            news.add(new NewsModel(id, label, main, author, date));
        }
        data.close();

        return news;
    }
}
