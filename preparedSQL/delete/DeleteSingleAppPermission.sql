DELETE FROM APP_PERMISSIONS
WHERE TABLE_ID = (SELECT TABLE_ID FROM DYNAMIC_TABLES WHERE DYNAMIC_TABLES.TABLE_NAME = ?)
AND PERMISSIONGROUP_ID = (SELECT TABLE_ID FROM DYNAMIC_TABLES WHERE DYNAMIC_TABLES.TABLE_NAME = ?)
AND PERMISSION_TYPE = ?