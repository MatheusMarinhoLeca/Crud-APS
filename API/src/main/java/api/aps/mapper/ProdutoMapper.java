package api.aps.mapper;

import api.aps.domain.Produto;
import api.aps.requests.ProdutoPostRequestBody;
import api.aps.requests.ProdutoPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ProdutoMapper {
    public static final ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
    public abstract Produto toProduto(ProdutoPostRequestBody produtoPostRequestBody);
    public abstract Produto toProduto(ProdutoPutRequestBody produtoPutRequestBody);

}
