package com.company.ja.trabalhofinal.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.company.ja.trabalhofinal.R;
import com.company.ja.trabalhofinal.model.Avaliacao;
import com.company.ja.trabalhofinal.model.Comentario;
import com.company.ja.trabalhofinal.model.Obra;
import com.company.ja.trabalhofinal.viewmodel.UsuarioViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.math3.analysis.function.Log;

public class ComentariosIntentService extends IntentService {

    private DatabaseReference mDatabase;
    private DatabaseReference dbRef;

    public ComentariosIntentService() {
        super("ComentariosIntentService");
        if(mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance().getReference("obras");

        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Obra ob = dataSnapshot.getValue(Obra.class);
                if(ob != null){
                    if(UsuarioViewModel.logado != null){
                        String userId = UsuarioViewModel.logado.id;
                        if(userId != null && !userId.equals("")){
                            boolean novidade = false;
                            if(ob.avaliacoes!= null){
                                for(Avaliacao av : ob.avaliacoes){
                                    if(av.usuario != null && av.usuario.id.equals(userId)){
                                        novidade = true;
                                    }
                                }
                            }

                            if(ob.comentarios != null){
                                int i = ob.comentarios.size();
                                if(i > 0 && ob.comentarios.get(i-1).usuario != null
                                        && (ob.comentarios.get(i-1).usuario.id.equals(userId))){
                                    novidade = false;
                                }
                            }

                            if (novidade){
                                NotificationManager notificationManager =
                                        (NotificationManager) getApplication().getSystemService(Service.NOTIFICATION_SERVICE);
                                Notification notification = new NotificationCompat.Builder(getApplication(),"notification_id")
                                        .setSmallIcon(R.drawable.ic_icon)
                                        .setContentTitle("OBRAS PUBLICAS")
                                        .setContentText("A obra "+ob.descricao+" que você avaliou teve alterações")
                                        .setDefaults(NotificationCompat.DEFAULT_SOUND)
                                        .build();
                                notificationManager.notify(0, notification);
                            }
                        }
                    }
                }
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

    @Override
    protected void onHandleIntent(Intent intent) {
        android.util.Log.d("COMENTARIOS", "START");
    }
}
