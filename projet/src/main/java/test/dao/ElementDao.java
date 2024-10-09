package test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.bo.Element;

import java.util.List;
import java.util.Optional;

public interface ElementDao extends JpaRepository<Element,Long> {
    Optional<Element> findElementByNom(String nom);
    Optional<Element> findElementByIdElement(Long id);

    void deleteElementByNom(String nom);
    Element getElementByIdElement(Long id);
    void deleteElementByIdElement(Long aLong);

    @Query("SELECT e FROM Element e WHERE e.module.idModule = :moduleId")
    List<Element> getElementsByModule(@Param("moduleId") Long moduleId);
}
