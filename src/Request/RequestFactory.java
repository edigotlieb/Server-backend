/**
 * FILE : RequestFactory.java AUTHORS : Erez Gotlieb DESCRIPTION :
 */
package Request;

import Request.AppRequest.AddPermissionGroupForTableRequest;
import Request.AppRequest.AddPermissionGroupRequest;
import Request.AppRequest.AddTableRequest;
import Request.AppRequest.AppRequest;
import Request.AppRequest.AppRequest.APP_ACTION_TYPE;
import Request.AppRequest.Column;
import Request.AppRequest.CreateAppRequest;
import Request.AppRequest.DeleteAppRequest;
import Request.AppRequest.DropTableRequest;
import Request.AppRequest.GetAllAppsRequest;
import Request.AppRequest.GetTableInfoRequest;
import Request.AppRequest.GetTablesRequest;
import Request.AppRequest.Permission;
import Request.AppRequest.Permission.PERMISSION_TYPE;
import Request.AppRequest.RemovePermissionGroupForTableRequest;
import Request.AppRequest.RemovePermissionGroupRequest;
import Request.AppRequest.SetPermissionGroupAdminRequest;
import Request.DTDRequest.DTDDeleteRequest;
import Request.DTDRequest.DTDInsertRequest;
import Request.DTDRequest.DTDRequest;
import Request.DTDRequest.DTDRequest.DTD_ACTION_TYPE;
import Request.DTDRequest.DTDSelectRequest;
import Request.DTDRequest.DTDUpdateRequest;
import Request.UserRequest.UserAddPermissionGroupRequest;
import Request.UserRequest.UserDeleteUserRequest;
import Request.UserRequest.UserGetPermissionGroups;
import Request.UserRequest.UserRemovePermissionGroupRequest;
import Request.UserRequest.UserRequest;
import Request.UserRequest.UserRequest.USER_ACTION_TYPE;
import Request.UserRequest.UserSelectRequest;
import Request.UserRequest.UserSigninRequest;
import Request.UserRequest.UserSignupRequest;
import Request.UserRequest.UserUpdateInfoRequest;
import Request.UserRequest.UserUpdateUserPasswordRequest;
import Request.UserRequest.UsergetUsersWithPermissionGroup;
import SQL.DynamicStatements.SqlQueryGenerator;
import Statement.AndStatement;
import Statement.EmptyStatement;
import Statement.OrStatement;
import Statement.RelStatement;
import Statement.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.*;

public class RequestFactory {

	public static Request createRequestFromString(String source) throws JSONException {
		JSONObject masterObj = new JSONObject(source);
		JSONObject credsObj = masterObj.getJSONObject("RequesterCredentials");

		// create the request cradentials
		Credentials creds = new Credentials(credsObj.getString("username"), credsObj.getString("password"),
				credsObj.getString("appName"), credsObj.getString("appKey"));

		JSONObject requestInfo = masterObj.getJSONObject("RequestInfo");
		JSONObject requestData = masterObj.getJSONObject("RequestData");

		try {
			return buildRequeset(creds, requestInfo, requestData);
		} catch (IllegalArgumentException ex) {
			throw new JSONException("Illegal Argument Exception");
		}
	}

	private static Request buildRequeset(Credentials creds, JSONObject requestInfo, JSONObject requestData) {
		Request.TYPE requestType = Request.TYPE.valueOf(requestInfo.getString("requestType"));
		switch (requestType) {
			case APP: {
				APP_ACTION_TYPE actionType = APP_ACTION_TYPE.valueOf(requestInfo.getString("requestAction"));
				return buildAppRequest(creds, actionType, requestData);
			}
			case DTD: {
				DTD_ACTION_TYPE actionType = DTD_ACTION_TYPE.valueOf(requestInfo.getString("requestAction"));
				return buildDTDRequest(creds, actionType, requestData);
			}
			case USER: {
				USER_ACTION_TYPE actionType = USER_ACTION_TYPE.valueOf(requestInfo.getString("requestAction"));
				return buildUserRequest(creds, actionType, requestData);
			}
		}
		return null;
	}

