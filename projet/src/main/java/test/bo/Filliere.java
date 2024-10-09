package test.bo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Filliere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFilliere;

    private String nomFilliere;
    private String allias;
    @JsonManagedReference
    @OneToMany(mappedBy = "filliere")
    private List<Niveau> niveauList;
    @ManyToMany(mappedBy = "filieres")
    private List<Enseignant> enseignants;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idDepartement")
    private Departement departement;

    @OneToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant chefFiliere;
    public List<Niveau> getNiveauList() {
        return niveauList;
    }

    public void setNiveauList(List<Niveau> niveauList) {
        this.niveauList = niveauList;
    }

    public String getNomFilliere() {
        return nomFilliere;
    }

    public void setNomFilliere(String nomFilliere) {
        this.nomFilliere = nomFilliere;
    }

    public String getAllias() {
        return allias;
    }

    public void setAllias(String allias) {
        this.allias = allias;
    }

    public void setIdFilliere(Long idFilliere) {
        this.idFilliere = idFilliere;
    }

    public Long getIdFilliere() {
        return idFilliere;
    }

    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Enseignant getChefFiliere() {
        return chefFiliere;
    }

    public void setChefFiliere(Enseignant chefFiliere) {
        this.chefFiliere = chefFiliere;
    }

    public Filliere() {
        niveauList = new ArrayList<>();
        enseignants = new ArrayList<>();
    }

    public Filliere(String nomFilliere, String allias, Enseignant chefFiliere) {
        this.nomFilliere = nomFilliere;
        this.allias = allias;
        this.chefFiliere = chefFiliere;
    }
}
