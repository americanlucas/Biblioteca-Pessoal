package com.example.bibliotecapessoal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bibliotecapessoal.R;
import com.example.bibliotecapessoal.model.Livro;

import java.util.List;

public class AdapterLivro extends RecyclerView.Adapter<AdapterLivro.MyViewHolder> {

    private List<Livro> lista;
    private final Context context;

    public AdapterLivro(List<Livro> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_livro, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Livro livro = lista.get(position);
        holder.txtTitulo.setText(livro.getTitulo());
        // Ano vai pelo placeholder %2$d: setText(int) buscaria um recurso e daria crash
        holder.txtAutorAno.setText(context.getString(R.string.item_autor_ano, livro.getAutor(), livro.getAno()));
        holder.txtStatus.setText(livro.isLido() ? R.string.tag_lido : R.string.tag_nao_lido);
        holder.txtStatus.setTextColor(ContextCompat.getColor(context,
                livro.isLido() ? R.color.cor_lido : R.color.cor_nao_lido));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // Troca os dados sem recriar o adapter (RT04)
    public void atualizarLista(List<Livro> novaLista) {
        this.lista = novaLista;
        notifyDataSetChanged();
    }

    public Livro getLivro(int position) {
        return lista.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        TextView txtAutorAno;
        TextView txtStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtAutorAno = itemView.findViewById(R.id.txtAutorAno);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}
