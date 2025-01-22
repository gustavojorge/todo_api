package br.ufba.tomorrow.todoProject.api.controllers;

import br.ufba.tomorrow.todoProject.api.dto.TodoCreateDTO;
import br.ufba.tomorrow.todoProject.api.dto.TodoDTO;
import br.ufba.tomorrow.todoProject.api.dto.TodoUpdateDTO;
import br.ufba.tomorrow.todoProject.domain.entities.Estado;
import br.ufba.tomorrow.todoProject.domain.entities.Todo;
import br.ufba.tomorrow.todoProject.domain.services.TodoService;
import br.ufba.tomorrow.todoProject.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService service;
    public TodoController(TodoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TodoDTO> criar(@RequestBody TodoCreateDTO todo){
        return new ResponseEntity<TodoDTO>(service.criar(todo), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TodoDTO>> listarPorUsuario(@PathVariable long userId){
        return new ResponseEntity<List<TodoDTO>>(service.listarTodosDeUmUsuario(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/{estado}")
    public ResponseEntity<List<TodoDTO>> listarPorUsuarioPorEstado(@PathVariable long userId, @PathVariable Estado estado){
        return new ResponseEntity<List<TodoDTO>>(service.listarTodosDeUmUsuarioComEstado(userId, estado), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TodoDTO> alterar(@RequestBody TodoUpdateDTO todo) throws Exception{
        return new ResponseEntity<TodoDTO>(service.alterar(todo), HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> removerTodo(@PathVariable Long todoId) {
        service.remover(todoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoDTO> atualizarParcial(@PathVariable Long todoId, @RequestBody TodoUpdateDTO atualizacao) throws Exception {
        TodoDTO todoAtualizado = service.atualizarParcial(todoId, atualizacao);
        return new ResponseEntity<>(todoAtualizado, HttpStatus.OK);
    }
}
