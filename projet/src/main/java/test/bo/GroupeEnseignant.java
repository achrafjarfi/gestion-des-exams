package test.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupeEnseignant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupeEnseignant;
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }
    @ManyToMany
    @JoinTable(name = "groupeensaignant_ensaignant",
            joinColumns = @JoinColumn(name = "idGroupeEnseignant"),
            inverseJoinColumns = @JoinColumn(name = "idEnseignant"))
    private List<Enseignant> enseignants ;

    public void setIdGroupeEnseignant(Long idGroupeEnseignant) {
        this.idGroupeEnseignant = idGroupeEnseignant;
    }

    public Long getIdGroupeEnseignant() {
        return idGroupeEnseignant;
    }

    public GroupeEnseignant() {
        enseignants = new ArrayList<>();
    }

    public GroupeEnseignant(String nom, List<Enseignant> enseignants) {
        this.nom = nom;
        this.enseignants = enseignants;
    }
}
