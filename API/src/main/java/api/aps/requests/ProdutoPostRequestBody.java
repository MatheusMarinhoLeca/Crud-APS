package api.logap.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoPostRequestBody {
    @NotEmpty(message = "O nome do produto não pode ser vazio")
    private String nome;
    private String valorCompra;
    private String valorVenda;

    private String qtdDisponivel;
    @NotEmpty(message = "A categoria do produto não pode ser vazia")
    private String categoria;
    @NotEmpty(message = "O nome do fornecedor não pode ser vazio")
    private String fornecedor;
}
