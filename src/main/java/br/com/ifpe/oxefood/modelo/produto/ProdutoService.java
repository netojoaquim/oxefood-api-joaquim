package br.com.ifpe.oxefood.modelo.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.util.exception.ProdutoException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto save(Produto produto, Usuario usuarioLogado) throws ProdutoException {

        if (produto.getValorUnitario() < 10) {
            throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_PRODUTO);
        }

        produto.setHabilitado(Boolean.TRUE);
        produto.setCriadoPor(usuarioLogado);
        return repository.save(produto);
    }

    public List<Produto> listarTodos() {

        return repository.findAll();
    }

    public Produto obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public Produto update(Long id, Produto produtoAlterado, Usuario usuarioLogado) throws ProdutoException {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoException("Produto não encontrado com id: " + id));

        produto.setCategoria(produtoAlterado.getCategoria());
        produto.setCodigo(produtoAlterado.getCodigo());
        produto.setTitulo(produtoAlterado.getTitulo());
        produto.setDescricao(produtoAlterado.getDescricao());
        produto.setValorUnitario(produtoAlterado.getValorUnitario());
        produto.setTempoEntregaMaximo(produtoAlterado.getTempoEntregaMaximo());
        produto.setTempoEntregaMinimo(produtoAlterado.getTempoEntregaMinimo());

        produto.setUltimaModificacaoPor(usuarioLogado);

        return repository.save(produto);
    }


    @Transactional
    public void delete(Long id) {

        Produto produto = repository.findById(id).get();
        produto.setHabilitado(Boolean.FALSE);

        repository.save(produto);
    }
    public List<Produto> filtrar(String codigo, String titulo, Long idCategoria) {

       List<Produto> listaProdutos = repository.findAll();

       if ((codigo != null && !"".equals(codigo)) &&
           (titulo == null || "".equals(titulo)) &&
           (idCategoria == null)) {
               listaProdutos = repository.consultarPorCodigo(codigo);
       } else if (
           (codigo == null || "".equals(codigo)) &&
           (titulo != null && !"".equals(titulo)) &&
           (idCategoria == null)) {
               listaProdutos = repository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
       } else if (
           (codigo == null || "".equals(codigo)) &&
           (titulo == null || "".equals(titulo)) &&
           (idCategoria != null)) {
               listaProdutos = repository.consultarPorCategoria(idCategoria);
       } else if (
           (codigo == null || "".equals(codigo)) &&
           (titulo != null && !"".equals(titulo)) &&
           (idCategoria != null)) {
               listaProdutos = repository.consultarPorTituloECategoria(titulo, idCategoria);
       }

       return listaProdutos;
}

}
