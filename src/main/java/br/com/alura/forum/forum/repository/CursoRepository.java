package br.com.alura.forum.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.forum.modelo.Curso;

public interface CursoRepository  extends JpaRepository<Curso, Long>{

    Curso findByNome(String nomeCurso);
    
}
