package api.logap.util;

import api.logap.requests.ProdutoPostRequestBody;

public class ProdutoPostRequestBodyCreator {
    public static ProdutoPostRequestBody createProdutoPostRequestBody(){
        return ProdutoPostRequestBody.builder()
                .nome(ProdutoCreator.createProdutoToBeSaved().getNome())
                .valorCompra(ProdutoCreator.createProdutoToBeSaved().getValorCompra())
                .valorVenda(ProdutoCreator.createProdutoToBeSaved().getValorVenda())
                .categoria(ProdutoCreator.createProdutoToBeSaved().getCategoria())
                .qtdDisponivel(ProdutoCreator.createProdutoToBeSaved().getQtdDisponivel())
                .fornecedor(ProdutoCreator.createProdutoToBeSaved().getFornecedor())
                .build();

    }

}
