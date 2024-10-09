package test.bo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "EnseignantId")
public class Enseignant extends User{
    private String specialite;
    @JsonIgnore
    @ManyToMany(mappedBy = "enseignants")
    private List<GroupeEnseignant> groupeEnseignants;



    @ManyToMany
    @JoinTable(
            name = "enseignant_filiere",
            joinColumns = @JoinColumn(name = "EnseignantId"),
            inverseJoinColumns = @JoinColumn(name = "idFilliere")
    )
    private List<Filliere> filieres;
    @OneToMany(mappedBy = "enseignant")
    @JsonManagedReference
    private List<Module> modules;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    @JsonIgnore
    private Departement departement;
    @JsonIgnore
    @OneToMany(mappedBy = "enseignant")
    private List<Element> elementList;

    @ManyToOne
    @JoinColumn(name = "idSalle")
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "idExamen")
    @JsonIgnore
    private Examen examen;

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
    }

    public List<Filliere> getFilieres() {
        return filieres;
    }

    public void setFilieres(List<Filliere> filieres) {
        this.filieres = filieres;
    }

    public List<GroupeEnseignant> getGroupeEnseignants() {
        return groupeEnseignants;
    }

    public void setGroupeEnseignants(List<GroupeEnseignant> groupeEnseignants) {
        this.groupeEnseignants = groupeEnseignants;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Enseignant() {
        groupeEnseignants = new ArrayList<>();
        filieres = new ArrayList<>();
        elementList = new ArrayList<>();
    }
}
