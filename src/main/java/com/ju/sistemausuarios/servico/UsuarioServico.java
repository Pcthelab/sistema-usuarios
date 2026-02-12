package com.ju.sistemausuarios.servico;

import com.ju.sistemausuarios.modelo.Usuario;
import com.ju.sistemausuarios.repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServico {

    private final UsuarioRepositorio repositorio;

    public UsuarioServico(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario cadastrar(String nome, String email) {
        validarNome(nome);
        validarEmail(email);
        return repositorio.salvar(nome.trim(), email.trim().toLowerCase());
    }

    public List<Usuario> listar() {
        return repositorio.listar();
    }

    public Usuario buscarPorId(Long id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado (id=" + id + ")."));
    }

    public Usuario atualizar(Long id, String nome, String email) {
        validarNome(nome);
        validarEmail(email);

        Usuario atualizado = repositorio.atualizar(id, nome.trim(), email.trim().toLowerCase());
        if (atualizado == null) {
            throw new IllegalArgumentException("Usuário não encontrado (id=" + id + ").");
        }
        return atualizado;
    }

    public void remover(Long id) {
        boolean ok = repositorio.remover(id);
        if (!ok) {
            throw new IllegalArgumentException("Usuário não encontrado (id=" + id + ").");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (nome.trim().length() < 2) {
            throw new IllegalArgumentException("Nome muito curto.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }
        String e = email.trim();
        if (!e.contains("@") || !e.contains(".") || e.startsWith("@") || e.endsWith("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }
}
