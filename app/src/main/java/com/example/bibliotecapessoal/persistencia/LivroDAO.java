package com.example.bibliotecapessoal.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bibliotecapessoal.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroDAO implements ILivroDAO {

    private final SQLiteDatabase escreve;
    private final SQLiteDatabase le;

    public LivroDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Livro livro) {
        try {
            // insert devolve -1 quando falha
            return escreve.insert(DbHelper.TABELA_LIVROS, null, montarValores(livro)) != -1;
        } catch (Exception e) {
            Log.e("LivroDAO", "Erro ao salvar livro", e);
            return false;
        }
    }

    @Override
    public boolean atualizar(Livro livro) {
        // Sem id não há registro para atualizar (evita NullPointerException)
        if (livro.getId() == null) return false;
        try {
            String[] args = {livro.getId().toString()};
            return escreve.update(DbHelper.TABELA_LIVROS, montarValores(livro),
                    DbHelper.COLUNA_ID + " = ?", args) > 0;
        } catch (Exception e) {
            Log.e("LivroDAO", "Erro ao atualizar livro", e);
            return false;
        }
    }

    @Override
    public boolean excluir(Livro livro) {
        if (livro.getId() == null) return false;
        try {
            String[] args = {livro.getId().toString()};
            return escreve.delete(DbHelper.TABELA_LIVROS,
                    DbHelper.COLUNA_ID + " = ?", args) > 0;
        } catch (Exception e) {
            Log.e("LivroDAO", "Erro ao excluir livro", e);
            return false;
        }
    }

    @Override
    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        // COLLATE NOCASE para "a" e "A" ficarem juntos na ordem alfabética
        String sql = "SELECT * FROM " + DbHelper.TABELA_LIVROS
                + " ORDER BY " + DbHelper.COLUNA_TITULO + " COLLATE NOCASE ASC";
        Cursor cursor = null;
        try {
            cursor = le.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Livro livro = new Livro();
                livro.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COLUNA_ID)));
                livro.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUNA_TITULO)));
                livro.setAutor(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUNA_AUTOR)));
                livro.setAno(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUNA_ANO)));
                // Leitura do boolean a partir do INTEGER
                livro.setLido(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUNA_LIDO)) == 1);
                livros.add(livro);
            }
        } catch (Exception e) {
            Log.e("LivroDAO", "Erro ao listar livros", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return livros;
    }

    @Override
    public int contar(Boolean lido) {
        String sql = "SELECT COUNT(*) FROM " + DbHelper.TABELA_LIVROS;
        String[] args = null;
        if (lido != null) {                       // null = total geral
            sql += " WHERE " + DbHelper.COLUNA_LIDO + " = ?";
            args = new String[]{lido ? "1" : "0"};
        }
        Cursor cursor = null;
        try {
            cursor = le.rawQuery(sql, args);
            return cursor.moveToFirst() ? cursor.getInt(0) : 0;
        } catch (Exception e) {
            Log.e("LivroDAO", "Erro ao contar livros", e);
            return 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    // Campos comuns a salvar e atualizar; o id fica de fora (gerado pelo banco)
    private ContentValues montarValores(Livro livro) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUNA_TITULO, livro.getTitulo());
        cv.put(DbHelper.COLUNA_AUTOR, livro.getAutor());
        cv.put(DbHelper.COLUNA_ANO, livro.getAno());
        // Gravação do boolean como INTEGER
        cv.put(DbHelper.COLUNA_LIDO, livro.isLido() ? 1 : 0);
        return cv;
    }
}
