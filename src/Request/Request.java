package Request;

import Request.Exceptions.ExecutionException;
import Request.Exceptions.ValidationException;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import SQL.Utilities.ExistenceValidator;
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

	public final ResultSet execute() throws SQLException, ExecutionException {
		return performRequest();
	}

	protected abstract ResultSet performRequest() throws SQLException, ExecutionException;

	// abstract public Credentials getCreds();
	abstract public TYPE getType();

	public final boolean Validate(SqlExecutor sqlExc, String challenge) throws SQLException, ValidationException {
		ResultSet rset;

		String app_key = ExistenceValidator.appByName(sqlExc, this.creds.getAppName());
		if (app_key.length() == 0) {
			throw new ValidationException(1);
		}
		if (!this.creds.getHashedAppKey().equals(Hashing.MD5Hash(app_key + challenge))) {
			throw new ValidationException(2);
		}

		final String username = this.creds.getUsername();
		rset = sqlExc.executePreparedStatement("getUserPermissionGroups", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, username);
			}
		});
		if (!rset.next()) {
			throw new ValidationException(3);
		}
		String hashed_pass = rset.getString("PASSWORD");
		if (!this.creds.getHashedPassword().equals(Hashing.MD5Hash(hashed_pass + challenge))) {
			throw new ValidationException(4);
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

		try {
			return CheckPermissions(sqlExc);
		} catch (ValidationException ex) {
			if (ex.getErrorCode() != 6) {
				throw ex;
			}
			return this.creds.isSuperAdmin();
		}

	}

	protected abstract boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException;
}
