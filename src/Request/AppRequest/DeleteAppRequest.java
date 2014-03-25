/**
 * FILE : SignInRequest.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request.AppRequest;

import Request.Credentials;
import Request.Exceptions.ValidationException;
import SQL.SqlExecutor;
import java.sql.SQLException;

public class DeleteAppRequest extends AppRequest {

        String appName;

	public DeleteAppRequest(Credentials creds,String appName) {
		super(creds);
                this.appName = appName;
	}

	@Override
	protected boolean CheckPermissions(SqlExecutor sqlExc) throws SQLException,ValidationException {
		if (!this.creds.isAppSuperAdmin()) {
			throw new ValidationException(6);			
		}
		return true;
	}

	@Override
	public APP_ACTION_TYPE getActionType() {
		return AppRequest.APP_ACTION_TYPE.DELETE_APP;
	}
}
