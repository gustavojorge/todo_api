package br.ufba.tomorrow.todoProject.domain.entities;

import br.ufba.tomorrow.todoProject.api.dto.UsuarioCreateDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tb_usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
    @OneToMany(mappedBy = "usuario")
    private List<Todo> todoList;

    public Usuario(){}
    public Usuario (Long id){this.id = id;}

    public Usuario(UsuarioCreateDTO dto){
        this.email = dto.getEmail();
        this.senha = dto.getSenha();;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getId(), usuario.getId()) && Objects.equals(getEmail(), usuario.getEmail()) && Objects.equals(getSenha(), usuario.getSenha()) && Objects.equals(getTodoList(), usuario.getTodoList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getSenha(), getTodoList());
    }
}
