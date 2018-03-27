package com.job.tuchat.userManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.job.tuchat.MainActivity;
import com.job.tuchat.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    @BindView(R.id.register_appbar)
    Toolbar mToolbar;
    @BindView(R.id.register_input_displayname)
    TextInputLayout register_displayname;
    @BindView(R.id.register_input_password)
    TextInputLayout register_password;
    @BindView(R.id.register_input_email)
    TextInputLayout register_email;

    ProgressDialog mProgressDialog;
    //firebase
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    @OnClick(R.id.register_button)
    public void registerBtnClick(){

        final String displayname = register_displayname.getEditText().getText().toString();
        String email = register_email.getEditText().getText().toString();
        String password = register_password.getEditText().getText().toString();

        if(!TextUtils.isEmpty(displayname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("Account Registration");
            mProgressDialog.setMessage("Please wait as we set a few things ready");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> authtask) {
                            if (authtask.isSuccessful()){

                                final String user_id = mAuth.getCurrentUser().getUid();

                                //store displayname
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("name",displayname);
                                hashMap.put("imagelink","default");
                                hashMap.put("status",getResources().getString(R.string.default_status));

                                mFirestore.collection("Users").document(user_id).set(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                sendToMain();
                                                Log.d(TAG, "onSuccess: user registered success: "+user_id);
                                                mProgressDialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {

                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: database error: "+e.getMessage());
                                        mProgressDialog.dismiss();
                                    }
                                });
                            }
                            else {
                                UserAuthToastExceptions(authtask);
                                mProgressDialog.dismiss();
                            }
                        }
                    });
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void UserAuthToastExceptions(@NonNull Task<AuthResult> authtask) {
        String error = "";
        try {
            throw authtask.getException();
        } catch (FirebaseAuthWeakPasswordException e) {
            error = "Weak Password!";
        } catch (FirebaseAuthInvalidCredentialsException e) {
            error = "Invalid email";
        } catch (FirebaseAuthUserCollisionException e) {
            error = "Existing Account";
        } catch (Exception e) {
            error = "Unknown Error Occured";
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }
}
