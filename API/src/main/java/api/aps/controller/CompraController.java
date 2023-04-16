package api.aps.controller;

import api.aps.domain.Cep;
import api.aps.domain.Compra;
import api.aps.domain.Produto;
import api.aps.facade.CompraFacade;
import api.aps.requests.CompraPostRequestBody;
import api.aps.requests.CompraPutRequestBody;
import api.aps.service.CompraService;
import api.aps.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/compras")
@CrossOrigin(origins = "http://localhost:4200")
@Log4j2
@RequiredArgsConstructor
public class CompraController {

    private final CompraFacade compraFacade;

    @GetMapping(path= "/cep/{cep}")
    public ResponseEntity<Cep> getCep(@PathVariable String cep) throws Exception {
        return ResponseEntity.ok(compraFacade.getCep(cep));
    }

    @GetMapping
    public ResponseEntity<Page<Compra>> list(Pageable pageable) throws InterruptedException {
        return ResponseEntity.ok(compraFacade.listAll(pageable));
    }

    @GetMapping(path= "/{id}")
    public ResponseEntity<Compra> findById(@PathVariable Long id) throws InterruptedException {
        return ResponseEntity.ok(compraFacade.findById(id));
    }

    @PostMapping
    public ResponseEntity<Compra> save(@RequestBody @Valid CompraPostRequestBody compraPostRequestBody){
        return new ResponseEntity<>(compraFacade.save(compraPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path= "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compraFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid CompraPutRequestBody compraPutRequestBody) {
        compraFacade.replace(compraPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
