package com.example.hari.mirrormirroronthewall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirststartactivityActivity extends AppCompatActivity {
SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.firststartactivity);
        pref = getSharedPreferences("com.example.hari.mirrormirrorontthewall", MODE_PRIVATE);
    }
public void okayokay(View view){
    EditText text = (EditText) findViewById(R.id.kekspek);
    String code = text.getText().toString();
    Log.d("code is",code);
    if(code.equals(null) || code.equals("") || code.length() !=7){
        Toast.makeText(getApplicationContext(),"Invalid Code",Toast.LENGTH_SHORT).show();
        Log.d("got here","Got here");
    }
    else{
        pref.edit().putBoolean("firstuse",false).commit();

        pref.edit().putString("code",code).commit();
        pref.edit().putString("code",code).apply();
        //Start Main Activity

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        //set the firstrun to false
        //save the code
    }
}

}
