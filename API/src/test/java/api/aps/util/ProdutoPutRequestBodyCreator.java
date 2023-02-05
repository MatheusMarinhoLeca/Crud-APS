package api.logap.util;

import api.logap.requests.ProdutoPostRequestBody;
import api.logap.requests.ProdutoPutRequestBody;

public class ProdutoPutRequestBodyCreator {
    public static ProdutoPutRequestBody createProdutoPutRequestBody(){
        return ProdutoPutRequestBody.builder()
                .id(ProdutoCreator.createProdutoUpdated().getId())
                .nome(ProdutoCreator.createProdutoUpdated().getNome())
                .valorCompra(ProdutoCreator.createProdutoUpdated().getValorCompra())
                .valorVenda(ProdutoCreator.createProdutoUpdated().getValorVenda())
                .categoria(ProdutoCreator.createProdutoUpdated().getCategoria())
                .qtdDisponivel(ProdutoCreator.createProdutoUpdated().getQtdDisponivel())
                .fornecedor(ProdutoCreator.createProdutoUpdated().getFornecedor())
                .build();

    }

}
