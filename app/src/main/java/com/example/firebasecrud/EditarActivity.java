package com.example.firebasecrud;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasecrud.model.Recado;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditarActivity extends AppCompatActivity {

    @BindView(R.id.remetenteEditText)
    EditText mRemetenteEditText;

    @BindView(R.id.destinatarioEditText)
    EditText mDestinatarioEditText;

    @BindView(R.id.recadoEditText)
    EditText mRecadoEditText;

    @BindView(R.id.salvarButton)
    Button mSalvarButton;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        ButterKnife.bind(this);
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("character").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Recado charater = dataSnapshot.getValue(Recado.class);

                if (charater.getRemetente() != null) {
                    mRemetenteEditText.setText(charater.getRemetente());
                }

                if (charater.getDestinatario()!= null) {
                    mDestinatarioEditText.setText(charater.getDestinatario());
                }

                if (charater.getRecado() != null) {
                    mRecadoEditText.setText(charater.getRecado());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditarActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mSalvarButton.setOnClickListener(v -> {
            mDatabaseReference.child("remetente").setValue(mRemetenteEditText.getText().toString());
            mDatabaseReference.child("destinatario").setValue(mDestinatarioEditText.getText().toString());
            mDatabaseReference.child("recado").setValue(mRecadoEditText.getText().toString());
            finish();
        });
    }
}