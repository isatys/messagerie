
/*DROP TABLE IF EXISTS todolists;
CREATE TABLE todolists (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
                         id INT AUTO_INCREMENT PRIMARY KEY ,
                         name VARCHAR(250) NOT NULL,
                         description TEXT NULL,
                         date DATETIME NULL DEFAULT NULL,
                         finished TINYINT NOT NULL DEFAULT '0',
                         started TINYINT NOT NULL DEFAULT '0',
                         id_todolist INT NOT NULL,
                         foreign key (id_todolist) references todolists(id)
);
*/
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    mail VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    telephone VARCHAR(255) NOT NULL,
    port INT NOT NULL
);

CREATE TABLE IF NOT EXISTS messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    contenu TEXT NOT NULL,
    date_envoi DATETIME NOT NULL,
    expediteur_id INT NOT NULL,
    destinataire_id INT NOT NULL,
    FOREIGN KEY (expediteur_id) REFERENCES utilisateur(id),
    FOREIGN KEY (destinataire_id) REFERENCES utilisateur(id)
    );

CREATE TABLE liste_amis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    utilisateur1_id INT NOT NULL,
    utilisateur2_id INT NOT NULL,
    etat ENUM('attente', 'accepte', 'refuse','bloque') NOT NULL DEFAULT 'attente',
    FOREIGN KEY (utilisateur1_id) REFERENCES utilisateur(id),
    FOREIGN KEY (utilisateur2_id) REFERENCES utilisateur(id)
);


