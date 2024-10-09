package test.bo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNiveau;

    private String nomNiveau;
    private String allias;
    @OneToMany(mappedBy = "niveau")
    private List<Etudiant> etudiants ;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idFilliere")
    private Filliere filliere;
    @JsonIgnore
    @OneToMany(mappedBy = "niveau")
    private List<Module> modules;

    @OneToMany(mappedBy = "niveau")
    @JsonIgnore
    private List<Examen> examenList;
    public List<Examen> getExamenList() {
        return examenList;
    }

    public void setExamenList(List<Examen> examenList) {
        this.examenList = examenList;
    }

    public String getNomNiveau() {
        return nomNiveau;
    }

    public void setNomNiveau(String nomNiveau) {
        this.nomNiveau = nomNiveau;
    }

    public String getAllias() {
        return allias;
    }

    public void setAllias(String allias) {
        this.allias = allias;
    }

    public void setIdNiveau(Long idNiveau) {
        this.idNiveau = idNiveau;
    }

    public Long getIdNiveau() {
        return idNiveau;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Filliere getFilliere() {
        return filliere;
    }

    public void setFilliere(Filliere filliere) {
        this.filliere = filliere;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Niveau() {
        etudiants = new ArrayList<>();
        modules = new ArrayList<>();
        examenList = new ArrayList<>();
    }

    public Niveau(String nomNiveau, String allias) {
        this.nomNiveau = nomNiveau;
        this.allias = allias;
    }
}
