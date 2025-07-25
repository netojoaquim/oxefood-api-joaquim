package br.com.ifpe.oxefood.util.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntidadeAuditavel extends EntidadeNegocio {

   @JsonIgnore
   @Version
   private Long versao;

   @JsonIgnore
   @CreatedDate
   private LocalDate dataCriacao;

   @JsonIgnore
   @LastModifiedDate
   private LocalDate dataUltimaModificacao;

   @CreatedBy
   @JoinColumn
   @ManyToOne
   private Usuario criadoPor; // obj do usuário que o criou

   @JoinColumn
   @LastModifiedBy
   @ManyToOne
   private Usuario ultimaModificacaoPor; // obj do usuário que fez a última alteração

}
