package jeux;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

import jfox.jdbc.DataSourceSingleConnection;

@ComponentScan(
		basePackages = {
				"jeux.javafx.view",
				"jeux.javafx.model.standard",
				"jeux.emb.dao.jdbc",
				"jeux.emb.service.standard",
		},
		lazyInit = true	)
public class Config2DaoJdbc {
	@Bean @Lazy
	public DataSource dataSource() {
		return new DataSourceSingleConnection("classpath:META-INF/jdbc.properties" );
	}
}
