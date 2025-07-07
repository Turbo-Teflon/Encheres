package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

@Service
@Profile("prod")
public class CategorieServiceImpl implements CategorieService {
	private CategorieDAO categorieDAO;
	
	public CategorieServiceImpl (CategorieDAO categorieDAO) {
		this.categorieDAO=categorieDAO;
	}
	
	@Override
	public List<Categorie> selectAll() {
		return categorieDAO.selectAll();
		
	}

	@Override
	public Categorie selectById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
