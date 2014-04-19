INSERT ALL
  INTO ROLEENUM VALUES ('R_ASSOCIATION')
  INTO ROLEENUM VALUES ('R_CENTRE')
  INTO ROLEENUM VALUES ('R_PLANIFICATION')
SELECT * FROM dual;

INSERT ALL
  INTO COMPETENCEENUM VALUES ('Aisance oratoire')
  INTO COMPETENCEENUM VALUES ('Aptitude à la communication')
  INTO COMPETENCEENUM VALUES ('Charisme')
  INTO COMPETENCEENUM VALUES ('Capacité à superviser')
  INTO COMPETENCEENUM VALUES ('Collaborateur')
  INTO COMPETENCEENUM VALUES ('Compétitif')
  INTO COMPETENCEENUM VALUES ('Créatif')
  INTO COMPETENCEENUM VALUES ('Dynamique')
  INTO COMPETENCEENUM VALUES ('Drôle')
  INTO COMPETENCEENUM VALUES ('Esprit d’équipe')
  INTO COMPETENCEENUM VALUES ('Habileté à gérer des conflits')
  INTO COMPETENCEENUM VALUES ('Imaginatif')
  INTO COMPETENCEENUM VALUES ('Leadership')
  INTO COMPETENCEENUM VALUES ('Motivateur')
  INTO COMPETENCEENUM VALUES ('Motivé')
  INTO COMPETENCEENUM VALUES ('Pédagogue')
  INTO COMPETENCEENUM VALUES ('Ponctuel')
  INTO COMPETENCEENUM VALUES ('Sens de l’humour')
  INTO COMPETENCEENUM VALUES ('Sociable')
  INTO COMPETENCEENUM VALUES ('Stratégique')
  INTO COMPETENCEENUM VALUES ('Spontané')
  INTO COMPETENCEENUM VALUES ('Versatile')
SELECT * FROM dual;

INSERT ALL
  INTO ETATENUM VALUES ('OUVERTE')
  INTO ETATENUM VALUES ('FERMEE')
  INTO ETATENUM VALUES ('PRE_CONFIRMEE')
  INTO ETATENUM VALUES ('CONFIRMEE')
  INTO ETATENUM VALUES ('FINIE')
SELECT * FROM dual;

INSERT ALL
  INTO THEMEENUM VALUES('Découverte de la nature')
  INTO THEMEENUM VALUES('Initiation à l’environnement')
  INTO THEMEENUM VALUES('Expression artistique')
  INTO THEMEENUM VALUES('Sport collectif')
  INTO THEMEENUM VALUES('Sport extreme')
  INTO THEMEENUM VALUES('Sport de précision')
SELECT * FROM dual;  

INSERT ALL
  INTO CENTRELOISIRS VALUES('MAISON des COLLINES')
  INTO CENTRELOISIRS VALUES('PETITES MAISONS')  
  INTO CENTRELOISIRS VALUES('LA COMBE DE LANCEY')
  INTO CENTRELOISIRS VALUES('CLOS D’ESPIES')
  INTO CENTRELOISIRS VALUES('CLEM’ENFANTS')
  INTO CENTRELOISIRS VALUES('VILLA ARTHAUD')
  INTO CENTRELOISIRS VALUES('TANIERE ENCHANTEE')
SELECT * FROM dual;  

INSERT ALL
  INTO THEME VALUES ('Sport de précision', 'PETITES MAISONS')
  INTO THEME VALUES ('Expression artistique', 'PETITES MAISONS')
  INTO THEME VALUES ('Expression artistique', 'LA COMBE DE LANCEY')
  INTO THEME VALUES ('Sport collectif', 'CLOS D’ESPIES')
  INTO THEME VALUES ('Sport extreme', 'CLOS D’ESPIES')
  INTO THEME VALUES ('Expression artistique', 'CLOS D’ESPIES')
  INTO THEME VALUES ('Découverte de la nature', 'MAISON des COLLINES')
  INTO THEME VALUES ('Initiation à l’environnement', 'MAISON des COLLINES')
  INTO THEME VALUES ('Sport collectif', 'CLEM’ENFANTS')
  INTO THEME VALUES ('Expression artistique', 'CLEM’ENFANTS')
  INTO THEME VALUES ('Expression artistique', 'VILLA ARTHAUD')
  INTO THEME VALUES ('Sport collectif', 'VILLA ARTHAUD')
  INTO THEME VALUES ('Sport extreme', 'VILLA ARTHAUD')
  INTO THEME VALUES ('Expression artistique', 'TANIERE ENCHANTEE')
