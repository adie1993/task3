package com.adie.login.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.adie.login.activity.SignIn;

import java.util.HashMap;


@SuppressLint("CommitPrefEdits")
public class SessionManager {
	//deklarasi variabel yang dibutuhkan
	SharedPreferences pref;
	Editor editor;
	Context _context;
	int PRIVATE_MODE = 0;
	private static final String PREF_NAME = "Sesi";
	private static final String IS_LOGIN = "Is Logged In";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ID = "id";
	//melakukan inisialisasi sharedpreferences
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	//create session
	public void createSession(String email){
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_EMAIL, email);
		editor.commit();
	}

	//fungsi untuk cek login di session
	public void checkLogin(){
		//jika user belum melakukan login maka akan menuju ke halaman sign in
		if(!this.isLoggedIn()){
			Intent i = new Intent(_context, SignIn.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);
		}
		
	}

	//fungsi untuk melakukan pemanggilan data pada session
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(KEY_ID, pref.getString(KEY_ID, null));
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		return user;
	}

	//logout
	public void logoutUser(){
		editor.clear();
		editor.commit();
		Intent i = new Intent(_context, SignIn.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_context.startActivity(i);
	}

	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
