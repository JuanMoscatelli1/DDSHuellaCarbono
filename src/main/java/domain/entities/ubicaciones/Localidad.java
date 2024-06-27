package domain.entities.ubicaciones;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
public class Localidad {
    @Id
    @Getter
    public int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "municipio_id", referencedColumnName = "id")
    @Getter @Setter
    private Municipio municipio;

    @Column(name = "nombre")
    @Getter
    public String nombre;

    @Column(name = "codigo_postal")
    public int codPostal;

    public Localidad() {

    }

    public Localidad(int id, String nombre, int codPostal) {
        this.id = id;
        this.nombre = nombre;
        this.codPostal = codPostal;
    }
}