package com.example.newssqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button btnReg = findViewById(R.id.btnReg);
        Button btnAuth = findViewById(R.id.btnAuth);

        btnAuth.setOnClickListener(view -> {
            Intent auth = new Intent(this, Authorization.class);

            startActivity(auth);
            finish();
        });

        btnReg.setOnClickListener(view -> {
            EditText edtLogin = findViewById(R.id.login);
            EditText edtPassword = findViewById(R.id.password);
            CheckBox cbRole = findViewById(R.id.role);

            String login = edtLogin.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cbRole.isChecked()) {
                Registration(login, password, "Admin");
            }
            else {
                Registration(login, password, "User");
            }
        });
    }

    private void Registration(String login, String password, String role) {
        DatabaseHelper db = new DatabaseHelper(this);

        db.addUser(login, password, role);

        Intent auth = new Intent(this, Authorization.class);

        startActivity(auth);
        finish();
    }
}