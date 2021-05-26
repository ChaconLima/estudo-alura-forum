package br.com.alura.forum.forum.Config.validacao;

public class ErroDeFormulario {
    private String erro;
    private String mensagem;
    
    public ErroDeFormulario(String erro, String mensagem) {
        this.setErro(erro);
        this.setMensagem(mensagem);
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


}
