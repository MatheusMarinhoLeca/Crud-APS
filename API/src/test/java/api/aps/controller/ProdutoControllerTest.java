package api.logap.controller;

import api.logap.domain.Produto;
import api.logap.requests.ProdutoPostRequestBody;
import api.logap.requests.ProdutoPutRequestBody;
import api.logap.service.ProdutoService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class ProdutoControllerTest {
    @InjectMocks
    private ProdutoController produtoController;
    @Mock
    private ProdutoService  produtoServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Produto> produtoPage = new PageImpl<>(List.of(ProdutoCreator.createProdutoValid()));
        BDDMockito.when(produtoServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(produtoPage);

        BDDMockito.when(produtoServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(ProdutoCreator.createProdutoValid());
        BDDMockito.when(produtoServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(ProdutoCreator.createProdutoValid());

        BDDMockito.when(produtoServiceMock.findByOrderByCategoriaAsc())
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoServiceMock.findDistinctByQtdDisponivel("0"))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid().getFornecedor()));

        BDDMockito.when(produtoServiceMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoServiceMock.findByFornecedor(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(ProdutoCreator.createProdutoValid());

        BDDMockito.when(produtoServiceMock.findByOrderByFornecedorAsc())
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));


        BDDMockito.when(produtoServiceMock.findByQtdDisponivel(ArgumentMatchers.anyString()))
                .thenReturn(List.of(ProdutoCreator.createProdutoValid()));

        BDDMockito.when(produtoServiceMock.save(ArgumentMatchers.any(ProdutoPostRequestBody.class)))
                .thenReturn(ProdutoCreator.createProdutoValid());

        BDDMockito.doNothing().when(produtoServiceMock).replace(ArgumentMatchers.any(ProdutoPutRequestBody.class));

        BDDMockito.doNothing().when(produtoServiceMock).delete(ArgumentMatchers.anyLong());
    }
    @Test
    @DisplayName("Lista retorna uma lista de produtos num page object quando sucesso")
    void list_ReturnsListOfProdutosInsidePageObject_WhenSuccessful() throws InterruptedException {
        String expectedName = ProdutoCreator.createProdutoValid().getNome();

        Page<Produto> produtoPage = produtoController.list(null).getBody();

        Assertions.assertThat(produtoPage).isNotNull();

        Assertions.assertThat(produtoPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtoPage.toList().get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById retorna produto quando sucesso")
    void findById_ReturnsProduto_WhenSuccessful() throws InterruptedException {
        Long expectedId = ProdutoCreator.createProdutoValid().getId();

        Produto produto = produtoController.findById(1L).getBody();

        Assertions.assertThat(produto).isNotNull();

        Assertions.assertThat(produto.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("save retorna produto quando sucesso")
    void save_ReturnsProduto_WhenSuccessful() throws InterruptedException {

        Produto produto = produtoController.save(ProdutoPostRequestBodyCreator.createProdutoPostRequestBody()).getBody();


        Assertions.assertThat(produto).isNotNull().isEqualTo(ProdutoCreator.createProdutoValid());
    }

    @Test
    @DisplayName("replace atualiza produto quando sucesso")
    void replace_UpdateProduto_WhenSuccessful() throws InterruptedException {


        Assertions.assertThatCode(() ->produtoController.replace(ProdutoPutRequestBodyCreator.createProdutoPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = produtoController.replace(ProdutoPutRequestBodyCreator.createProdutoPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete remove produto quando sucesso")
    void delete_RemoveProduto_WhenSuccessful() throws InterruptedException {


        Assertions.assertThatCode(() ->produtoController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = produtoController.delete(1L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    @DisplayName("findByQtdDisponivel retorna uma lista de produtos com qtdDisponivel 0 num page object quando sucesso")
    void findByQtdDisponivel_ReturnsListOfProduto_WhenSuccessful() throws InterruptedException {
        String expectedQtdDisponivel = ProdutoCreator.createProdutoValid().getQtdDisponivel();

        List<Produto> produtos = produtoController.findByQtdDisponivel().getBody().getContent();

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

        List<String> fornecedores = produtoController.findDistinctByQtdDisponivel().getBody().getContent();

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

        List<Produto> produtos = produtoController.findByCategoria(expectedCategoria).getBody().getContent();

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

        List<Produto> produtos = produtoController.findByFornecedor(expectedFornecedor).getBody().getContent();

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

        List<Object> categorias = produtoController.findByOrderByCategoriaAsc().getBody().getContent();

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

        List<Produto> produtos = produtoController.findByOrderByFornecedorAsc().getBody().getContent();

        Assertions.assertThat(produtos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtos.get(0).getFornecedor()).isEqualTo(expectedFornecedor);
    }
    @Test
    @DisplayName("findByQtdDisponivel retorna uma lista vazia de Produtos quando não achar com os critérios")
    void findByQtdDisponivel_ReturnsEmptyListOfProduto_WhenProdutoIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoServiceMock.findByQtdDisponivel(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Produto> produtos = produtoController.findByQtdDisponivel().getBody().getContent();

        Assertions.assertThat(produtos)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByCategoria retorna uma lista vazia quando não bate os critérios")
    void findByCategoria_ReturnsEmptyListOfProduto_WhenCategoriaIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoServiceMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Produto> produtoss = produtoController.findByCategoria("").getBody().getContent();

        Assertions.assertThat(produtoss)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findFornecedorDistinctByQtdDisponivel retorna lista vazia de fornecedores se não achar")
    void findDistinctByQtdDisponivel_ReturnsEmptyListOfProduto_WhenProdutoIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoServiceMock.findDistinctByQtdDisponivel(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<String> fornecedores = produtoController.findDistinctByQtdDisponivel().getBody().getContent();

        Assertions.assertThat(fornecedores)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByOrderByCategoriaAsc retorna uma lista vazia de categorias se não achar")
    void findByOrderByCategoriaAsc_ReturnsEmptyListOfProduto_WhenCategoriaIsNotProduto() throws InterruptedException {
        BDDMockito.when(produtoServiceMock.findByOrderByCategoriaAsc())
                .thenReturn(Collections.emptyList());

        List<Object> categorias = produtoController.findByOrderByCategoriaAsc().getBody().getContent();

        Assertions.assertThat(categorias)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByFornecedor retorna uma lista vazia de produtos se não achar")
    void findByFornecedor_ReturnsEmptyListOfProduto_WhenFornecedorIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoServiceMock.findByFornecedor(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Produto> produtos = produtoController.findByFornecedor("").getBody().getContent();

        Assertions.assertThat(produtos)
                .isNotNull()
                .isEmpty();

    }
    @Test
    @DisplayName("findByOrderByFornecedor retorna uma lista vazia de produtos se não achar")
    void findByOrderByFornecedorAscEmptyListOfProduto_WhenProdutoIsNotFound() throws InterruptedException {
        BDDMockito.when(produtoServiceMock.findByOrderByFornecedorAsc())
                .thenReturn(Collections.emptyList());

        List<Produto> produtos = produtoController.findByOrderByFornecedorAsc().getBody().getContent();

        Assertions.assertThat(produtos)
                .isNotNull()
                .isEmpty();

    }

}