package api.logap.service;

import api.logap.controller.ProdutoController;
import api.logap.domain.Produto;
import api.logap.repository.ProdutoRepository;
import api.logap.requests.ProdutoPostRequestBody;
import api.logap.requests.ProdutoPutRequestBody;
import api.logap.util.ProdutoCreator;
import api.logap.util.ProdutoPostRequestBodyCreator;
import api.logap.util.ProdutoPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class ProdutoServiceTest {
    @InjectMocks
    private ProdutoService produtoService;
    @Mock
    private ProdutoRepository produtoRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Produto> produtoPage = new PageImpl<>(List.of(ProdutoCreator.createProdutoValid()));
        BDDMockito.when(produtoRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(produtoPage);

        BDDMockito.when(produtoRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(ProdutoCreator.createProdutoValid()));


        BDDMockito.when(produtoRepositoryMock.findByOrderByCategoriaAsc())
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoRepositoryMock.findDistinctByQtdDisponivel("0"))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid().getFornecedor()));

        BDDMockito.when(produtoRepositoryMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoRepositoryMock.findByFornecedor(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoRepositoryMock.findByOrderByFornecedorAsc())
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));


        BDDMockito.when(produtoRepositoryMock.findByQtdDisponivel(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoRepositoryMock.save(ArgumentMatchers.any(Produto.class)))
                .thenReturn(ProdutoCreator.createProdutoValid());



        BDDMockito.doNothing().when(produtoRepositoryMock).delete(ArgumentMatchers.any(Produto.class));
    }
    @Test
    @DisplayName("Lista retorna uma lista de produtos num page object quando sucesso")
    void listAll_ReturnsListOfProdutosInsidePageObject_WhenSuccessful() throws InterruptedException {
        String expectedName = ProdutoCreator.createProdutoValid().getNome();

        Page<Produto> produtoPage = produtoService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(produtoPage).isNotNull();

        Assertions.assertThat(produtoPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtoPage.toList().get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException retorna produto quando sucesso")
    void findByIdOrThrowBadRequestException_ReturnsProduto_WhenSuccessful() throws InterruptedException {
        Long expectedId = ProdutoCreator.createProdutoValid().getId();

        Produto produto = produtoService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(produto).isNotNull();

        Assertions.assertThat(produto.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save retorna produto quando sucesso")
    void save_ReturnsProduto_WhenSuccessful() throws InterruptedException {

        Produto produto = produtoService.save(ProdutoPostRequestBodyCreator.createProdutoPostRequestBody());


        Assertions.assertThat(produto).isNotNull().isEqualTo(ProdutoCreator.createProdutoValid());
    }

    @Test
    @DisplayName("replace atualiza produto quando sucesso")
    void replace_UpdateProduto_WhenSuccessful() throws InterruptedException {


        Assertions.assertThatCode(() ->produtoService.replace(ProdutoPutRequestBodyCreator.createProdutoPutRequestBody()))
                .doesNotThrowAnyException();



    }

    @Test
    @DisplayName("delete remove produto quando sucesso")
    void delete_RemoveProduto_WhenSuccessful() throws InterruptedException {


        Assertions.assertThatCode(() ->produtoService.delete(1L))
                .doesNotThrowAnyException();



    }


    @Test
    @DisplayName("findByQtdDisponivel retorna uma lista de produtos com qtdDisponivel 0 num page object quando sucesso")
    void findQtdDisponivel_ReturnsListOfProduto_WhenSuccessful() throws InterruptedException {
        String expectedQtdDisponivel = ProdutoCreator.createProdutoValid().getQtdDisponivel();

        List<Produto> produtos = produtoService.findByQtdDisponivel("0");

        Assertions.assertThat(produtos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtos.get(0).getQtdDisponivel()).isEqualTo(expectedQtdDisponivel);
    }

    @Test
    @DisplayName("findDistinctByQtdDisponivel retorna uma lista de fornecedores com qtdDisponivel 0 num page object quando sucesso")
    void findDistinctByQtdDisponivel_ReturnsListOfFornecedores_WhenSuccessful() throws InterruptedException {
        String expectedFornecedor = ProdutoCreator.createProdutoValid().getFornecedor();

        List<String> fornecedores = produtoService.findDistinctByQtdDisponivel("0");

        Assertions.assertThat(fornecedores)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(fornecedores.get(0)).isEqualTo(expectedFornecedor);
    }

    @Test
    @DisplayName("findByCategoria retorna uma lista de produtos com dada categoria num page object quando sucesso")
    void findByCategoria_ReturnsListOfProduto_WhenSuccessful() throws InterruptedException {
        String expectedCategoria = ProdutoCreator.createProdutoValid().getCategoria();

        List<Produto> produtos = produtoService.findByCategoria(expectedCategoria);

        Assertions.assertThat(produtos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtos.get(0).getCategoria()).isEqualTo(expectedCategoria);
    }

    @Test
    @DisplayName("findByFornecedor retorna uma lista de produtos com dado fornecedor num page object quando sucesso")
    void findByFornecedor_ReturnsListOfProduto_WhenSuccessful() throws InterruptedException {
        String expectedFornecedor = ProdutoCreator.createProdutoValid().getFornecedor();

        List<Produto> produtos = produtoService.findByFornecedor(expectedFornecedor);

        Assertions.assertThat(produtos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtos.get(0).getFornecedor()).isEqualTo(expectedFornecedor);
    }

    @Test
    @DisplayName("findByOrderByCategoriaAsc retorna uma lista de categorias ordenadas com suas quantidades totais de produtos num page object quando sucesso")
    void findByOrderByCategoriaAsc_ReturnsListOfObject_WhenSuccessful() throws InterruptedException {
        String expectedCategoria = ProdutoCreator.createProdutoValid().getCategoria();

        List<Object> categorias = produtoService.findByOrderByCategoriaAsc();

        Assertions.assertThat(categorias)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(categorias.get(0)).isNotNull();
    }

    @Test
    @DisplayName("findByOrderByFornecedorAscretorna uma lista de produtos ordenados por fornecedor num page object quando sucesso")
    void findByOrderByFornecedorAsc_ReturnsListOfProduto_WhenSuccessful() throws InterruptedException {
        String expectedFornecedor = ProdutoCreator.createProdutoValid().getFornecedor();

        List<Produto> produtos = produtoService.findByOrderByFornecedorAsc();

        Assertions.assertThat(produtos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtos.get(0).getFornecedor()).isEqualTo(expectedFornecedor);
    }
    @Test
    @DisplayName("findByQtdDisponivel retorna uma lista vazia de Produtos quando não achar com os critérios")
    void findByQtdDisponivel_ReturnsEmptyListOfProduto_WhenProdutoIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoRepositoryMock.findByQtdDisponivel(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Produto> produtos = produtoService.findByQtdDisponivel("");

        Assertions.assertThat(produtos)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByCategoria retorna uma lista vazia quando não bate os critérios")
    void findByCategoria_ReturnsEmptyListOfProduto_WhenCategoriaIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoRepositoryMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Produto> produtoss = produtoService.findByCategoria("");

        Assertions.assertThat(produtoss)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findFornecedorDistinctByQtdDisponivel retorna lista vazia de fornecedores se não achar")
    void findDistinctByQtdDisponivel_ReturnsEmptyListOfProduto_WhenProdutoIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoRepositoryMock.findDistinctByQtdDisponivel(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<String> fornecedores = produtoService.findDistinctByQtdDisponivel("");

        Assertions.assertThat(fornecedores)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByOrderByCategoriaAsc retorna uma lista vazia de categorias se não achar")
    void findByOrderByCategoriaAsc_ReturnsEmptyListOfProduto_WhenCategoriaIsNotProduto() throws InterruptedException {
        BDDMockito.when(produtoRepositoryMock.findByOrderByCategoriaAsc())
                .thenReturn(Collections.emptyList());

        List<Object> categorias = produtoService.findByOrderByCategoriaAsc();

        Assertions.assertThat(categorias)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByFornecedor retorna uma lista vazia de produtos se não achar")
    void findByFornecedor_ReturnsEmptyListOfProduto_WhenFornecedorIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoRepositoryMock.findByFornecedor(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Produto> produtos = produtoService.findByFornecedor("");

        Assertions.assertThat(produtos)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByOrderByFornecedor retorna uma lista vazia de produtos se não achar")
    void findByOrderByFornecedorAscEmptyListOfProduto_WhenProdutoIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoRepositoryMock.findByOrderByFornecedorAsc())
                .thenReturn(Collections.emptyList());

        List<Produto> produtos = produtoService.findByOrderByFornecedorAsc();

        Assertions.assertThat(produtos)
                .isNotNull()
                .isEmpty();

    }
}