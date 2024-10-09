package test.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepartement;

    private String nom;
    private int codeDepartement;

    @OneToMany(mappedBy = "departement")
    @JsonManagedReference
    private List<Enseignant> enseignants = new ArrayList<>();

    @OneToMany(mappedBy = "departement")
    @JsonManagedReference
    private List<Filliere> fillieres = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "coordonateur_id")
    private Enseignant coordonateur;

    public Departement() {
        // Default constructor
    }

    public Departement(String nom, Enseignant coordonateur) {
        this.nom = nom;
        this.coordonateur = coordonateur;
    }

    public Long getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(Long idDepartement) {
        this.idDepartement = idDepartement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(int codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    public List<Filliere> getFillieres() {
        return fillieres;
    }

    public void setFillieres(List<Filliere> fillieres) {
        this.fillieres = fillieres;
    }

    public Enseignant getCoordonateur() {
        return coordonateur;
    }

    public void setCoordonateur(Enseignant coordonateur) {
        this.coordonateur = coordonateur;
    }
}
