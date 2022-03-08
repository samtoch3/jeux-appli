package jeux.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import jeux.emb.dao.IDaoPendu;
import jeux.emb.data.Pendu;
import jfox.jdbc.UtilJdbc;


@Component
public class DaoPendu implements IDaoPendu {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	
	
	// Actions
	
	@Override
	public void inserer( Pendu pendu ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO pendu ( FlagModeFacile, NbErreursMaxi, NbErreursRestantes, MotMystere, Resultat, LettresJouees, IdJoueur ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement( sql );
			stmt.setBoolean(1, pendu.isFlagModeFacile() );
			stmt.setInt(	2, pendu.getNbErreursMaxi() );
			stmt.setInt(	3, pendu.getNbErreursRestantes() );
			stmt.setString(	4, pendu.getMotMystere() );
			stmt.setString(	5, pendu.getResultat() );
			stmt.setString(	6, pendu.getLettresJouees() );
			stmt.setString(	7, pendu.getIdJoueur() );
			stmt.executeUpdate();
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	@Override
	public void modifier( Pendu pendu ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE pendu SET FlagModeFacile = ?, NbErreursMaxi = ?, NbErreursRestantes = ?, MotMystere = ?, Resultat = ?, LettresJouees = ? WHERE IdJoueur =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setBoolean(1, pendu.isFlagModeFacile() );
			stmt.setInt(	2, pendu.getNbErreursMaxi() );
			stmt.setInt(	3, pendu.getNbErreursRestantes() );
			stmt.setString(	4, pendu.getMotMystere().toString() );
			stmt.setString(	5, pendu.getResultat().toString() );
			stmt.setString(	6, pendu.getLettresJouees() );
			stmt.setString(	7, pendu.getIdJoueur() );
			stmt.executeUpdate();
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
		
	}
	
	
	@Override
	public Pendu retrouver( String idJoueur ) {
		
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM pendu WHERE IdJoueur = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString( 1, idJoueur );
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireDataPendu(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Pendu construireDataPendu( ResultSet rs ) throws SQLException {
		Pendu pendu = new Pendu();
		pendu.setIdJoueur( 			rs.getString( "IdJoueur" ) );
		pendu.setFlagModeFacile( 	rs.getBoolean( "FlagModeFacile" ) );
		pendu.setNbErreursMaxi( 	rs.getInt( "NbErreursMaxi" ) );
		pendu.setNbErreursRestantes(rs.getInt( "NbErreursRestantes" ) );
		pendu.setMotMystere( 		rs.getString( "MotMystere" ) );
		pendu.setResultat( 			rs.getString( "Resultat" ) );
		pendu.setLettresJouees(		rs.getString( "LettresJouees" ) );
		return pendu;
	}

}