	private static AppRequest buildAppRequest(Credentials creds, AppRequest.APP_ACTION_TYPE actionType, JSONObject requestData) {
		switch (actionType) {
			case ADD_PERMISSIONGROUP: {
				return new AddPermissionGroupRequest(creds, requestData.getString("permissionGroupName"), requestData.getString("description"));
			}
			case ADD_PERMISSION_GROUP_FOR_TABLE: {
				return new AddPermissionGroupForTableRequest(PERMISSION_TYPE.valueOf(requestData.getString("type")), requestData.getString("appName"), requestData.getString("to"), requestData.getString("permissionGroupName"), creds);
			}
			case ADD_TABLE: {
				String tableName = requestData.getString("tableName");
				List<Column> cols = processCols(requestData.getJSONArray("cols"));
				//List<Permission> permissions = processPermissions(requestData.getJSONArray("permissions"), creds, tableName);
				return new AddTableRequest(creds, tableName, cols, /*permissions,*/ requestData.getString("appName"));
			}
			case CREATE_APP: {
				return new CreateAppRequest(creds, requestData.getString("appName"), requestData.getString("appKey"));
			}
			case DELETE_APP: {
				return new DeleteAppRequest(creds, requestData.getString("appName"));
			}
			case DROP_TABLE: {
				return new DropTableRequest(creds, requestData.getString("tableName"), requestData.getString("appName"));
			}
			case GET_TABLE_INFO: {
				return new GetTableInfoRequest(creds, requestData.getString("tableName"), requestData.getString("appName"));
			}
			case GET_TABLES: {
				return new GetTablesRequest(creds, requestData.getString("appName"));
			}
			case REMOVE_PERMISSIONGROUP: {
				return new RemovePermissionGroupRequest(creds, requestData.getString("permissionGroupName"));
			}
			case REMOVE_PERMISSION_GROUP_FOR_TABLE: {
				return new RemovePermissionGroupForTableRequest(PERMISSION_TYPE.valueOf(requestData.getString("type")), requestData.getString("appName"), requestData.getString("from"), requestData.getString("permissionGroupName"), creds);
			}
			case SET_PERMISSIONGROUP_ADMIN: {
				String username = requestData.getString("username");
				String groupName = requestData.getString("permissionGroupName");
				return new SetPermissionGroupAdminRequest(username, groupName, creds);
			}
			case GET_ALL_APPS: {
				return new GetAllAppsRequest(creds);
			}
		}
		return null;
	}

	private static UserRequest buildUserRequest(Credentials creds, USER_ACTION_TYPE actionType, JSONObject requestData) {
		switch (actionType) {
			case SIGN_IN: {
				return new UserSigninRequest(creds);
			}
			case SIGN_UP: {
				String username = requestData.getString("username");
				String pass = requestData.getString("password");
				String name = requestData.getString("name");
				String disp_name = requestData.getString("displayName");
				String email = requestData.getString("email");
				int year = requestData.getInt("year");
				int room = requestData.getInt("room");
				return new UserSignupRequest(username, pass, name, disp_name, email, room, year, creds);
			}
			case UPDATE_INFO: {
				String userToChange = requestData.getString("username");
				String newName = requestData.getString("newName");
				String newDispName = requestData.getString("newDisplayName");
				String newEmail = requestData.getString("newEmail");
				int newYear = requestData.getInt("newYear");
				int newRoom = requestData.getInt("newRoom");
				return new UserUpdateInfoRequest(userToChange, newName, newDispName, newEmail, newYear, newRoom, creds);
			}
			case ADD_PERMISSION: {
				String username = requestData.getString("username");
				String groupName = requestData.getString("permissionGroupName");
				return new UserAddPermissionGroupRequest(username, groupName, creds);
			}
			case GET_GROUPS: {
				return new UserGetPermissionGroups(creds);
			}
			case SELECT: {
				return new UserSelectRequest(processStatement(requestData.getJSONObject("WHERE")), creds);
			}
			case UPDATE_PASSWORD: {
				String user = requestData.getString("username");
				String newPass = requestData.getString("newPassword");
				return new UserUpdateUserPasswordRequest(creds, user, newPass);
			}
			case REMOVE_PERMISSION: {
				String user = requestData.getString("username");
				String permissionGroupName = requestData.getString("permissionGroupName");
				return new UserRemovePermissionGroupRequest(user, permissionGroupName, creds);
			}
			case GET_USERS_WITH_GROUPS: {
				String groupName = requestData.getString("groupName");
				return new UsergetUsersWithPermissionGroup(groupName, creds);
			}
                        case DELETE_USER: {
                                return new UserDeleteUserRequest(creds,requestData.getString("userToDelete"));
                        }
		}
		return null;
	}

