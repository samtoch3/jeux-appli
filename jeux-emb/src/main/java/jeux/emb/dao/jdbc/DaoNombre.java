package jeux.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import jeux.emb.dao.IDaoNombre;
import jeux.emb.data.Nombre;
import jfox.jdbc.UtilJdbc;


@Component
public class DaoNombre implements IDaoNombre {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	
	
	// Actions
	
	@Override
	public void inserer( Nombre nombre ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO nombre ( ValeurMaxi, NbEssaisMaxi, NbEssaisRestants, NombreMystere, BorneInf, BorneSup, FlagPartieGagnee, IdJoueur ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement( sql );
			stmt.setInt(	1, nombre.getValeurMaxi() );
			stmt.setInt(	2, nombre.getNbEssaisMaxi() );
			stmt.setInt(	3, nombre.getNbEssaisRestants() );
			stmt.setInt(	4, nombre.getNombreMystere() );
			stmt.setInt(	5, nombre.getBorneInf() );
			stmt.setInt(	6, nombre.getBorneSup() );
			stmt.setBoolean(7, nombre.isFlagPartieGagnee() );
			stmt.setString(	8, nombre.getIdJoueur() );
			stmt.executeUpdate();
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	@Override
	public void modifier( Nombre nombre ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE nombre SET ValeurMaxi = ?, NbEssaisMaxi = ?, NbEssaisRestants = ?, NombreMystere = ?, BorneInf = ?, BorneSup = ?, FlagPartieGagnee = ? WHERE IdJoueur =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setInt(	1, nombre.getValeurMaxi() );
			stmt.setInt(	2, nombre.getNbEssaisMaxi() );
			stmt.setInt(	3, nombre.getNbEssaisRestants() );
			stmt.setInt(	4, nombre.getNombreMystere() );
			stmt.setInt(	5, nombre.getBorneInf() );
			stmt.setInt(	6, nombre.getBorneSup() );
			stmt.setBoolean(7, nombre.isFlagPartieGagnee() );
			stmt.setString(	8, nombre.getIdJoueur() );
			stmt.executeUpdate();
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
		
	}
	
	
	@Override
	public Nombre retrouver( String idJoueur ) {
		
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
				cn = dataSource.getConnection();
				sql = "SELECT * FROM nombre WHERE IdJoueur = ?";
				stmt = cn.prepareStatement( sql );
				stmt.setString( 1, idJoueur );
				rs = stmt.executeQuery();

				if ( rs.next() ) {
					return construireDataNombre(rs);
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
	
	private Nombre construireDataNombre( ResultSet rs ) throws SQLException {
		Nombre nombre = new Nombre();
		nombre.setIdJoueur( 		rs.getString( "IdJoueur"	) );
		nombre.setValeurMaxi( 		rs.getInt( "ValeurMaxi"	) );
		nombre.setNbEssaisMaxi(		rs.getInt( "NbEssaisMaxi"	) );
		nombre.setNbEssaisRestants(	rs.getInt( "NbEssaisRestants" ) );
		nombre.setBorneInf( 		rs.getInt( "BorneInf" ) );
		nombre.setBorneSup( 		rs.getInt( "BorneSup" ) );
		nombre.setFlagPartieGagnee(	rs.getBoolean( "FlagPartieGagnee" ) );
		nombre.setNombreMystere( 	rs.getInt( "NombreMystere" ) );
		return nombre;
	}

}
