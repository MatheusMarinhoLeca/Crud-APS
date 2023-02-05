package api.logap.util;

import api.logap.domain.Produto;

public class ProdutoCreator {
    public static Produto createProdutoToBeSaved(){
        return Produto.builder()
                .nome("Caneta Azul")
                .valorCompra("R$ 0,60")
                .valorVenda("R$ 1,00")
                .qtdDisponivel("0")
                .categoria("Material escolar")
                .fornecedor("Blue Pen")
                .build();

    }
    public static Produto createProdutoValid(){
        return Produto.builder()
                .id(1L)
                .nome("banana")
                .valorCompra("R$ 0,60")
                .valorVenda("R$ 1,00")
                .qtdDisponivel("0")
                .categoria("Alimento")
                .fornecedor("Seasa")
                .build();

    }
    public static Produto createProdutoUpdated(){
        return Produto.builder()
                .id(1L)
                .nome("maçã")
                .valorCompra("R$ 0,60")
                .valorVenda("R$ 1,00")
                .qtdDisponivel("0")
                .categoria("Alimento")
                .fornecedor("Seasa")
                .build();

    }
}
