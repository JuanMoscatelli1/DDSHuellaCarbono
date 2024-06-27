package domain.entities.actores.organizaciones;

import db.EntidadPersistente;
import domain.entities.actores.miembros.Miembro;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sector")
public class Sector extends EntidadPersistente {
    @Column
    private String nombre;

    @Getter
    @ManyToOne
    private Organizacion organizacion;

    @Getter
    @ManyToMany
    private List<Miembro> miembros;

    public Sector() {
        this.miembros = new ArrayList<>();
    }

    public Sector(String nombre, Organizacion organizacion) {
        this.nombre = nombre;
        this.organizacion = organizacion;
        organizacion.agregarSector(this);
        this.miembros = new ArrayList<>();
    }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public int cantidadDeMiembros() {
        return miembros.size();
    }

    public boolean tieneAlMiembro(Miembro miembro) {
        return miembros.contains(miembro);
    }

    public SectorDTO convertirADTO(){
        return new SectorDTO(this);
    }

    public class SectorDTO {
        @Getter
        private int id;
        @Getter
        private String nombre;
        private List<Miembro> miembros;
        private Organizacion organizacion;

        public SectorDTO(Sector sector) {
            id = sector.id;
            nombre = sector.nombre;
            miembros = sector.miembros;
            organizacion = sector.organizacion;
        }

        public List<Miembro.MiembroDTO> getMiembros() {
            return miembros.stream().map(Miembro::convertirADTO).collect(Collectors.toList());
        }

        public int getOrganizacionId() {
            return organizacion.getId();
        }

        public Integer getCantidadDeMiembros(){
            return miembros.size();
        }
    }
}
