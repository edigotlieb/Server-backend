DELETE FROM TEMP_KEYS WHERE UNIX_TIMESTAMP(CURRENT_TIMESTAMP()) - UNIX_TIMESTAMP(TEMP_KEYS.CREATION_DATE) > ?