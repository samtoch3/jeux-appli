package jeux;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
		basePackages = {
				"jeux.javafx.view",
				"jeux.javafx.model.autonome",
		},
		lazyInit = true	)
public class Config1ModelAutonome {
	
}
