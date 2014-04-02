/**
 * FILE : PreparedStatementStringas.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package SQL.PreparedStatements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreparedStatementStrings {

	private static final HashMap<String, String> fileNames = new HashMap<>();

	static {
		/*
		 * params: Username
		 * cols: USER_ID, USERNAME, PASSWORD (hashed), Name, EMAIL, DATE_REGISTERED, YEAR, DISPLAY_NAME, ROOM_NUM
		 * descriptions: Gives the information of a given user by username
		 */
		fileNames.put("getAllUserInfoByUsername", "select/getAllUserInfoByUsername.sql");
		/*
		 * params: App Name
		 * cols: APP_ID, APP_NAME, APP_KEY
		 * descriptions: Gives the information of a given app by name
		 */
		fileNames.put("getAllAppInfoByName", "select/getAllAppInfoByName.sql");
		/*
		 * params: App Key
		 * cols: APP_ID, APP_NAME, APP_KEY
		 * descriptions: Gives the information of a given app by key
		 */
		fileNames.put("getAllAppInfoByKey", "select/getAllAppInfoByKey.sql");
		/*
		 * params: Username
		 * cols: USER_ID, USERNAME, PASSWORD (hashed), Name, EMAIL, DATE_REGISTERED, YEAR, DISPLAY_NAME, ROOM_NUM, PERMISSIONGROUP_IF, PERMISSION_NAME, PERMISSIONGROUP_DESCTIPTION, PERMISION_GROUP_ADMIN
		 * descriptions: returns a set of rows, each describes the user info and the info of one of the
		 * permission groups he is in.
		 */
		fileNames.put("getUserPermissionGroups", "select/getUserPermissionGroups.sql");
		/*
		 * params: Username, App Name
		 * cols: TABLE_NAME, PERMISSION_TYPE
		 * descriptions: returns all DT permissions a user has in a given app
		 */
		fileNames.put("getUserPermissionsToTables", "select/getUserPermissionsToTables.sql");
		/*
		 * params: Username, App Name, Table name
		 * cols: PERMISSION_TYPE
		 * descriptions: returns all DT permissions a user has in a given app to
		 * a given table
		 */
		fileNames.put("getUserTablePermission", "select/getUserTablePermission.sql");
		/*
		 * params: Table name
		 * cols: TABLE_NAME, APP_NAME, APP_KEY
		 * descriptions: returns the app a table belongs to
		 */
		fileNames.put("getTableInfoByName", "select/getTableInfoByName.sql");
		/*
		 * params: Permission group name
		 * cols: PERMISSIONGROUP_IF, PERMISSION_NAME, PERMISSIONGROUP_DESCTIPTION, PERMISION_GROUP_ADMIN, USERNAME
		 * descriptions: returns the permissiongroup info including the username of the permissiongroup admin
		 */
		fileNames.put("getPermissionGroupInfoByName", "select/getPermissionGroupInfoByName.sql");
	}
	private final HashMap<String, String> PreparedSql = new HashMap<>();
	private static PreparedStatementStrings instance = null;

	public static PreparedStatementStrings getInstance() {
		if (instance != null) {
			return instance;
		} else {
			return (instance = new PreparedStatementStrings());
		}
	}

	private PreparedStatementStrings() {
		readFiles();
	}

	private void readFiles() {
		for (String key : fileNames.keySet()) {
			try {
				PreparedSql.put(key, new String(Files.readAllBytes(Paths.get("preparedSQL/" + fileNames.get(key)))));
			} catch (IOException ex) {
				Logger.getLogger(PreparedStatementStrings.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public String getSQL(String key) {
		return this.PreparedSql.get(key);
	}
}
