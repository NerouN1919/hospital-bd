CREATE OR REPLACE PROCEDURE get_user(IN _username VARCHAR, INOUT _password VARCHAR, INOUT _rolename VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
SELECT Users.Password, Roles.RoleName INTO _password, _rolename
FROM Users
         INNER JOIN Roles ON Users.RoleID = Roles.RoleID
WHERE Users.UserName = _username;
END; $$