	private static DTDRequest buildDTDRequest(Credentials creds, DTD_ACTION_TYPE actionType, JSONObject requestData) {
		switch (actionType) {
			case DELETE: {
				String tableName = requestData.getString("from");
				Statement where = processStatement(requestData.getJSONObject("WHERE"));
				return new DTDDeleteRequest(tableName, where, creds);
			}
			case INSERT: {
				String tableName = requestData.getString("into");
				Map<String, String> data = processData(requestData.getJSONObject("data"));
				return new DTDInsertRequest(tableName, data, creds);
			}
			case SELECT: {
				String tableName = requestData.getString("from");
				Statement where = processStatement(requestData.getJSONObject("WHERE"));
                                
                                if(requestData.has("order")) {
                                    JSONObject order = requestData.getJSONObject("order");
                                    return new DTDSelectRequest(tableName, where,order.getString("by") , SqlQueryGenerator.ORDER_ORIENTATION.valueOf(order.getString("dir")), creds);
                                } else {
                                   return new DTDSelectRequest(tableName, where, creds);
                                }
                                
				
			}
			case UPDATE: {
				String tableName = requestData.getString("into");
				Statement where = processStatement(requestData.getJSONObject("WHERE"));
				Map<String, String> data = processData(requestData.getJSONObject("data"));
				return new DTDUpdateRequest(tableName, where, data, creds);
			}
		}
		return null;
	}

	private static Statement processStatement(JSONObject requestData) {
                if(requestData.length() == 0) {
                    return new EmptyStatement();
                }
		if (requestData.length() > 1) {
			throw new JSONException("Bad Format");
		}
		JSONObject termData;
		if (requestData.has("Term")) {
			termData = requestData.getJSONObject("Term");
			return new RelStatement(termData.getString("Field"), termData.getString("Value"), termData.getString("Op"));
		} else if (requestData.has("AND")) {
			termData = requestData.getJSONObject("AND");
			return new AndStatement(processStatement(termData.getJSONObject("firstStatement")), processStatement(termData.getJSONObject("secondStatement")));
		} else if (requestData.has("OR")) {
			termData = requestData.getJSONObject("OR");
			return new OrStatement(processStatement(termData.getJSONObject("firstStatement")), processStatement(termData.getJSONObject("secondStatement")));
		} else {
			throw new JSONException("Bad Format");
		}
	}

	private static Map<String, String> processData(JSONObject data) {
		HashMap<String, String> map = new HashMap<>();
		Iterator itr = data.keys();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			map.put(key, data.getString(key));
		}
		return map;
	}

	private static List<Column> processCols(JSONArray jsonArray) {
		ArrayList<Column> al = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject col = jsonArray.getJSONObject(i);
			String colName = (col.getString("colName"));
			Column.COL_TYPE type = Column.COL_TYPE.valueOf(col.getString("colType"));
			int size;
			size = col.has("size") ? (col.getInt("size")) : (-1);
			boolean autoInc = col.has("autoInc") ? (col.getBoolean("autoInc")) : (false);
			boolean isPrimary = col.has("isPrimary") ? (col.getBoolean("isPrimary")) : (false);
			al.add(new Column(colName, type, size, autoInc, isPrimary));
		}
		return al;
	}

	private static List<Permission> processPermissions(JSONArray jsonArray, Credentials creds, String tableName) {
		ArrayList<Permission> al = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject permission = jsonArray.getJSONObject(i);
			PERMISSION_TYPE type = PERMISSION_TYPE.valueOf(permission.getString("permissionType"));
			String groupName = permission.getString("permissionGroupName");
			al.add(new Permission(type, creds.getAppName(), tableName, groupName));
		}
		return al;
	}
}
