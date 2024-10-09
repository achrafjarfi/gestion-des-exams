package test.bo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExamen;

    @NotNull(message = "L'heure debut est obligatoire")
    private double heureDebut;

    @NotNull(message = "L'heure fin est obligatoire")
    private double heureFin;

    @NotNull(message = "La date est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull(message = "semestre est obligatoire")
    private String semestre;

    @NotNull(message = "La session est obligatoire")
    private String session;

    @NotNull(message = "La duree prevue est obligatoire")
    private double dureeprevue;

    private String epreuve;
    private String rapport;
    private String pv;

    @OneToMany(mappedBy = "examen")
    private List<Enseignant> survellants;

    public List<Enseignant> getSurvellants() {
        return survellants;
    }

    public void setSurvellants(List<Enseignant> survellants) {
        this.survellants = survellants;
    }

    @ManyToOne
    @JoinColumn(name = "CadreAdminId")
    @JsonIgnore
    private CadreAdmin controller;
    @OneToMany(mappedBy = "examen")
    private List<Salle> salles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idNiveau")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "idModule")
    private Module module;

    public Long getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(Long idExamen) {
        this.idExamen = idExamen;
    }

    public double getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(double heureDebut) {
        this.heureDebut = heureDebut;
    }

    public double getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(double heureFin) {
        this.heureFin = heureFin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public double getDureeprevue() {
        return dureeprevue;
    }

    public void setDureeprevue(double dureeprevue) {
        this.dureeprevue = dureeprevue;
    }

    public String getEpreuve() {
        return epreuve;
    }

    public void setEpreuve(String epreuve) {
        this.epreuve = epreuve;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public CadreAdmin getController() {
        return controller;
    }

    public void setController(CadreAdmin controller) {
        this.controller = controller;
    }

    public List<Salle> getSalles() {
        return salles;
    }

    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Examen() {
        survellants=new ArrayList<>();
    }
}
