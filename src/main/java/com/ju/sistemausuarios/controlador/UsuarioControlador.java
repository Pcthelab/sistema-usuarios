package com.ju.sistemausuarios.controlador;

import com.ju.sistemausuarios.modelo.Usuario;
import com.ju.sistemausuarios.servico.UsuarioServico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioControlador {

    private final UsuarioServico servico;

    public UsuarioControlador(UsuarioServico servico) {
        this.servico = servico;
    }

    @GetMapping
    public List<Usuario> listar() {
        return servico.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return servico.buscarPorId(id);
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody Map<String, String> body) {
        return servico.cadastrar(body.get("nome"), body.get("email"));
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return servico.atualizar(id, body.get("nome"), body.get("email"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarRegra(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
