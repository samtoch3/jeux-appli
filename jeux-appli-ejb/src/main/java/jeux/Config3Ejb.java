package jeux;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import jeux.commun.service.IServiceNombre;
import jeux.commun.service.IServicePendu;

@ComponentScan(basePackages = { "jeux.javafx.view", "jeux.javafx.model.standard", }, lazyInit = true)
public class Config3Ejb {
	@Bean
	public InitialContext initialContext() throws NamingException {
		return new InitialContext();
	}

	@Bean
	public IServiceNombre serviceNombre(InitialContext ic) throws NamingException {
		return (IServiceNombre) ic.lookup("jeux-ear/jeux-ejb/ServiceNombre!jeux.commun.service.IServiceNombre");
	}
	
	@Bean
	public IServicePendu servicePendu(InitialContext ic) throws NamingException{
		return (IServicePendu) ic.lookup("jeux-ear/jeux-ejb/ServicePendu!jeux.commun.service.IServicePendu");
	}

}
