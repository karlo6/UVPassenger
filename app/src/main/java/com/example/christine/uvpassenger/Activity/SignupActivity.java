package com.example.christine.uvpassenger.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.christine.uvpassenger.R;

import com.example.christine.uvpassenger.Model.SignupModel;
import com.example.christine.uvpassenger.Service.APIServiceGenerator;
import com.example.christine.uvpassenger.Service.APIServices;
import com.example.christine.uvpassenger.databinding.ActivitySignupBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.christine.uvpassenger.AppController.hideKeyboard;

/**
 * Created by Christine on 2/5/2018.
 */

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = binding.firstName.getText().toString();
                String lastname = binding.lastName.getText().toString();
                String email = binding.emailAdd.getText().toString();
                String password = binding.password.getText().toString();

                getSignUp(firstname, lastname, email, password);


            }
        });

        binding.password.setTypeface(Typeface.DEFAULT);
        binding.password.setTransformationMethod(new PasswordTransformationMethod());

    }

    private void getSignUp(String firstname, String lastname, String email, String password) {
        hideKeyboard(SignupActivity.this);
        binding.signup.setEnabled(false);
        binding.signup.setAlpha(0.4f);

        APIServiceGenerator.TOKEN = APIServiceGenerator.AUTHENTICATE_KEY;
        APIServices service=APIServiceGenerator.createService(APIServices.class);

        Call<SignupModel> signupUser = service.register(firstname, lastname, email, password);
        signupUser.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                binding.signup.setEnabled(true);
                binding.signup.setAlpha(1f);
                SignupModel asd = response.body();
                try{
                    if ("true".equals(response.body().getStatus()))
                        Log.e("success", String.valueOf(asd));
                    Log.d("success", response.body().getMessage());
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } catch (NullPointerException e){
                    //Log.e("Error", e.getMessage());
                    // new Gson().toJson(response.body().toString());
                    Log.e("error", String.valueOf(asd));
                    Log.d("error", response.body().getMessage());
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
