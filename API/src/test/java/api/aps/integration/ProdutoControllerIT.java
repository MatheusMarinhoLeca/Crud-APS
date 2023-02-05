package api.logap.integration;

import api.logap.domain.Produto;
import api.logap.repository.ProdutoRepository;
import api.logap.requests.ProdutoPostRequestBody;
import api.logap.util.ProdutoCreator;
import api.logap.util.ProdutoPostRequestBodyCreator;
import api.logap.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProdutoControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("lista retorna lista de produtos num page object quando sucesso")
    void list_ReturnsListOfProdutoInsidePageObject_WhenSuccessful() {
        Produto savedproduto = produtoRepository.save(ProdutoCreator.createProdutoToBeSaved());

        String expectedName = savedproduto.getNome();

        PageableResponse<Produto> produtoPage = testRestTemplate.exchange("/api/produtos", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Produto>>() {
                }).getBody();

        Assertions.assertThat(produtoPage).isNotNull();

        Assertions.assertThat(produtoPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(produtoPage.toList().get(0).getNome()).isEqualTo(expectedName);
    }


    @Test
    @DisplayName("findById retorna produto quando sucesso")
    void findById_ReturnsProduto_WhenSuccessful() throws InterruptedException {
        Produto savedproduto = produtoRepository.save(ProdutoCreator.createProdutoToBeSaved());

        Long expectedId = savedproduto.getId();

        Produto produto = testRestTemplate.getForObject("/api/produtos/{id}", Produto.class, expectedId);

        Assertions.assertThat(produto).isNotNull();

        Assertions.assertThat(produto.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save retorna produto quando sucesso")

    void save_ReturnsProduto_WhenSuccessful() throws InterruptedException {

        ProdutoPostRequestBody produtoPostRequestBody = ProdutoPostRequestBodyCreator.createProdutoPostRequestBody();

        ResponseEntity<Produto> produtoResponseEntity = testRestTemplate.postForEntity("/api/produtos", produtoPostRequestBody, Produto.class);

        Assertions.assertThat(produtoResponseEntity).isNotNull();
        Assertions.assertThat(produtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(produtoResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(produtoResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace atualiza produto quando sucesso")

    void replace_UpdateProduto_WhenSuccessful() throws InterruptedException {
        Produto savedProduto = produtoRepository.save( ProdutoCreator.createProdutoToBeSaved());

        savedProduto.setNome("new name");

        ResponseEntity<Void> produtoResponseEntity = testRestTemplate.exchange("/api/produtos",
                HttpMethod.PUT,new HttpEntity<>(savedProduto), Void.class);

        Assertions.assertThat(produtoResponseEntity).isNotNull();

        Assertions.assertThat(produtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete remove produto quando sucesso")

    void delete_RemoveProduto_WhenSuccessful() throws InterruptedException {


        Produto savedProduto = produtoRepository.save(ProdutoCreator.createProdutoToBeSaved());

        ResponseEntity<Void> produtoResponseEntity = testRestTemplate.exchange("/api/produtos/{id}",
                HttpMethod.DELETE,null, Void.class, savedProduto.getId());

        Assertions.assertThat(produtoResponseEntity).isNotNull();

        Assertions.assertThat(produtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    @DisplayName("findByOrderByCategoriaAsc retorna uma lista de categorias ordenadas com suas quantidades totais de produtos num page object quando sucesso")
    void findByOrderByCategoriaAsc_ReturnsListOfObject_WhenSuccessful() throws InterruptedException {
        Produto savedProduto = produtoRepository.save(ProdutoCreator.createProdutoToBeSaved());
        String url = String.format("/api/produtos/findCategoriaAsc");

        PageableResponse<List<Object>> categorias = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<List<Object>>>() {
                }).getBody();

        Assertions.assertThat(categorias)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(categorias).isNotEmpty();
    }

}
