UPDATE PERMISSION_GROUPS SET PERMISSION_GROUP_ADMIN = (SELECT USER_ID FROM USERS WHERE USER_NAME = ?) WHERE PERMISSION_NAME = ?