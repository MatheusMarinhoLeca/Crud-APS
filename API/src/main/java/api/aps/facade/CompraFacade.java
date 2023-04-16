package api.aps.facade;

import api.aps.domain.Cep;
import api.aps.domain.Compra;
import api.aps.exception.BadRequestException;
import api.aps.mapper.CompraMapper;
import api.aps.repository.CompraRepository;
import api.aps.requests.CompraPostRequestBody;
import api.aps.requests.CompraPutRequestBody;
import api.aps.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CompraFacade {

    private final CompraService compraService;

    public Page<Compra> listAll(Pageable pageable) {
        return compraService.listAll(pageable);
    }

    public Compra findById(Long id) {
        return compraService.findByIdOrThrowBadRequestException(id);
    }

    public Cep getCep(String cep) throws Exception {
        return compraService.getCep(cep);
    }

    @Transactional
    public Compra save(CompraPostRequestBody compraPostRequestBody) {
        return compraService.save(compraPostRequestBody);
    }

    @Transactional
    public void delete(Long id) {
        compraService.delete(id);
    }

    @Transactional
    public void replace(CompraPutRequestBody compraPutRequestBody) {
        compraService.replace(compraPutRequestBody);
    }
}