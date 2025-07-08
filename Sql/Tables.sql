USE BDD_Encheres;

IF OBJECT_ID('Encheres', 'U') IS NOT NULL DROP TABLE Encheres;
IF OBJECT_ID('Retraits', 'U') IS NOT NULL DROP TABLE Retraits;
IF OBJECT_ID('Articles', 'U') IS NOT NULL DROP TABLE Articles;
IF OBJECT_ID('Categories', 'U') IS NOT NULL DROP TABLE Categories;
IF OBJECT_ID('Utilisateurs', 'U') IS NOT NULL DROP TABLE Utilisateurs;

CREATE TABLE Utilisateurs (
    idUtilisateur INT PRIMARY KEY IDENTITY(1,1),
    pseudo VARCHAR(50) NOT NULL UNIQUE,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    telephone VARCHAR(20),
    rue VARCHAR(255),
    codePostal VARCHAR(10),
    ville VARCHAR(100),
    motDePasse VARCHAR(255) NOT NULL,
    credit INT DEFAULT 0,
    administrateur BIT DEFAULT 0
);

CREATE TABLE Roles (
	idUtilisateur INT PRIMARY KEY,
	role VARCHAR(20),
	FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur)
);

CREATE TABLE Categories (
    idCategorie INT PRIMARY KEY IDENTITY(1,1),
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE Articles (
    idArticle INT PRIMARY KEY IDENTITY(1,1),
    nomArticle VARCHAR(100),
    description VARCHAR(MAX),
    dateDebutEncheres DATETIME,
    dateFinEncheres DATETIME,
    miseAPrix INT,
    prixVente INT,
    etatVente NVARCHAR(50),
    idUtilisateur INT NOT NULL,
    idCategorie INT NOT NULL,
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur),
    FOREIGN KEY (idCategorie) REFERENCES Categories(idCategorie)
);

CREATE TABLE Retraits (
    idArticle INT PRIMARY KEY,
    rue NVARCHAR(255),
    codePostal NVARCHAR(10),
    ville NVARCHAR(100),
    FOREIGN KEY (idArticle) REFERENCES Articles(idArticle)
);

CREATE TABLE Encheres (
    idUtilisateur INT,
    idArticle INT,
    dateEnchere DATETIME NOT NULL,
    montantEnchere INT NOT NULL,
    PRIMARY KEY (idUtilisateur, idArticle),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur),
    FOREIGN KEY (idArticle) REFERENCES Articles(idArticle)
);
select * from Encheres;
select * from Articles;

EXEC sp_help Utilisateurs;


