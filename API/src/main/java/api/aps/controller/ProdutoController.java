package api.logap.controller;

import api.logap.domain.Produto;
import api.logap.requests.ProdutoPostRequestBody;
import api.logap.requests.ProdutoPutRequestBody;
import api.logap.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produtos")
//@CrossOrigin(origins = "http://localhost:4200")
@Log4j2
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<Produto>> list(Pageable pageable) throws InterruptedException {
        return ResponseEntity.ok(produtoService.listAll(pageable));
    }

    @GetMapping(path= "/findByQtdDisponivel")
    public ResponseEntity<Page<Produto>> findByQtdDisponivel() throws InterruptedException {
        final Page<Produto> page = new PageImpl<>(produtoService.findByQtdDisponivel("0"));
        return ResponseEntity.ok(page);
    }

    @GetMapping(path= "/findFornecedorDistinctByQtdDisponivel")
    public ResponseEntity<Page<String>> findDistinctByQtdDisponivel() throws InterruptedException {
        final Page<String> page = new PageImpl<>(produtoService.findDistinctByQtdDisponivel("0"));
        return ResponseEntity.ok(page);
    }

    @GetMapping(path= "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) throws InterruptedException {
        return ResponseEntity.ok(produtoService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path= "/findCategoria")
    public ResponseEntity<Page<Produto>> findByCategoria( @RequestParam (required = false) String categoria) throws InterruptedException {
        final Page<Produto> page = new PageImpl<>(produtoService.findByCategoria(categoria));
        return ResponseEntity.ok(page);
    }

    @GetMapping(path= "/findCategoriaAsc")
    public ResponseEntity<Page<Object>> findByOrderByCategoriaAsc() throws InterruptedException {
        final Page<Object> page = new PageImpl<>(produtoService.findByOrderByCategoriaAsc());
        return ResponseEntity.ok(page);
    }

    @GetMapping(path= "/findFornecedor")
    public ResponseEntity<Page<Produto>> findByFornecedor( @RequestParam (required = false) String fornecedor) throws InterruptedException {
        final Page<Produto> page = new PageImpl<>(produtoService. findByFornecedor(fornecedor));
       return ResponseEntity.ok( page );
    }

    @GetMapping(path= "/findFornecedorAsc")
    public ResponseEntity<Page<Produto>> findByOrderByFornecedorAsc() throws InterruptedException {
        final Page<Produto> page = new PageImpl<>(produtoService.findByOrderByFornecedorAsc());
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoPostRequestBody produtoPostRequestBody){
       return new ResponseEntity<>(produtoService.save(produtoPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path= "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid ProdutoPutRequestBody produtoPutRequestBody) {
        produtoService.replace(produtoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
