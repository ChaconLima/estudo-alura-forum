package br.com.alura.forum.forum.Controller.Form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.forum.modelo.Topico;
import br.com.alura.forum.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm {
    
   
    @NotNull @NotEmpty @Length(min= 5)
    private String titulo;
  
    @NotNull @NotEmpty
    private String mensagem;


    public AtualizacaoTopicoForm(String titulo, String mensagem) {
        this.setTitulo(titulo);
        this.setMensagem(mensagem);
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


    public Topico atualizar(Long id_topico, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getOne(id_topico);
        topico.setMensagem(this.mensagem);
        topico.setTitulo(this.titulo);

        return topico;
    }

}
