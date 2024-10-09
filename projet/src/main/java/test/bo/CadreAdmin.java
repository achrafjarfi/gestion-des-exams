package test.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "CadreAdminId")
public class CadreAdmin extends User {
    private String grade;

    @OneToMany(mappedBy = "controller")
    @JsonIgnore
    private List<Examen> examen;

    public List<Examen> getExamen() {
        return examen;
    }

    public void setExamen(List<Examen> examen) {
        this.examen = examen;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public CadreAdmin() {
        examen = new ArrayList<>();
    }
}
