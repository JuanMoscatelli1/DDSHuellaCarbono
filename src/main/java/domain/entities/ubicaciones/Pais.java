package domain.entities.ubicaciones;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
@Getter
@Setter
public class Pais {
    @Id
    public int id;

    @Column(name = "nombre")
    public String nombre;
}