package android.example.peacockchat;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignActivity2 extends AppCompatActivity {

    private static final String TAG = "SignActivity2";

    private FirebaseAuth auth;

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText namedEditText;
    private TextView toggleLogin;
    private Button loginSingUpButton;

    private boolean loginModeActive;

    private FirebaseDatabase database ;
    private DatabaseReference usersDatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign2);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDatabaseReference = database.getReference().child("users");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        namedEditText = findViewById(R.id.namedEditText);
        toggleLogin = findViewById(R.id.toggleLogin);
        loginSingUpButton = findViewById(R.id.loginSingUpButton);


        loginSingUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSingUpUser(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());
            }
        });

        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(SignActivity2.this, UserListActivity.class));
        }
    }

    private void loginSingUpUser(String email,String password) {
        if(loginModeActive){
            if (!passwordEditText.getText().toString().trim().equals(repeatPasswordEditText.
                    getText().toString().trim())){
                Toast.makeText(this,"???????????????????????? ????????????",Toast.LENGTH_SHORT).show();

            }
            else if (emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this,"?????????????? ?????? ??????????",Toast.LENGTH_LONG).show();

            } else {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    Intent intent = new Intent(SignActivity2.this,
                                            UserListActivity.class);
                                    intent.putExtra("userName",namedEditText.getText().toString().trim());
                                    startActivity(intent);
//                                    createUser(user);

//                            updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignActivity2.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                                    // ...
                                }

                                // ...
                            }
                        });
            }

        }else {
            if (!passwordEditText.getText().toString().trim().equals(repeatPasswordEditText.
                    getText().toString().trim())){
                Toast.makeText(this,"???????????????????????? ????????????",Toast.LENGTH_SHORT).show();

            }
            else if (emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this,"?????????????? ?????? ??????????",Toast.LENGTH_LONG).show();

            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    createUser(user);
                                    Intent intent = new Intent(SignActivity2.this,
                                            UserListActivity.class);
                                    intent.putExtra("userName",namedEditText.getText().toString().trim());
                                    startActivity(intent);
//                           updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignActivity2.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                                }

                                // ...
                            }
                        });
            }

        }



    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(namedEditText.getText().toString().trim());

        usersDatabaseReference.push().setValue(user);
    }

    public void toggleLoginMode(View view) {
        if(loginModeActive){
            loginModeActive= false;
            loginSingUpButton.setText("??????????????????????????????????");
            toggleLogin.setText("????????????????????????????");
            repeatPasswordEditText.setVisibility(View.VISIBLE);
        } else {
            loginModeActive= true;
            loginSingUpButton.setText("??????????");
            toggleLogin.setText("?????? ??????????????????????????????????");
            repeatPasswordEditText.setVisibility(View.GONE);
        }
    }
}