package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

@Service
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
	public Categorie selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
