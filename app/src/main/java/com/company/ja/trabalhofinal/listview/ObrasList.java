package com.company.ja.trabalhofinal.listview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.company.ja.trabalhofinal.R;
import com.company.ja.trabalhofinal.model.Obra;

import java.util.List;

public class ObrasList  extends ArrayAdapter<String> {
    private final Activity context;
    private final List<Obra> obras;

    public ObrasList(Activity context, List<Obra> obras) {
        super(context, R.layout.list_obras);
        this.context = context;
        this.obras = obras;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_obras, null, true);
        TextView pos = (TextView) rowView.findViewById(R.id.posicao);
        TextView txtDesc = (TextView) rowView.findViewById(R.id.descricao);
        Obra obra = obras.get(position);
        txtDesc.setText(obra.descricao);
        int p = position + 1;
        if(position < 9) {
            pos.setText("0" + p);
        }else {
            pos.setText("" + p);
        }
        return rowView;
    }

    @Override
    public int getCount() {
        if(obras == null)
            return  0;
        return obras.size();
    }
}
