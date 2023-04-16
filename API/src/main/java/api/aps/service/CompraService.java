package api.aps.service;

import api.aps.adapter.CepAdapter;
import api.aps.domain.Cep;
import api.aps.domain.Compra;
import api.aps.exception.BadRequestException;
import api.aps.mapper.CompraMapper;
import api.aps.repository.CompraRepository;
import api.aps.requests.CompraPostRequestBody;
import api.aps.requests.CompraPutRequestBody;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
@RequiredArgsConstructor
public class CompraService{

    private final CompraRepository compraRepository;
    private final CepAdapter.CepService cepService = new CepAdapter.ExternalCepService();
    public Page<Compra> listAll(Pageable pageable)  {
        return compraRepository.findAll(pageable);
    }
    public Compra findByIdOrThrowBadRequestException(Long id)  {
        return compraRepository.findById(id)
                .orElseThrow(()-> new BadRequestException( "Compra não encontrado"));
    }
    public Cep getCep(String cep) throws Exception {
        return cepService.getCep(cep);
    }
    @Transactional
    public Compra save(CompraPostRequestBody compraPostRequestBody) {
        Compra compra = CompraMapper.INSTANCE.toCompra(compraPostRequestBody);
        compra.setQuantidade(compraPostRequestBody.getQuantidade());
        return compraRepository.save(compra);
    }

    @Transactional
    public void delete(Long id) {

        compraRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(CompraPutRequestBody compraPutRequestBody) {
        Compra compraSaved = findByIdOrThrowBadRequestException(compraPutRequestBody.getId());
        Compra compra = CompraMapper.INSTANCE.toCompra(compraPutRequestBody);
        compra.setId(compraSaved.getId());
        compraRepository.save(compra);

    }
}
