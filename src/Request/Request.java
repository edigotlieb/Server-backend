package Request;

import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import Utilities.Hashing;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Utilities;

/**
 *
 * @author talpiot
 */
public abstract class Request {

	protected final Credentials creds;

	public Request(Credentials creds) {
		this.creds = creds;
	}

	public enum TYPE {

		USER, APP, DTD
	}

	// abstract public Credentials getCreds();
	abstract public TYPE getType();

	public final boolean Validate(SqlExecutor sqlExc, String challenge) throws SQLException {
		final String appname = this.creds.getUsername();
		ResultSet rset = sqlExc.executePreparedStatement("getAllAppInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, appname);
			}
		});
		if (!rset.next()) {
			return false;
		}
		String app_key = rset.getString("APP_KEY");
		if (!this.creds.getHashedAppKey().equals(Hashing.MD5Hash(app_key + challenge))) {
			return false;
		}
		
		final String username = this.creds.getUsername();
		rset = sqlExc.executePreparedStatement("getUserPermissionGroups", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, username);
			}
		});
		if (!rset.next()) {
			return false;
		}
		String hashed_pass = rset.getString("PASSWORD");
		if (!this.creds.getHashedPassword().equals(Hashing.MD5Hash(hashed_pass + challenge))) {
			return false;
		}
		this.creds.setMoreInfo(rset.getString("NAME"),
				rset.getString("DISPLAY_NAME"),
				rset.getString("EMAIL"),
				rset.getInt("YEAR"),
				rset.getInt("ROOM_NUM"));
		List<String> permissions = new ArrayList<>();
		do {
			permissions.add(rset.getString("PERMISSION_NAME"));
		} while (!rset.next());
		this.creds.setPermissions(permissions);
		
		return CheckPermissions(sqlExc);
	}

	protected abstract boolean CheckPermissions(SqlExecutor sqlExc);
}
