package br.com.alura.forum.forum.Controller.Form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.forum.modelo.Curso;
import br.com.alura.forum.forum.modelo.Topico;
import br.com.alura.forum.forum.repository.CursoRepository;

public class TopicoFrom {

    @NotNull @NotEmpty @Length(min= 5)
    private String titulo;

    @NotNull @NotEmpty
    private String mensagem;

    @NotNull @NotEmpty @Length(min= 10)
    private String nomeCurso;

   
    public TopicoFrom(String titulo, String mensagem, String nomeCurso) {
        this.setTitulo(titulo);
        this.setMensagem(mensagem);
        this.setNomeCurso(nomeCurso);
    }
    public String getNomeCurso() {
        return nomeCurso;
    }
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Topico converterTipo(CursoRepository cursoRepository) {
        
        Curso curso = cursoRepository.findByNome(this.nomeCurso);
        Topico topico = new Topico(this.titulo, this.mensagem, curso);

        return topico;
    }




    
}
