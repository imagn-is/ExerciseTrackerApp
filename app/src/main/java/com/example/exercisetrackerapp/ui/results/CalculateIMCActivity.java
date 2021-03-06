package com.example.exercisetrackerapp.ui.results;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CalculateIMCActivity extends AppCompatActivity {

    private TextView textViewIMC;
    float imc = 0,peso=0, altura=0;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String email="",uid,id,userID,emailAux;

    ///CON ESTA REFERENCIA
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_imc);

        //Formula BMI = weight(kg)/[height(m)]^2
        inicializarFirebase();

        ////AQUI SE AGARRA EL EMAIL DEL USUARIO QUE INGRESO SESION
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }
        textViewIMC = (TextView) findViewById(R.id.textViewIMC);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    emailAux = areaSnapshot.child("correo").getValue().toString();
                    if(emailAux.equals(email)){
                        peso = Float.parseFloat(areaSnapshot.child("peso").getValue().toString());
                        altura = Float.parseFloat(areaSnapshot.child("altura").getValue().toString());
                        textViewIMC.setText(String.valueOf(peso/(altura*altura)));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
