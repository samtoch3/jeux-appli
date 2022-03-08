package jeux;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jfox.javafx.view.ManagerGuiAbstract;

public class AppliJeux1 extends Application {

	// Titre de la fenêtre
	private final String TITRE = "Jeux";

	// Champs

	// private static final Logger logger = Logger.getLogger(
	// AppliJeux1.class.getName() );
	private static final Logger logger = getLogger();

	private AnnotationConfigApplicationContext context;

	// Actions

	@Override
	public final void start(Stage stage) {

		try {

			// Context
			context = new AnnotationConfigApplicationContext();
			context.register(Config2DaoJdbc.class);
			context.refresh();

			// ManagerGui
			ManagerGuiAbstract managerGui = context.getBean(ManagerGuiAbstract.class);
			managerGui.setFactoryController(context::getBean);
			managerGui.setStage(stage);
			managerGui.configureStage();

			// Affiche le stage
			stage.setTitle(TITRE);
			// stage = null;
			logger.log(Level.CONFIG, "Initialisation effectuée");
			stage.show();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Echec de demarrage", e);

			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Impossible de démarrer l'application.");
			alert.showAndWait();
			Platform.exit();
		}

	}

	@Override
	public final void stop() throws Exception {
		if (context != null) {
			context.close();
		}
	}

	// Classe interne Main

	public static class Main {
		public static void main(String[] args) {
			Application.launch(AppliJeux1.class, args);
			System.exit(0);
		}
	}

	private static Logger getLogger() {
		try {
			InputStream in = AppliJeux1.class.getResourceAsStream("/META-INF/logging.properties");
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Logger.getLogger(AppliJeux1.class.getName());
	}

}
