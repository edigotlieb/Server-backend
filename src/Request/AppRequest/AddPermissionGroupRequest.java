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

public class AddPermissionGroupRequest extends AppRequest {

	private final String permissionGroupName;
        private final String description;
        
	public AddPermissionGroupRequest(Credentials creds, String permissionGroupName,String description) {
            super(creds);
            this.permissionGroupName = permissionGroupName;
            this.description = description;
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
		if (rset.next() || permissionGroupName.equals(Credentials.anonymous)) {
			throw new ValidationException(5);                                
		}                
		if (!this.creds.isAppSuperAdmin()) {
                        throw new ValidationException(6);			
		}
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.ADD_PERMISSIONGROUP;
	}

    @Override
    protected ResultSet performRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
