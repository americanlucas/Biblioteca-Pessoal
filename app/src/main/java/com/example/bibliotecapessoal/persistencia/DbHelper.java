package com.example.bibliotecapessoal.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    // Nomes centralizados para nenhuma SQL usar texto solto
    public static final String NOME_DB = "biblioteca.db";
    public static final int VERSAO = 1;
    public static final String TABELA_LIVROS = "livros";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_TITULO = "titulo";
    public static final String COLUNA_AUTOR = "autor";
    public static final String COLUNA_ANO = "ano";
    public static final String COLUNA_LIDO = "lido";
    public static final String COLUNA_NOTA = "nota";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabela da v1: a coluna nota só chega pela migração
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_LIVROS + " ("
                + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_TITULO + " TEXT NOT NULL, "
                + COLUNA_AUTOR + " TEXT NOT NULL, "
                + COLUNA_ANO + " INTEGER, "
                + COLUNA_LIDO + " INTEGER DEFAULT 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migração incremental: preserva os dados existentes
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABELA_LIVROS
                    + " ADD COLUMN " + COLUNA_NOTA + " INTEGER");
        }
    }
}
