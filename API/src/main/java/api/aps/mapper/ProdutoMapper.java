package api.logap.mapper;

import api.logap.domain.Produto;
import api.logap.requests.ProdutoPostRequestBody;
import api.logap.requests.ProdutoPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ProdutoMapper {
    public static final ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
    public abstract Produto toProduto(ProdutoPostRequestBody produtoPostRequestBody);
    public abstract Produto toProduto(ProdutoPutRequestBody produtoPutRequestBody);

}
