package br.com.ifpe.oxefood.api.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;

import br.com.ifpe.oxefood.modelo.produto.CategoriaProdutoService;
import br.com.ifpe.oxefood.modelo.produto.Produto;
import br.com.ifpe.oxefood.modelo.produto.ProdutoService;
import br.com.ifpe.oxefood.util.exception.ProdutoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/produto")

public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ProdutoRequest produtoRequest,HttpServletRequest request ) {
        try {
            Produto produto = produtoRequest.build();

            produto.setCategoria(categoriaProdutoService.obterPorID(produtoRequest.getIdCategoria()));
            produto.setUsuario(usuarioService.obterUsuarioLogado(request));

            Produto salvo = produtoService.save(produto, usuarioService.obterUsuarioLogado(request));

            return new ResponseEntity<Produto>(salvo, HttpStatus.CREATED);
        } catch (ProdutoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/filtrar")
    public List<Produto> filtrar(
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "idCategoria", required = false) Long idCategoria) {

        return produtoService.filtrar(codigo, titulo, idCategoria);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable("id") Long id,
            @RequestBody @Valid ProdutoRequest produtoRequest,
            HttpServletRequest request) {
        try {
            Produto produto = produtoRequest.build();
            produto.setCategoria(categoriaProdutoService.obterPorID(produtoRequest.getIdCategoria()));
            produto.setUsuario(usuarioService.obterUsuarioLogado(request));
            Produto atualizado = produtoService.update(id, produto, usuarioService.obterUsuarioLogado(request));

            return ResponseEntity.ok(atualizado);
        } catch (ProdutoException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        produtoService.delete(id);

        return ResponseEntity.ok().build();
    }
}
