package com.example.collegeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView title, lblclgname;
    private Button register, next;
    private FirebaseAuth auth;
    private FirebaseFirestore fstore;
    private EditText name, email, clgid, dob, password, mno;
    private Spinner cgender;

    DatePickerDialog picker;

    // private FirebaseFirestore fstore;
    EditText eText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        auth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        //lable
        title = findViewById(R.id.tv_title);
       cgender = findViewById(R.id.gender);
        //button
        register = findViewById(R.id.register);
        next = findViewById(R.id.bt_next);

        //edit text
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        clgid = findViewById(R.id.et_clgid);
        mno = findViewById(R.id.mno);
        //clgname = findViewById(R.id.com_clgname);
        password = findViewById(R.id.et_pass);

        //for only date
        eText = (EditText) findViewById(R.id.et_dob);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

        });


        //  date over

//for the email and password  authantication
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text_name=name.getText().toString();
                String text_email = email.getText().toString();
                String txt_dob= eText.getText().toString();
                String txt_gender=cgender.getSelectedItem().toString();
                String txt_mno=mno.getText().toString();

                String value=name.getText().toString()+eText.getText().toString();

                String txt_clgid=value.toString();
                String text_password = password.getText().toString();
                if(TextUtils.isEmpty(txt_mno) && txt_mno.length() == 10){
                    Toast.makeText(RegisterActivity.this, "Enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
                }




                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)) {
                    Toast.makeText(RegisterActivity.this, "Email or password is empty", Toast.LENGTH_SHORT).show();
                } else if (text_password.length() > 6) {
                    Toast.makeText(RegisterActivity.this, "Password length is must be greater than 6 ", Toast.LENGTH_SHORT).show();

                } else {
                    registeruser(text_email, text_password);
                    savedata(text_name,text_email,txt_clgid,txt_dob,txt_gender,txt_mno,text_password);
                }

            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(RegisterActivity.this,RegisterActivity1.class);
                startActivity(in);
            }
        });
    }


    // data save
    private void savedata(String text_name, String txt_gender, String text_email, String txt_clgid, String txt_dob, String txt_mno, String text_password) {
        Map<String,String> name_of_the_student=new HashMap<>();
        name_of_the_student.put("Name",text_name);
        name_of_the_student.put("Email",text_email);
        name_of_the_student.put("Unique_id",txt_clgid);
        name_of_the_student.put("Birthdate",txt_dob);
        name_of_the_student.put("Gender",txt_gender);
        name_of_the_student.put("Mobile_number",txt_mno);
        name_of_the_student.put("Password",text_password);


        fstore.collection("user").add(name_of_the_student).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterActivity.this, " data save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registeruser(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, " ", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(RegisterActivity.this, "register not succesfully", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}