SELECT * FROM dual;  

INSERT ALL
  INTO PERIODE VALUES('Vacances d''hiver 2014', null, DATE '2014-03-01', DATE '2014-03-16')
  INTO PERIODE VALUES('Vacances de printemps 2014', null, DATE '2014-04-26', DATE '2014-05-11' )
  INTO PERIODE VALUES('Vacances d''été 2014', null, DATE '2014-07-05', DATE '2014-08-31' )
  INTO PERIODE VALUES('Vacances de Toussaint 2014', null, DATE '2014-10-18', DATE '2014-11-03' )
  INTO PERIODE VALUES('Vacances de noël 2014', null, DATE '2014-12-20', DATE '2015-01-05' )
SELECT * FROM dual;    

INSERT ALL
  INTO ACTIVITE (NOMCENTRE, NOMTHEME, NOM, DESCRIPTIF, NBMAXANIM) 
    VALUES('MAISON des COLLINES', 'Découverte de la nature', 'Visite de la campagne', 
    'les enfants inscrits peuvent profiter des nombreux espaces verts de la Maison des Collines et d''un encadrement, par des équipes d''animation sensibilisées à la nature',
    10)
SELECT * FROM dual;

INSERT ALL
  INTO PERIODE VALUES('Découverte de la nature 04/28 - 04/30', 'Vacances de printemps 2014', DATE '2014-04-28', DATE '2014-04-30')
SELECT * FROM dual;  

INSERT ALL 
  INTO ETAT VALUES(1, 'Découverte de la nature 04/28 - 04/30', 'OUVERTE')
SELECT * FROM dual;




INSERT ALL
  INTO COMPTE VALUES ('Richard.Fuller','1234',1)
  INTO COMPTE VALUES ('Marie.Bertrand', '1234', 1)
  INTO COMPTE VALUES ('Dominique.Perrier', '1234', 1)
  INTO COMPTE VALUES ('Paul.Henriot', '1234', 1)
  INTO COMPTE VALUES ('Janine.Labrune', '1234', 0)
  INTO COMPTE VALUES ('Carine.Schmitt', '1234', 0)
SELECT * FROM dual;

INSERT ALL
  INTO UTILISATEUR VALUES ('Fuller','Richard','Richard.Fuller','Richard.Fuller@mail.com')
  INTO UTILISATEUR VALUES ('Bertrand','Marie','Marie.Bertrand','Marie.Bertrand@mail.com')
  INTO UTILISATEUR VALUES ('Perrier','Dominique','Dominique.Perrier','Dominique.Perrier@mail.com')
  INTO UTILISATEUR VALUES ('Henriot','Paul','Paul.Henriot','Paul.Henriot@mail.com')
  INTO UTILISATEUR VALUES ('Labrune','Janine','Janine.Labrune','Janine.Labrune@mail.com') 
  INTO UTILISATEUR VALUES ('Schmitt','Carine','Carine.Schmitt','Carine.Schmitt@mail.com') 
SELECT * FROM dual;

INSERT ALL
  INTO RESPONSABLE VALUES ('Fuller','Richard', null, 'R_ASSOCIATION', 'Richard.Fuller','Richard.Fuller@mail.com')
  INTO RESPONSABLE VALUES ('Bertrand','Marie', 'MAISON des COLLINES', 'R_CENTRE', 'Marie.Bertrand','Marie.Bertrand@mail.com')
  INTO RESPONSABLE VALUES ('Perrier','Dominique', 'MAISON des COLLINES', 'R_PLANIFICATION', 'Dominique.Perrier','Dominique.Perrier@mail.com')
SELECT * FROM dual;

INSERT ALL
  INTO RFAMILLE VALUES('Henriot','Paul', 'Paul.Henriot','Paul.Henriot@mail.com', 36000)
  INTO RFAMILLE VALUES('Labrune','Janine','Janine.Labrune','Janine.Labrune@mail.com', 30000)
  INTO RFAMILLE VALUES('Schmitt','Carine','Carine.Schmitt','Carine.Schmitt@mail.com', 32000)
SELECT * FROM dual;

INSERT ALL
  INTO ENFANT VALUES('Daniel', 'Henriot', 'Henriot','Paul', 15)
  INTO ENFANT VALUES('Annette', 'Roulet', 'Labrune','Janine', 16)
  INTO ENFANT VALUES('Christian', 'Roulet', 'Labrune','Janine', 12)
  INTO ENFANT VALUES('John', 'Schmitt', 'Schmitt','Carine', 8)
SELECT * FROM dual;

commit;
