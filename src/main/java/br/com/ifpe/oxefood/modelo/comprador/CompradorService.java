package br.com.ifpe.oxefood.modelo.comprador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class CompradorService {
    @Autowired
    private CompradorRepository repository;

    @Transactional
    public Comprador save(Comprador comprador){
        comprador.setHabilitado(Boolean.TRUE);
        return repository.save(comprador);
    }
    public List<Comprador> listarTodos() {

        return repository.findAll();
    }

    public Comprador obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Comprador compradorAlterado) {

        Comprador comprador = repository.findById(id).get();
        comprador.setSegmento(compradorAlterado.getSegmento());
        comprador.setNome(compradorAlterado.getNome());
        comprador.setEnderecoComercial(compradorAlterado.getEnderecoComercial());
        comprador.setEnderecoResidencial(compradorAlterado.getEnderecoResidencial());
        comprador.setComissao(compradorAlterado.getComissao());
        comprador.setQtdComprasMediasMes(compradorAlterado.getQtdComprasMediasMes());
        comprador.setContratadoEm(compradorAlterado.getContratadoEm());

        repository.save(comprador);
    }

    @Transactional
    public void delete(Long id) {

       Comprador comprador = repository.findById(id).get();
       comprador.setHabilitado(Boolean.FALSE);

       repository.save(comprador);
   }
}
