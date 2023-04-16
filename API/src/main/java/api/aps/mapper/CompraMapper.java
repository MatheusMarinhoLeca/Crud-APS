package api.aps.mapper;

import api.aps.domain.Compra;
import api.aps.requests.CompraPostRequestBody;
import api.aps.requests.CompraPutRequestBody;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CompraMapper {

    public static final CompraMapper INSTANCE = Mappers.getMapper(CompraMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantidade", expression = "java(compraPostRequestBody.getQuantidade())")
    public abstract Compra toCompra(CompraPostRequestBody compraPostRequestBody);

    public abstract Compra toCompra(CompraPutRequestBody compraPutRequestBody);
}
