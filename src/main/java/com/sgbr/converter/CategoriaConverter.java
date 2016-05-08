package com.sgbr.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sgbr.model.Categoria;
import com.sgbr.repository.Categorias;
import com.sgbr.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Perfil.class)
public class PerfilConverter implements Converter{
	
	
	private Perfis perfis;
	
	public CategoriaConverter(){
		perfis = CDIServiceLocator.getBean(Perfis.class);
	} 
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;
		
		if(value != null){
			Long id = new Long(value);
			retorno = perfis.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value != null){
			return ((Perfil)value).getId().toString();
		}
		return " ";
	}

	
}