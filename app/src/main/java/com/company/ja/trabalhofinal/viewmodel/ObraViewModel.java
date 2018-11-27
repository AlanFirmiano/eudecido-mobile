package com.company.ja.trabalhofinal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.company.ja.trabalhofinal.model.Obra;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mapquest.android.commoncore.log.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObraViewModel extends ViewModel {
    List<Obra> newObras = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference dbRef;
    private MutableLiveData<List<Obra>> obras;

    public LiveData<List<Obra>> getObras() {
        L.d("ObraViewModel", "get obras");
        if (obras == null) {
            obras = new MutableLiveData<List<Obra>>();
            loadObras();
        }
        return obras;
    }

    private void loadObras() {
        L.d("ObraViewModel", "load obras");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance().getReference("obras");

        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Obra ob = dataSnapshot.getValue(Obra.class);
                newObras.add(ob);
                obras.setValue(newObras);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                L.d("ObraViewModel", "load obras alteracoes "+dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addChildEventListener(childListener);

    }
}
