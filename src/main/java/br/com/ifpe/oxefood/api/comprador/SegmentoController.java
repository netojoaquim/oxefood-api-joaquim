package br.com.ifpe.oxefood.api.comprador;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.comprador.SegmentoService;
import br.com.ifpe.oxefood.modelo.comprador.Segmento;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/segmento")

public class SegmentoController {

    @Autowired
    private SegmentoService segmentoService;

    @PostMapping
    public ResponseEntity<Segmento> save(@RequestBody SegmentoRequest request) {
        Segmento segmentoNovo = request.build();
        Segmento segmentoComprador = segmentoService.save(segmentoNovo);
        segmentoService.save(segmentoNovo);
        return new ResponseEntity<>(segmentoComprador, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Segmento> listarTodos() {
        return segmentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Segmento obterPorID(@PathVariable Long id) {
        return segmentoService.obterPorID(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Segmento> update(@PathVariable("id") Long id, @RequestBody SegmentoRequest request) {

        Segmento categoriaProduto = request.build();
        segmentoService.update(id, categoriaProduto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        segmentoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
