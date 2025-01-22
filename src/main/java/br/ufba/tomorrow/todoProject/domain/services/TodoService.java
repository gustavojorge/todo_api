package br.ufba.tomorrow.todoProject.domain.services;

import br.ufba.tomorrow.todoProject.api.dto.TodoCreateDTO;
import br.ufba.tomorrow.todoProject.api.dto.TodoDTO;
import br.ufba.tomorrow.todoProject.api.dto.TodoUpdateDTO;
import br.ufba.tomorrow.todoProject.domain.entities.Estado;
import br.ufba.tomorrow.todoProject.domain.entities.Todo;
import br.ufba.tomorrow.todoProject.domain.entities.Usuario;
import br.ufba.tomorrow.todoProject.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    final TodoRepository repository;
    public TodoService(final TodoRepository repository){
        this.repository = repository;
    }

    public TodoDTO criar (TodoCreateDTO todo){
        return new TodoDTO(repository.save(new Todo(todo)));
    }

    public List<TodoDTO> listarTodosDeUmUsuario(long userId){
        return repository.findByUsuario(new Usuario(userId))
                .stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> listarTodosDeUmUsuarioComEstado(long userId, Estado estado){
        return repository.findByUsuarioAndEstado(new Usuario(userId), estado)
                .stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());
    }

    public TodoDTO alterar(TodoUpdateDTO dto) throws Exception{
        Todo existe = repository.findById(dto.getId());
        if(existe != null){
            Todo inserir = new Todo(dto);
            inserir.setUsuario(existe.getUsuario()); //add because TodoUpdateDTO dont have the object Usuario, but the variable 'exist' does.
            return new TodoDTO(repository.save(inserir));
        }
        else throw new Exception("Objeto não encontrado com id:" + dto.getId());
    }

    public void remover(Long todoId) {
        if (!repository.existsById(todoId)) {
            throw new IllegalArgumentException("Todo não encontrado com ID: " + todoId);
        }
        repository.deleteById(todoId);
    }

    public TodoDTO atualizarParcial(Long todoId, TodoUpdateDTO atualizacao) throws Exception {
        Todo todo = repository.findById(todoId)
                .orElseThrow(() -> new Exception("Todo não encontrado com ID: " + todoId));

        if (atualizacao.getItem() != null) {
            todo.setItem(atualizacao.getItem());
        }
        if (atualizacao.getPrazo() != null) {
            todo.setPrazo(atualizacao.getPrazo());
        }
        if (atualizacao.getEstado() != null) {
            todo.setEstado(atualizacao.getEstado());
        }
        if (atualizacao.getConclusao() != null) {
            todo.setConclusao(atualizacao.getConclusao());
        }

        Todo todoAtualizado = repository.save(todo);

        return new TodoDTO(todoAtualizado);
    }

}
