package api.logap.queries;

import api.logap.domain.Produto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoComqtdDisponivelQuery implements Specification<Produto> {
    private Produto filter;

    public ProdutoComqtdDisponivelQuery(Produto filter) {
        super();
        this.filter = filter;
    }

    public Predicate toPredicate(Root<Produto> root, CriteriaQuery<?> cq,
                                 CriteriaBuilder cb) {

        Predicate p = cb.disjunction();

        if (filter.getQtdDisponivel()!= null) {
            p.getExpressions()
                    .add(cb.equal(root.get("qtdDisponivel"), filter.getQtdDisponivel()));
        }



        return p;
    }
}
