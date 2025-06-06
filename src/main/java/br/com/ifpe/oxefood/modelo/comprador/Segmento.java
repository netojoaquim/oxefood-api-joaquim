package br.com.ifpe.oxefood.modelo.comprador;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table (name = "Segmento")
@SQLRestriction("habilitado=true")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Segmento extends EntidadeAuditavel {

    @Column
    private String descricao;
}
