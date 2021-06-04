package com.example.collegeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity1 extends AppCompatActivity {

    private EditText email, scity, sstate;
    private Spinner cname, bname, adtype, catype, sayear, scyear;
    private Button reg;
    private FirebaseFirestore fstore;
    private FirebaseAuth auth;

    public RegisterActivity1() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);


        auth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        email = findViewById(R.id.et_email);
        scity = findViewById(R.id.city);
        sstate = findViewById(R.id.state);
        cname = findViewById(R.id.college);
        bname = findViewById(R.id.branch);
        adtype = findViewById(R.id.Atype);
        catype = findViewById(R.id.Ctype);
        sayear = findViewById(R.id.Ayear);
        scyear = findViewById(R.id.Cyear);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text_email = email.getText().toString();
                String text_state = sstate.getText().toString();
                String text_city = scity.getText().toString();
                String text_college = cname.getSelectedItem().toString();
                String text_branch = bname.getSelectedItem().toString();
                String text_admission = adtype.getSelectedItem().toString();
                String text_category = catype.getSelectedItem().toString();
                String text_academic = sayear.getSelectedItem().toString();
                String text_class = scyear.getSelectedItem().toString();


            }
        });
    }

    private void savedata(String text_email, String text_state, String text_city,String text_college, String text_branch,
                          String text_admission, String text_category, String text_academic, String text_class ) {
        Map<String,String> name_of_the_student=new HashMap<>();
        name_of_the_student.put("name",text_email);
        name_of_the_student.put("email",text_state);
        name_of_the_student.put("city",text_city);
        name_of_the_student.put("college",text_college);
        name_of_the_student.put("branch",text_branch);
        name_of_the_student.put("admission",text_admission);
        name_of_the_student.put("category",text_category);
        name_of_the_student.put("academic",text_academic );
        name_of_the_student.put("class",text_class );


        fstore.collection("user").add(name_of_the_student).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterActivity1.this, " data save", Toast.LENGTH_SHORT).show();
            }
        });




    }


}