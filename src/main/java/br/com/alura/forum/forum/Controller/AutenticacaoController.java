package br.com.alura.forum.forum.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.forum.Config.secury.TokenService;
import br.com.alura.forum.forum.Controller.Dto.TokenDto;
import br.com.alura.forum.forum.Controller.Form.LoginForm;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<?> autenticador(@RequestBody @Valid LoginForm loginForm) {

        UsernamePasswordAuthenticationToken dadosLogin = loginForm.converter();

        try {

            Authentication authentication = this.authenticationManager.authenticate(dadosLogin);
            String token = this.tokenService.gerarToken(authentication);

            return ResponseEntity.ok(new TokenDto(token,"Bearer"));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }
    

}
