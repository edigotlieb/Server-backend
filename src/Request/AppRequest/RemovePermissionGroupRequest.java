/**
 * FILE : SignInRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.PreparedStatements.StatementPreparer;
import SQL.SqlExecutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemovePermissionGroupRequest extends AppRequest {

	private final String permissionGroupName;

	public RemovePermissionGroupRequest(Credentials creds, String permissionGroupName) {
		super(creds);
		this.permissionGroupName = permissionGroupName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException, ValidationException {
		final String permissionGroupNameLocal = this.permissionGroupName;
		ResultSet rset = sqlExc.executePreparedStatement("getPermissionGroupInfoByName", new StatementPreparer() {
			@Override
			public void prepareStatement(PreparedStatement ps) throws SQLException {
				ps.setString(1, permissionGroupNameLocal);
			}
		});
		if (!rset.next()) {
                    throw new ValidationException(9);                    
		}
		String username = rset.getString("USERNAME");
		if (!this.creds.getUsername().equals(username)) {
                    throw new ValidationException(6);			
		}

		if (!this.creds.isAppSuperAdmin()) {
                    throw new ValidationException(6);			
		}
                
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.REMOVE_PERMISSIONGROUP;
	}

    @Override
    protected ResultSet performRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
