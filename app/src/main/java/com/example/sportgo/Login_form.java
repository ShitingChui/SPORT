package com.example.sportgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_form extends AppCompatActivity {
    public EditText Mail;
    public EditText Passward;
    public Button Login;
    public int counter = 5;
    public String MailString;
    public String PasswrdString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login Form");
    }

    public void btn_signupForm(View view) {

        Intent i = new Intent(this,Signup.class);
        startActivity(i);




    }

    public void btn_sportRecorder(View view) {

        Mail = (EditText)findViewById(R.id.editText_mail);
        Passward = (EditText)findViewById(R.id.editText_password);

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);


        String MailString = Mail.getText().toString();
//        String PasswardString = Passward.getText().toString();
        if( MailString == "tinapq212pq1212@gmail.com" ){



        }else{
            counter --;

            if(counter == 0){
                Login.setEnabled(false);
            }
        }


    }

//    private void validate (String mail, String userPassward ){
//        if((mail == "tinapq212pq1212@gmail.com") && (userPassward == "1234"))
//            Intent intent = new Intent (MainActivity.this, SecondActivity.class);
//    }


}
