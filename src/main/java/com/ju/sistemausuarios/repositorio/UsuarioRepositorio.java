package com.ju.sistemausuarios.repositorio;

import com.ju.sistemausuarios.modelo.Usuario;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import com.ju.sistemausuarios.modelo.Usuario;

@Repository
public class UsuarioRepositorio {
    private final Map<Long, Usuario> banco = new ConcurrentHashMap<>();
    private final AtomicLong sequenciaId = new AtomicLong(1);


    public Usuario salvar(String nome, String email) {
        Long id = sequenciaId.getAndIncrement();
        Usuario usuario = new Usuario(id, nome, email);
        banco.put(id, usuario);
        return usuario;
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>(banco.values());
        lista.sort(Comparator.comparingLong(Usuario::getId));
        return lista;
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    public Usuario atualizar(Long id, String nome, String email) {
        Usuario existente = banco.get(id);
        if (existente == null) return null;
        existente.setNome(nome);
        existente.setEmail(email);
        return existente;
    }

    public boolean remover(Long id) {
        return banco.remove(id) != null;
    }
}
