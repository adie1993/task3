package com.adie.login.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import com.adie.login.R;
import com.adie.login.utils.SessionManager;
import com.adie.login.model.Users;
import com.adie.login.model.UsersResponse;
import com.adie.login.rest.ApiClient;
import com.adie.login.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity{
	//deklarasi variabel
	private EditText inputemail, inputpassword;
	private TextInputLayout inputLayoutEmail, inputLayoutPassword;
	Button login;
	private CoordinatorLayout coordinatorLayout;
 	SessionManager session;
	ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		//memanggil atribut yang diperlukan
		pDialog = new ProgressDialog(SignIn.this);
		session = new SessionManager(getApplicationContext());
		login = (Button) findViewById(R.id.btn_login);
		coordinatorLayout = (CoordinatorLayout)findViewById(R.id.cord);
		inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
		inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
		inputemail = (EditText) findViewById(R.id.email);
		inputpassword = (EditText) findViewById(R.id.password);
		inputemail.addTextChangedListener(new MyTextWatcher(inputemail));
		inputpassword.addTextChangedListener(new MyTextWatcher(inputpassword));

		//trigger onclick button login
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//melakukan validasi inputan email & password
				if (!validateEmail()) {
					return;
				}
				if (!validatePassword()) {
					return;
				}
				if (inputemail.getText().toString().trim().length() > 0
						&& inputpassword.getText().toString().trim().length() > 0)
				{
					//mengeksekusi method sign in untuk melakukan tahap request login pada API
					SignIn();
				}

			}
		});
	}

	public void SignIn(){
		//menampilkan progress dialog
		pDialog =new ProgressDialog(SignIn.this);
		pDialog.setMessage("Signing In");
		pDialog.setCancelable(false);
		pDialog.setIndeterminate(false);
		pDialog.show();
		//melakukan inisialisasi pada class ApiInterface untuk melakukan request POST pada Endpoint API
		ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
		Call<UsersResponse> call = apiService.Login(inputemail.getText().toString(),inputpassword.getText().toString());

		call.enqueue(new Callback<UsersResponse>() {
			@Override
			public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
					//melakukan decapsulate dari response gson ke class users
					List<Users> user = response.body().getUsers();
					Log.d("SignIn", "Index User = " + user.size());
					String stremail,strpass;
					//melakukan pengulangan sebanyak jumlah dari index user
					for(int i=0; i<user.size(); i++){
						//mengconvert response list users gson ke bentuk string
						stremail = user.get(i).getEmail();
						strpass = user.get(i).getPassword();
						//melakukan autentifikasi pada inputan email & pass dengan yang ada pada Login API
						if(stremail.equals(inputemail.getText().toString())&&strpass.equals(inputpassword.getText().toString())){
							//jika berhasil maka menuju ke MainActivity
							pDialog.dismiss();
							//membuat data session baru
							session.createSession(stremail);
							//melakukan intent ke mainactivity
							Intent capcus = new Intent(SignIn.this,MainActivity.class);
							startActivity(capcus);
							finish();
						}else{
							//jika tidak maka akan muncul alert snackbar
							pDialog.dismiss();
							Snackbar snackbar = Snackbar
									.make(coordinatorLayout, "Fails Login", Snackbar.LENGTH_LONG);
							snackbar.show();
						}
					}
			}
			//fungsi dari method onfailure yaitu untuk menghandle jika terjadi kegagalan pada saat melakukan koneksi pada Endpoint API yang dituju / no connection
			@Override
			public void onFailure(Call<UsersResponse> call, Throwable t) {
				pDialog.dismiss();
				//menampilkan alert snackbar
				Snackbar snackbar = Snackbar
						.make(coordinatorLayout, "Server Error", Snackbar.LENGTH_LONG);
				snackbar.show();

			}
		});



	}


	//validasi email input
	private boolean validateEmail() {
		String email = inputemail.getText().toString().trim();
		if (email.isEmpty() || !isValidEmail(email)) {
			inputLayoutEmail.setError("Masukkan Email Anda");
			requestFocus(inputemail);
			return false;
		} else {
			inputLayoutEmail.setErrorEnabled(false);
		}

		return true;
	}


	//validasi password input
	private boolean validatePassword() {
		if (inputpassword.getText().toString().trim().isEmpty()) {
			inputLayoutPassword.setError("Masukkan Password Anda");
			requestFocus(inputpassword);
			return false;
		} else {
			inputLayoutPassword.setErrorEnabled(false);
		}

		return true;
	}

	//fungsi untuk validasi pattern email yang benar
	private static boolean isValidEmail(String email) {
		return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	//fungsi untuk request focus pada edit text yand dituju
	private void requestFocus(View view) {
		if (view.requestFocus()) {
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		}
	}

	//fungsi untuk validasi setiap inputan pada edittext
	private class MyTextWatcher implements TextWatcher {

		private View view;

		private MyTextWatcher(View view) {
			this.view = view;
		}

		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		}

		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		}

		public void afterTextChanged(Editable editable) {
			switch (view.getId()) {
				case R.id.email:
					validateEmail();
					break;
				case R.id.password:
					validatePassword();
					break;
			}
		}
	}

}
