package jeux.javafx.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import jeux.commun.exception.ExceptionAnomaly;
import jeux.commun.exception.ExceptionPermission;
import jeux.commun.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.ManagerGuiAbstract;
import jfox.javafx.view.View;
import jfox.localization.BundleMessages;


@Component
public class ManagerGui extends ManagerGuiAbstract {
	
	//Champs
	private static final Logger logger = Logger.getLogger(ManagerGui.class.getName() );
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		addExceptionAnomaly( ExceptionAnomaly.class );
		addExceptionPermission( ExceptionPermission.class );
		addExceptionValidation( ExceptionValidation.class );
	}
	
	
	// Actions

	@Override
	public void configureStage()  {
		
		// Choisit la vue à afficher
		showView( EnumView.Menu );

	}


	@Override
	public Scene createScene( View view ) {
		Scene scene = new Scene( view.getRoot() );
		return scene;
	}
	
	
	@Override
	public void showDialogError( Throwable e ) {

		if ( e != null ) {
			e = UtilFX.unwrapException(e);
			if( !(e instanceof ExceptionAnomaly) && !(e instanceof ExceptionValidation)) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
			
		}
		
		showDialogError(  BundleMessages.getString( "error.failure" ) );
	}
	
	
}