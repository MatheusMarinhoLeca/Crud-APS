package api.aps.facade;

import api.aps.domain.Produto;
import api.aps.requests.ProdutoPostRequestBody;
import api.aps.requests.ProdutoPutRequestBody;
import api.aps.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoFacade {

    private final ProdutoService produtoService;

    public Page<Produto> listAll(Pageable pageable) {
        return produtoService.listAll(pageable);
    }

    public List<Produto> findByQtdDisponivel(String QtdDisponivel) {
        return produtoService.findByQtdDisponivel(QtdDisponivel);
    }

    public List<String> findDistinctByQtdDisponivel(String QtdDisponivel) {
        return produtoService.findDistinctByQtdDisponivel(QtdDisponivel);
    }

    public List<Produto> findByCategoria(String categoria) {
        return produtoService.findByCategoria(categoria);
    }

    public List<Produto> findByFornecedor(String fornecedor) {
        return produtoService.findByFornecedor(fornecedor);
    }

    public List<Produto> findByOrderByFornecedorAsc() {
        return produtoService.findByOrderByFornecedorAsc();
    }

    public List<Object> findByOrderByCategoriaAsc() {
        return produtoService.findByOrderByCategoriaAsc();
    }

    public Produto findByIdOrThrowBadRequestException(Long id) {
        return produtoService.findByIdOrThrowBadRequestException(id);
    }

    @Transactional
    public Produto save(ProdutoPostRequestBody produtoPostRequestBody) {
        return produtoService.save(produtoPostRequestBody);
    }

    @Transactional
    public void delete(Long id) {
        produtoService.delete(id);
    }

    @Transactional
    public void replace(ProdutoPutRequestBody produtoPutRequestBody) {
        produtoService.replace(produtoPutRequestBody);
    }
}