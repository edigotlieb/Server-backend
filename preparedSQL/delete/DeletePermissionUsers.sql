DELETE FROM USER_PERMISSIONS
WHERE PERMISSIONGROUP_ID = (SELECT PERMISSIONGROUP_ID FROM PERMISSION_GROUPS WHERE PERMISSION_GROUPS.PERMISSION_NAME = ?)