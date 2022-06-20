package com.example.newssqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class Authorization extends AppCompatActivity {
    BiometricPrompt.PromptInfo promptInfo;
    BiometricPrompt biometricPrompt;
    UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        DatabaseHelper db = new DatabaseHelper(this);
        EditText txtLogin = findViewById(R.id.txtLogin);
        EditText txtPassword = findViewById(R.id.txtPassword);
        Button btnReg = findViewById(R.id.btnReg);
        Button btnAuth = findViewById(R.id.btnAuth);
        Executor executor = ContextCompat.getMainExecutor(this);
        btnAuth.setOnClickListener(view -> Authorization(db, txtLogin.getText().toString().trim(), txtPassword.getText().toString().trim()));
        btnReg.setOnClickListener(view -> {
            Intent reg = new Intent(this, Registration.class);
            startActivity(reg);
            finish();
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Вход")
                .setNegativeButtonText("Назад")
                .build();
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Log.e("Auth", errString.toString());
                Toast.makeText(Authorization.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.w("Auth", "Success");
                Intent news;
                if (user.isAdmin) {
                    news = new Intent(Authorization.this, AdminNews.class);news.putExtra("user", user);} else {
                    news = new Intent(Authorization.this, UserNews.class);
                }
                startActivity(news);finish();
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Log.e("Auth", "Failed");
                Toast.makeText(Authorization.this, "Ошибка Авторизации\n. Попробуйте позже", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Authorization(DatabaseHelper db, String login, String password) {
        if (db.findUser(login, password) == null) {
            Toast.makeText(this, "Логин или пароль введены неверно", Toast.LENGTH_SHORT).show();
        }
        else {
            user = db.findUser(login, password);
            biometricPrompt.authenticate(promptInfo);
        }
    }
}