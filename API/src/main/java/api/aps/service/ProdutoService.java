package api.aps.service;

import api.aps.domain.Produto;
import api.aps.exception.BadRequestException;
import api.aps.mapper.ProdutoMapper;
import api.aps.repository.ProdutoRepository;
import api.aps.requests.ProdutoPostRequestBody;
import api.aps.requests.ProdutoPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    public Page<Produto> listAll(Pageable pageable)  {
        return produtoRepository.findAll(pageable);
    }

    public List<Produto> findByQtdDisponivel(String QtdDisponivel)  {

        return produtoRepository.findByQtdDisponivel(QtdDisponivel);
    }

    public List<String> findDistinctByQtdDisponivel(String QtdDisponivel)  {

        return produtoRepository.findDistinctByQtdDisponivel(QtdDisponivel);
    }

    public List<Produto> findByCategoria(String categoria)  {
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> findByFornecedor(String fornecedor)  {
        return produtoRepository.findByFornecedor(fornecedor);
    }
    public List<Produto>  findByOrderByFornecedorAsc()  {
        return produtoRepository.findByOrderByFornecedorAsc();
    }
    public List<Object> findByOrderByCategoriaAsc() {
        return produtoRepository.findByOrderByCategoriaAsc();
    }
    public Produto findByIdOrThrowBadRequestException(Long id)  {
       return produtoRepository.findById(id)
                .orElseThrow(()-> new BadRequestException( "Produto não encontrado"));
    }
    @Transactional
    public Produto save(ProdutoPostRequestBody produtoPostRequestBody){
        return produtoRepository.save(ProdutoMapper.INSTANCE.toProduto( produtoPostRequestBody));
    }
    @Transactional
    public void delete(Long id) {

        produtoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(ProdutoPutRequestBody produtoPutRequestBody) {
        Produto produtoSaved = findByIdOrThrowBadRequestException(produtoPutRequestBody.getId());
        Produto produto = ProdutoMapper.INSTANCE.toProduto(produtoPutRequestBody);
        produto.setId(produtoSaved.getId());
        produtoRepository.save(produto);

    }


}
