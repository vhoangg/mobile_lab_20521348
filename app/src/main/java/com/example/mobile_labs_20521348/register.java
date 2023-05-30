package com.example.mobile_labs_20521348;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class register extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edtFullname;
    EditText edtPhone;
    EditText edtUsername;
    EditText edtPassword;
    Button btnSignup;

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFullname = findViewById(R.id.edtFullName);
        edtPhone = findViewById(R.id.edtPhone);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignup = findViewById(R.id.btnLogout);
        TextView txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsername.getText().toString().length() >= 6 && edtPassword.getText().toString().length() >= 6) {
                    Map<String, Object> account = new HashMap<>();
                    account.put("Full name: ", edtFullname.getText().toString());
                    account.put("Phone: ", edtPhone.getText().toString());
                    account.put("Username: ", edtUsername.getText().toString());
                    account.put("Password: ", encryptPassword(edtPassword.getText().toString()));

                    db.collection("accounts")
                            .add(account)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Success", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(register.this, login.class);
                                    startActivity(intent);
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Fail", "Error adding document", e);
                                }
                            });
                }
                else Toast.makeText(getApplicationContext(), "Username and password must have more tham 6 keywords", Toast.LENGTH_LONG).show();
            }
        });

    }
}