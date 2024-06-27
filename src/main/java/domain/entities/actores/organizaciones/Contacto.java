package domain.entities.actores.organizaciones;

import db.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "contacto")
public class Contacto extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "mail")
    private String mail;

    @Column(name = "codigo_pais")
    private String codigoPais;

    @Column(name = "numero_telefono")
    private String nroTelefono;

    public Contacto(String nombre, String apellido, String mail, String codigoPais, String nroTelefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.codigoPais = codigoPais;
        this.nroTelefono = nroTelefono;
    }

    public Contacto() {}

    public String whatsapp() {
        return "whatsapp:+" + codigoPais + "9" + nroTelefono;
    }

    // -------------------- DTO --------------

    public ContactoDTO convertirADTO(){
        return new ContactoDTO(this);
    }

    @Getter
    public class ContactoDTO{
        public String nombre;
        public String apellido;
        public String mail;
        public String codigoPais;
        public String nroTelefono;

        public ContactoDTO(Contacto contacto){
            nombre = contacto.nombre;
            apellido = contacto.apellido;
            mail = contacto.mail;
            codigoPais = contacto.codigoPais;
            nroTelefono = contacto.nroTelefono;
        }

        public String getNroTelefono() {
            return codigoPais + "-" + nroTelefono;
        }
    }
}
