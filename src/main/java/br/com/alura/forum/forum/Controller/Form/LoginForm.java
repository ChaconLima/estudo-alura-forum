package br.com.alura.forum.forum.Controller.Form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
    
    private String email;
    private String senha;

    public LoginForm(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(this.email, this.senha);
    }

}


    
