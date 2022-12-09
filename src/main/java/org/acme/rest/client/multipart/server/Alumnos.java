package org.acme.rest.client.multipart.server;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


public class Alumnos {



    @Entity
    @Table(name = "Alumnos")
    @XmlRootElement
    @NamedQueries({
            @NamedQuery(name = "Estudiantes.findByCarnet", query = "SELECT e FROM Estudiantes e WHERE e.carnet = :carnet"),
            @NamedQuery(name = "Alumnos.findAll", query = "SELECT e FROM Estudiantes e"),
            @NamedQuery(name = "Alumnos.findByNombres", query = "SELECT e FROM Estudiantes e WHERE e.nombres = :nombres"),
            @NamedQuery(name = "Alumnos.findByApellidos", query = "SELECT e FROM Estudiantes e WHERE e.apellidos = :apellidos"),
            @NamedQuery(name = "Alumnos.findByEmail", query = "SELECT e FROM Estudiantes e WHERE e.email = :email"),
            @NamedQuery(name = "Alumnos.findByFoto", query = "SELECT e FROM Estudiantes e WHERE e.foto = :foto")})
    public class Estu implements Serializable {

        private static final long serialVersionUID = 1L;
        @Id

        @Basic(optional = false)
        @Column(name = "Carnet")
        private String carnet;

        @Basic(optional = false)
        @Column(name = "Nombres")
        private String nombres;
        @Basic(optional = false)
        @Column(name = "Apellidos")
        private String apellidos;
        @Basic(optional = false)
        @Column(name = "Email")
        private String email;
        @Basic(optional = false)
        @Column(name = "Foto")
        private String foto;
    }

   
}
