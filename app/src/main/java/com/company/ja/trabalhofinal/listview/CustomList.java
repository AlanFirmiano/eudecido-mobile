package com.company.ja.trabalhofinal.listview;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.ja.trabalhofinal.R;
import com.company.ja.trabalhofinal.model.Comentario;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final List<Comentario> comentarios;

    public CustomList(Activity context, List<Comentario> comentarios) {
        super(context, R.layout.list_single);
        this.context = context;
        this.comentarios = comentarios;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txt = (TextView) rowView.findViewById(R.id.txt);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtnome);
        CircleImageView imageView = (CircleImageView) rowView.findViewById(R.id.img);
        Comentario comentario = comentarios.get(position);
        txt.setText(comentario.comentario);
        if(comentario.usuario != null && !comentario.usuario.nome.equals("")) {
            txtTitle.setText(comentario.usuario.nome);
            Picasso.get().load(comentario.usuario.fotoUrl).into(imageView);
        }
        return rowView;
    }

    @Override
    public int getCount() {
        if(comentarios == null)
            return  0;
        return comentarios.size();
    }
}
