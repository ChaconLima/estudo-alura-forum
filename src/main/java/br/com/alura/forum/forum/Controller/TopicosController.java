package br.com.alura.forum.forum.Controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.forum.Controller.Dto.DetalhesTopicoDto;
import br.com.alura.forum.forum.Controller.Dto.TopicoDto;
import br.com.alura.forum.forum.Controller.Form.AtualizacaoTopicoForm;
import br.com.alura.forum.forum.Controller.Form.TopicoFrom;
import br.com.alura.forum.forum.modelo.Topico;
import br.com.alura.forum.forum.repository.CursoRepository;
import br.com.alura.forum.forum.repository.TopicoRepository;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;
    
    //================================
    // recurso de busca
    //================================
    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> lista(
        @RequestParam(required = false) String nomeCurso, 
        // @RequestParam(required = true) int pagina, 
        // @RequestParam(required = true) int quantidade,
        // @RequestParam(required = true) String ordenacao
        @PageableDefault(sort = "id", direction = Direction.DESC, page=0, size=5) Pageable paginacao
    ){
        // Pageable paginacao = PageRequest.of( pagina, quantidade,Direction.ASC, ordenacao);
        
        if(nomeCurso == null){
            Page<Topico> topicos = this.topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        } else {
            Page <Topico> topicos = this.topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }
    }
    //================================
    // recurso de cadastro
    //================================    
    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoFrom form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converterTipo(this.cursoRepository);
        this.topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }
    //================================
    // recurso de busca detalhada
    //================================
    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable("id") Long id_topico){

        Optional<Topico> optional = topicoRepository.findById(id_topico);
        if( optional.isPresent()){
            Topico topico = optional.get();
            return ResponseEntity.ok(new DetalhesTopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }
    //================================
    // recurso de de atualização
    //================================
    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable("id") Long id_topico, @RequestBody @Valid AtualizacaoTopicoForm atualizacaoTopicoForm){
        
        Optional<Topico> optional = topicoRepository.findById(id_topico);

        if( optional.isPresent()){
            Topico topico = atualizacaoTopicoForm.atualizar(id_topico, this.topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }

        return ResponseEntity.notFound().build();
    }
    //================================
    // recurso de deletar
    //================================
    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable("id") Long id_topico){

        Optional<Topico> optional = topicoRepository.findById(id_topico);
        if( optional.isPresent()){

            this.topicoRepository.deleteById(id_topico);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
