package com.example.mobile_labs_20521348;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class login extends AppCompatActivity {

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5Password = number.toString(16);

            while (md5Password.length() < 32) {
                md5Password = "0" + md5Password;
            }

            return md5Password;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    TextView txtSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignup = findViewById(R.id.txtSignup);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                db.collection("accounts")
                        .whereEqualTo("Username", username)
                        .whereEqualTo("Password", encryptPassword(password))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot snapshot = task.getResult();
                                    if (snapshot.isEmpty()) {
                                        Toast.makeText(login.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(login.this, "Register successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login.this, homepage.class);
                                        List<DocumentSnapshot> list  = snapshot.getDocuments();
                                        String fullname = list.get(0).getString("Full name");
                                        intent.putExtra("Fullname", fullname);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(login.this, "Error querying database.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}