package test.bo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idElement;
    private String nom;

    public String getNom() {
        return nom;
    }
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idModule")
    private Module module;

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setIdElement(Long idElement) {
        this.idElement = idElement;
    }

    public Long getIdElement() {
        return idElement;
    }

    public Element() {
    }

    public Element(String nom, Module module, Enseignant enseignant) {
        this.nom = nom;
        this.module = module;
        this.enseignant = enseignant;
    }
}
