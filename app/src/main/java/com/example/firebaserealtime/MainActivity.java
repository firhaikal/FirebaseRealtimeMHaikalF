   package com.example.firebaserealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

   public class MainActivity extends AppCompatActivity {
    EditText edtTxtName;
    Button btnAdd;
    Spinner spinnerGenre;

    DatabaseReference databaseArtist;

    ListView listVArtists;

    List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseArtist = FirebaseDatabase.getInstance().getReference("artists");

        edtTxtName = (EditText) findViewById(R.id.edtTxtName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenre);

        listVArtists = (ListView)findViewById(R.id.ListArtist);

        artistList = new ArrayList<>();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });
    }

       @Override
       protected void onStart() {
           super.onStart();

           databaseArtist.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   artistList.clear();

                   for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                       Artist artist = artistSnapshot.getValue(Artist.class);

                       artistList.add(artist);
                   }

                   ArtistList adapter = new ArtistList(MainActivity.this, artistList);
                   listVArtists.setAdapter(adapter);

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }

       private void addArtist(){
        String name = edtTxtName.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)){

            String id = databaseArtist.push().getKey();

            Artist artist = new Artist(id, name, genre);

            databaseArtist.child(id).setValue(artist);

            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "You should enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
