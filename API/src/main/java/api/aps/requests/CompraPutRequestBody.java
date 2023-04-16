package api.aps.requests;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompraPutRequestBody {
    private Long id;
    @NotEmpty(message = "O valor da compra não pode ser vazio")
    private String valorTotal;
    @NotEmpty(message = "A compra deve ter algum produto")
    private String quantidade;

    @NotEmpty(message = "A compra deve ter produtos")
    @ElementCollection
    private List<Long> produtos;
}
