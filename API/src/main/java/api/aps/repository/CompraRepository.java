package api.aps.repository;

import api.aps.domain.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompraRepository extends JpaRepository<Compra,Long>, JpaSpecificationExecutor<Compra> {
}
