package br.com.alura.forum.forum.Controller.Dto;

public class TokenDto {
    


    private String token;
    private String tipo;


    public TokenDto(String token, String tipo) {
        this.setTipo(tipo); this.setToken(token);
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
