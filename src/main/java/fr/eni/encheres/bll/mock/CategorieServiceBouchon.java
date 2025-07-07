package fr.eni.encheres.bll.mock;



import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Categorie;


@Service("categorieServiceBouchon")
@Profile("dev")
public class CategorieServiceBouchon implements CategorieService {

	@Override
	public List<Categorie> selectAll() {
	    return List.of(
	        new Categorie(1, "Informatiques-jeux vidéos"),
	        new Categorie(2, "Mobiliers"),
	        new Categorie(3, "Voitures")
	    );
	}

    @Override
    public Categorie selectById(long id) {
        return selectAll().stream()
            .filter(c -> c.getIdCategorie() == id)
            .findFirst()
            .orElse(null);
    }
}

