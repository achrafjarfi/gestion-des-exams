package test.bo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModule;

    private String nomModule;

    @OneToMany(mappedBy = "module")
    private List<Element> elements;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idNiveau")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "EnseignantId")
    @JsonIgnore
    private Enseignant enseignant;

    @JsonIgnore
    @OneToMany(mappedBy = "module")
    private List<Examen> examen;

    public List<Examen> getExamen() {
        return examen;
    }

    public void setExamen(List<Examen> examen) {
        this.examen = examen;
    }

    public String getNomModule() {
        return nomModule;
    }

    public void setNomModule(String nomModule) {
        this.nomModule = nomModule;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Long getIdModule() {
        return idModule;
    }

    public void setIdModule(Long idModule) {
        this.idModule = idModule;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Module() {
        elements = new ArrayList<>();
        examen = new ArrayList<>();
    }

    public Module(String nomModule, Niveau niveau) {
        this.nomModule = nomModule;
        this.niveau = niveau;
        this.elements = new ArrayList<>();
        this.examen = new ArrayList<>();
    }
}
