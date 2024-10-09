package test.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSalle;
    private int capacite;
    private int numSalle;
    @OneToMany(mappedBy = "salle")
    private List<Etudiant> etudiants ;

    @ManyToOne
    @JoinColumn(name = "idExamen")
    @JsonIgnore
    private Examen examen;

    @OneToMany(mappedBy = "salle")
    @JsonIgnore
    private List<Enseignant> surveillants;

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNumSalle() {
        return numSalle;
    }

    public void setNumSalle(int numSalle) {
        this.numSalle = numSalle;
    }


    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    public Long getIdSalle() {
        return idSalle;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Salle() {
        etudiants = new ArrayList<>();
        surveillants = new ArrayList<>();
    }

    public List<Enseignant> getSurveillants() {
        return surveillants;
    }

    public void setSurveillants(List<Enseignant> surveillants) {
        this.surveillants = surveillants;
    }
}
