package domain.entities.usuarios;

import db.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Getter @Setter
public class Usuario extends EntidadPersistente {
    @Column(name = "nombre_de_usuario", unique = true)
    private String nombreDeUsuario;

    @Column
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    @Column
    private Rol rol;

    public Usuario() {

    }

    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public boolean tienePermiso(Rol rol) {
        return this.rol.equals(rol);
    }
}