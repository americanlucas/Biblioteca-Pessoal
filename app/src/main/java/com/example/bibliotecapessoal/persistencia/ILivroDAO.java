package com.example.bibliotecapessoal.persistencia;

import com.example.bibliotecapessoal.model.Livro;

import java.util.List;

// Contrato de acesso ao banco: as Activities só conhecem esta interface
public interface ILivroDAO {

    boolean salvar(Livro livro);

    boolean atualizar(Livro livro);

    boolean excluir(Livro livro);

    List<Livro> listarTodos();

    int contar(Boolean lido);   // null = total | true = lidos | false = não lidos
}
