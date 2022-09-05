package br.com.farmacia.controller;

import br.com.farmacia.model.Produto;
import br.com.farmacia.repository.CategoriaRepository;
import br.com.farmacia.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        return ResponseEntity.ok((produtoRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id){
        return produtoRepository.findById(id)
                .map(resposta-> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
        return ResponseEntity.ok(produtoRepository.findByNomeContainingIgnoreCase(nome));
    }
    @PostMapping
    public ResponseEntity<Produto> postProduto (@Valid @RequestBody Produto produto){
          return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));

    }
    @PutMapping
    public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto){
                return produtoRepository.findById(produto.getId())
                        .map(resposta-> new ResponseEntity(produtoRepository.save(produto), HttpStatus.OK))
                        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        produtoRepository.deleteById(id);

    }
}
