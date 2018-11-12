package com.company.ja.trabalhofinal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.company.ja.trabalhofinal.model.Obra;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObraViewModel extends ViewModel {
    private DatabaseReference mDatabase;
    private MutableLiveData<List<Obra>> obras;

    public LiveData<List<Obra>> getObras() {
        if (obras == null) {
            obras = new MutableLiveData<List<Obra>>();
            loadObras();
        }
        return obras;
    }

    private void loadObras() {
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("obras");
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectObras((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void collectObras(Map<String,Object> obras) {
        List<Obra> newObras = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : obras.entrySet()){

            //Get user map
            Map singleObra = (Map) entry.getValue();
            //Get phone field and append to list
            Obra ob = new Obra();
            ob.key = (String) singleObra.get("key");
            ob.avaliacao = Double.parseDouble((String) singleObra.get("avaliacao"));
            ob.dataInicio = (String) singleObra.get("dataInicio");
            ob.dataFim = (String) singleObra.get("dataFim");
            ob.dataOrdem = (String) singleObra.get("dataOrdem");
            ob.latitude = (String) singleObra.get("latitude");
            ob.longitude = (String) singleObra.get("longitude");
            ob.percentual = Double.parseDouble((String) singleObra.get("percentual"));
            ob.situacao = (String) singleObra.get("situacao");
            ob.valor = Double.parseDouble((String) singleObra.get("valor"));

            newObras.add(ob);
        }

        this.obras.setValue(newObras);
    }
}
