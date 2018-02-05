package com.example.christine.uvpassenger.Activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

import com.example.christine.uvpassenger.Model.LoginModel;
import com.example.christine.uvpassenger.R;
import com.example.christine.uvpassenger.Service.APIServiceGenerator;
import com.example.christine.uvpassenger.Service.APIServices;
import com.example.christine.uvpassenger.databinding.ActivityLoginBinding;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.christine.uvpassenger.AppController.hideKeyboard;

/**
 * Created by Christine on 2/5/2018.
 */

public class LoginActivity extends Activity {

    private ActivityLoginBinding binding;
    private boolean mHasError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.etPassword.setTypeface(Typeface.DEFAULT);
        binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());

        binding.btnSignup.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), SignupActivity.class);
            startActivity(intent);
            finish();

        });

        binding.btnLogin.setOnClickListener(v -> {

            String username = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();




            getLogin(username, password);




        });

    }



    public void getLogin(String username, String password)
    {
        hideKeyboard(LoginActivity.this);
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setAlpha(0.4f);

        APIServiceGenerator.TOKEN = APIServiceGenerator.AUTHENTICATE_KEY;
        APIServices service= APIServiceGenerator.createService(APIServices.class);

        Call<LoginModel> loginUser = service.login(username, password);
        loginUser.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                try {
                    if ("true".equals(response.body().getStatus())) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                    if ("false".equals(response.body().getStatus()))
                        Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();


                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Looks like our server is down. Try again after 5 minutes.", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Logger.e(t.getMessage());
                binding.btnLogin.setEnabled(true);
                binding.btnLogin.setAlpha(1f);
                Toast.makeText(getApplicationContext(), "Cannot connect to Server.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
