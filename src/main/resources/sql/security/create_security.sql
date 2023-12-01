CREATE TABLE Roles (
                       RoleID serial PRIMARY KEY,
                       RoleName VARCHAR (50) UNIQUE NOT NULL
);

CREATE TABLE Users (
                       UserID serial PRIMARY KEY,
                       UserName VARCHAR (50) UNIQUE NOT NULL,
                       Password VARCHAR (50) NOT NULL,
                       RoleID INTEGER,
                       FOREIGN KEY (RoleID) REFERENCES Roles (RoleID)
);
