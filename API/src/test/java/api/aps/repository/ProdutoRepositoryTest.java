package api.logap.repository;

import api.logap.domain.Produto;

import api.logap.exception.ExceptionDetails;
import org.assertj.core.api.Assertions;
import org.hibernate.exception.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static api.logap.util.ProdutoCreator.createProdutoToBeSaved;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@DisplayName("Testes para o repositório Produto")
class ProdutoRepositoryTest {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Test@DisplayName("Salva o anime criado quando sucesso")
    void save_PersistProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);
        Assertions.assertThat(produtoSalvo).isNotNull();
        Assertions.assertThat(produtoSalvo.getId()).isNotNull();
        Assertions.assertThat(produtoSalvo.getNome()).isEqualTo(produto.getNome());
        Assertions.assertThat(produtoSalvo.getValorCompra()).isEqualTo(produto.getValorCompra());
        Assertions.assertThat(produtoSalvo.getValorVenda()).isEqualTo(produto.getValorVenda());
        Assertions.assertThat(produtoSalvo.getQtdDisponivel()).isEqualTo(produto.getQtdDisponivel());
        Assertions.assertThat(produtoSalvo.getCategoria()).isEqualTo(produto.getCategoria());
        Assertions.assertThat(produtoSalvo.getFornecedor()).isEqualTo( produto.getFornecedor());

    }
    @Test@DisplayName("Salva o anime atualizado quando sucesso")
    void save_UpdatedProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);
        produtoSalvo.setNome("Caneta Vermelha");
        Produto produtoAtualizado =  this.produtoRepository.save(produtoSalvo);
        Assertions.assertThat(produtoAtualizado).isNotNull();
        Assertions.assertThat(produtoAtualizado.getId()).isNotNull();
        Assertions.assertThat(produtoAtualizado.getNome()).isEqualTo(produtoSalvo.getNome());
        Assertions.assertThat(produtoAtualizado.getValorCompra()).isEqualTo(produtoSalvo.getValorCompra());
        Assertions.assertThat(produtoAtualizado.getValorVenda()).isEqualTo(produtoSalvo.getValorVenda());
        Assertions.assertThat(produtoAtualizado.getQtdDisponivel()).isEqualTo(produtoSalvo.getQtdDisponivel());
        Assertions.assertThat(produtoAtualizado.getCategoria()).isEqualTo(produtoSalvo.getCategoria());
        Assertions.assertThat(produtoAtualizado.getFornecedor()).isEqualTo(produtoSalvo.getFornecedor());

    }

    @Test@DisplayName("Deleta o produto quando sucesso")
    void delete_RemovesProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);

        this.produtoRepository.delete(produtoSalvo);
        Optional<Produto> produtoOptionaluto= this.produtoRepository.findById(produtoSalvo.getId());

        Assertions.assertThat(produtoOptionaluto.isEmpty()).isTrue();

    }

    @Test@DisplayName("O find by categoria retorna uma lista de produtos quando sucesso")
    void findByCategoria_ReturnListOfProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);

        String categoria = produtoSalvo.getCategoria();
        List<Produto> produtos = this.produtoRepository.findByCategoria(categoria);

        Assertions.assertThat(produtos.isEmpty()).isFalse();
        Assertions.assertThat(produtos.contains(produtoSalvo)).isTrue();

    }

    @Test@DisplayName("O find by categoria retorna uma lista de produtos vazia quando o produto não é encontrado")
    void findByCategoria_ReturnEmptyListOfProduto_WhenProdutoIsNotFound(){
        List<Produto> produtos = this.produtoRepository.findByCategoria("teste");

        Assertions.assertThat( produtos.isEmpty()).isTrue();


    }

    @Test@DisplayName("O find by fornecedor retorna uma lista de produtos quando sucesso")
    void findByFornecedor_ReturnListOfProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);

        String fornecedor = produtoSalvo.getFornecedor();
        List<Produto> produtos = this.produtoRepository.findByFornecedor(fornecedor);

        Assertions.assertThat(produtos.isEmpty()).isFalse();
        Assertions.assertThat(produtos.contains(produtoSalvo)).isTrue();

    }

    @Test@DisplayName("O find by fornecedor retorna uma lista de produtos vazia quando o produto não é encontrado")
    void findByFornecedor_ReturnEmptyListOfProduto_WhenProdutoIsNotFound(){
        List<Produto> produtos = this.produtoRepository.findByFornecedor("teste");

        Assertions.assertThat( produtos.isEmpty()).isTrue();


    }

    @Test@DisplayName("O find by qtdDisponivel retorna uma lista de produtos quando sucesso")
    void findByQtdDisponivel_ReturnListOfProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);

        String qtdDisponivel = produtoSalvo.getQtdDisponivel();
        List<Produto> produtos = this.produtoRepository.findByQtdDisponivel(qtdDisponivel);

        Assertions.assertThat(produtos.isEmpty()).isFalse();
        Assertions.assertThat(produtos.contains(produtoSalvo)).isTrue();

    }

    @Test@DisplayName("O find by qtdDisponivel retorna uma lista de produtos vazia quando o produto não é encontrado")
    void findByQtdDisponivel_ReturnEmptyListOfProduto_WhenProdutoIsNotFound(){
        List<Produto> produtos = this.produtoRepository.findByQtdDisponivel("teste");

        Assertions.assertThat( produtos.isEmpty()).isTrue();


    }

    @Test@DisplayName("O find by Distinct qtdDisponivel retorna uma lista de fornecedores distintos quando sucesso ao passar quantidade como 0 ")
    void findDistinctByQtdDisponivel_ReturnListOfProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produto2= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);
        Produto produtoSalvo2 = this.produtoRepository.save(produto2);

        String qtdDisponivel = produtoSalvo.getQtdDisponivel();
        List<String> produtos = this.produtoRepository.findDistinctByQtdDisponivel(qtdDisponivel);

        Assertions.assertThat(produtos.isEmpty()).isFalse();
        Assertions.assertThat(produtos.size()==1).isTrue();

    }

    @Test@DisplayName("O find by Distinct qtdDisponivel retorna uma lista de produtos fornecedores quando a qtdDisponivel não é 0 ")
    void findDistinctByQtdDisponivel_ReturnEmptyListOfProduto_WhenProdutoIsNotFound(){
        List<String> produtos = this.produtoRepository.findDistinctByQtdDisponivel("teste");

        Assertions.assertThat( produtos.isEmpty()).isTrue();


    }

    @Test@DisplayName("O find by Order Categoria retorna uma lista de categorias distintos e suas quantidades quando sucesso ")
    void findByOrderByCategoriaAsc_ReturnListOfProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produto2= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);
        Produto produtoSalvo2 = this.produtoRepository.save(produto2);

        List<Object> categorias = this.produtoRepository.findByOrderByCategoriaAsc();



        Assertions.assertThat(categorias.isEmpty()).isFalse();


    }

    @Test@DisplayName("O find by Order Categoria retorna uma lista de categorias vazia quando sucesso a quantidade não é 0  ")
    void findByOrderByCategoriaAsc_ReturnEmptyListOfProduto_WhenProdutoIsNotFound() {
        List<String> produtos = this.produtoRepository.findDistinctByQtdDisponivel("teste");

        Assertions.assertThat(produtos.isEmpty()).isTrue();


    }

    @Test@DisplayName("O find by Order Fornecedor retorna uma lista de fornecedores em ordem quando sucesso ")
    void findByOrderByFornecedorAsc_ReturnListOfProduto_WhenSuccessful(){
        Produto produto= createProdutoToBeSaved();
        Produto produto2= createProdutoToBeSaved();
        Produto produtoSalvo = this.produtoRepository.save(produto);
        Produto produtoSalvo2 = this.produtoRepository.save(produto2);

        List<Produto> fornecedores = this.produtoRepository.findByOrderByFornecedorAsc();



        Assertions.assertThat(fornecedores.isEmpty()).isFalse();


    }

    @Test@DisplayName("O find by Order Fornecedor retorna uma lista de categorias vazia quando sucesso a quantidade não é 0  ")
    void findByOrderByFornecedorAsc_ReturnEmptyListOfProduto_WhenProdutoIsNotFound() {
        List<Produto> fornecedores = this.produtoRepository.findByOrderByFornecedorAsc();

        Assertions.assertThat(fornecedores.isEmpty()).isTrue();


    }


}