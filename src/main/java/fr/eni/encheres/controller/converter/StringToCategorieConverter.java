package fr.eni.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie>{
	
	private CategorieDAO categorieDAO;

	/**
	 * @param categorieService
	 */
	public StringToCategorieConverter(CategorieDAO categorieDAO) {
		super();
		this.categorieDAO = categorieDAO;
	}



	@Override
	public Categorie convert(String source) {
		
		return this.categorieDAO.selectById(Long.parseLong(source));
	}

}
