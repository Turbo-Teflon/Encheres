package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EnchereDAO;

@Service
@Profile("prod")
public class EnchereServiceImpl implements EnchereService{
	private EnchereDAO enchereDAO;
	
	public EnchereServiceImpl(EnchereDAO enchereDAO) {
		this.enchereDAO=enchereDAO;
	}
	@Override
	public List<Enchere> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> selectByArticle(long idArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> selectByUtilisateur(long idUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Enchere enchere) {
		
	    enchereDAO.insert(enchere);
		
	}

}
