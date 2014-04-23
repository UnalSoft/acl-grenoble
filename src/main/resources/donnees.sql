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
    'les enfants inscrits peuvent profiter des nombreux espaces verts de la Maison des Collines et d''un encadrement, par des équipes d''animation sensibilisées à la nature', 10)
  INTO ACTIVITE (NOMCENTRE, NOMTHEME, NOM, DESCRIPTIF, NBMAXANIM) 
	  VALUES ('MAISON des COLLINES', 'Découverte de la nature', 'Tir à l''arc', 'Les activités se déroulent à St Martin d’Uriage et tour- nent autour de deux grands thèmes décidés une semaine à l’avance, par l’équipe d’animation. Les enfants sont répartis en 2 groupes d’âge.', 5)
SELECT * FROM dual;

INSERT ALL
  INTO COMPETENCEACTIVITE VALUES(1, 'Charisme')
  INTO COMPETENCEACTIVITE VALUES(1, 'Leadership')
  INTO COMPETENCEACTIVITE VALUES(1, 'Sens de l’humour')
  INTO COMPETENCEACTIVITE VALUES(2, 'Capacité à superviser')
  INTO COMPETENCEACTIVITE VALUES(2, 'Motivateur')
  INTO COMPETENCEACTIVITE VALUES(2, 'Stratégique')
SELECT * FROM dual;

INSERT ALL
  INTO PERIODE VALUES('Découverte de la nature 04/28 - 04/30', 'Vacances de printemps 2014', DATE '2014-04-28', DATE '2014-04-30')
  INTO PERIODE VALUES ('Tir à l''arc 04/18 - 04/28', 'Vacances de printemps 2014', DATE '2014-04-18', DATE '2014-04-28')
SELECT * FROM dual;  

INSERT ALL 
  INTO ETAT VALUES(1, 'Découverte de la nature 04/28 - 04/30', 'OUVERTE')
  INTO ETAT VALUES (2, 'Tir à l''arc 04/18 - 04/28', 'PRE_CONFIRMEE')
SELECT * FROM dual;


INSERT ALL
  INTO COMPTE VALUES ('Richard.Fuller','1234',1)
  INTO COMPTE VALUES ('Marie.Bertrand', '1234', 1)
  INTO COMPTE VALUES ('Dominique.Perrier', '1234', 1)
  INTO COMPTE VALUES ('Paul.Henriot', '1234', 1)
  INTO COMPTE VALUES ('Jose.Pavarotti', '1234', 1)
  INTO COMPTE VALUES ('Janine.Labrune', '1234', 0)
  INTO COMPTE VALUES ('Carine.Schmitt', '1234', 0)
SELECT * FROM dual;
INSERT ALL
  INTO UTILISATEUR VALUES ('Fuller','Richard','Richard.Fuller','Richard.Fuller@yopmail.com')
  INTO UTILISATEUR VALUES ('Bertrand','Marie','Marie.Bertrand','Marie.Bertrand@yopmail.com')
  INTO UTILISATEUR VALUES ('Perrier','Dominique','Dominique.Perrier','Dominique.Perrier@yopmail.com')
  INTO UTILISATEUR VALUES ('Henriot','Paul','Paul.Henriot','Paul.Henriot@yopmail.com')
  INTO UTILISATEUR VALUES ('Pavarotti','Jose','Jose.Pavarotti','Jose.Pavarotti@yopmail.com')
  INTO UTILISATEUR VALUES ('Labrune','Janine','Janine.Labrune','Janine.Labrune@yopmail.com') 
  INTO UTILISATEUR VALUES ('Schmitt','Carine','Carine.Schmitt','Carine.Schmitt@yopmail.com') 
SELECT * FROM dual;

INSERT ALL
  INTO RESPONSABLE VALUES ('Fuller','Richard', null, 'R_ASSOCIATION', 'Richard.Fuller','Richard.Fuller@yopmail.com')
  INTO RESPONSABLE VALUES ('Bertrand','Marie', 'MAISON des COLLINES', 'R_CENTRE', 'Marie.Bertrand','Marie.Bertrand@yopmail.com')
  INTO RESPONSABLE VALUES ('Perrier','Dominique', 'MAISON des COLLINES', 'R_PLANIFICATION', 'Dominique.Perrier','Dominique.Perrier@yopmail.com')
SELECT * FROM dual;

INSERT ALL
  INTO RFAMILLE VALUES('Henriot','Paul', 'Paul.Henriot','Paul.Henriot@mail.com', 36000)
  INTO RFAMILLE VALUES('Labrune','Janine','Janine.Labrune','Janine.Labrune@mail.com', 30000)
  INTO RFAMILLE VALUES('Schmitt','Carine','Carine.Schmitt','Carine.Schmitt@mail.com', 32000)
SELECT * FROM dual;

INSERT ALL
  INTO ENFANT VALUES('Daniel', 'Henriot', 'Henriot','Paul', 15)
  INTO ENFANT VALUES('Charlie', 'Henriot', 'Henriot','Paul', 15)
  INTO ENFANT VALUES('Jenny', 'Henriot', 'Henriot','Paul', 10)
  INTO ENFANT VALUES('Annette', 'Roulet', 'Labrune','Janine', 16)
  INTO ENFANT VALUES('Christian', 'Roulet', 'Labrune','Janine', 12)
  INTO ENFANT VALUES('John', 'Schmitt', 'Schmitt','Carine', 8)
SELECT * FROM dual;

INSERT ALL
  INTO ANIMATEUR VALUES('Phillips', 'Rene', 'Rene.Phillips@yopmail.com', 1)
  INTO ANIMATEUR VALUES('Pfalzheim', 'Henriette', 'Henriette.Pfalzheim@yopmail.com', 1)
  INTO ANIMATEUR VALUES('Bertrand', 'Marie', 'Marie.Bertrand@yopmail.com', 1)
  INTO ANIMATEUR VALUES('Pipps', 'Georg', 'Georg.Pipps@yopmail.com', 1)
  INTO ANIMATEUR VALUES('Batista', 'Bernardo', 'Bernardo.Batista@yopmail.com', 0)
  INTO ANIMATEUR VALUES('Kloss', 'Horst', 'Horst.Kloss@yopmail.com', 0)
  INTO ANIMATEUR VALUES('Wilson', 'Paula', 'Paula.Wilson@yopmail.com', 0)
  INTO ANIMATEUR VALUES('Limeira', 'Janete', 'Janete.Limeira@yopmail.com', 0)
SELECT * FROM dual;

INSERT ALL
  INTO COMPETENCEANIMATEUR VALUES('Phillips', 'Rene', 'Capacité à superviser')
  INTO COMPETENCEANIMATEUR VALUES('Phillips', 'Rene', 'Motivateur')
  INTO COMPETENCEANIMATEUR VALUES('Phillips', 'Rene', 'Stratégique')
  INTO COMPETENCEANIMATEUR VALUES('Phillips', 'Rene', 'Sociable')
  INTO COMPETENCEANIMATEUR VALUES('Pfalzheim', 'Henriette', 'Capacité à superviser')
  INTO COMPETENCEANIMATEUR VALUES('Pfalzheim', 'Henriette', 'Sociable')
  INTO COMPETENCEANIMATEUR VALUES('Bertrand', 'Marie', 'Sociable')
  INTO COMPETENCEANIMATEUR VALUES('Pipps', 'Georg', 'Sociable')
  INTO COMPETENCEANIMATEUR VALUES('Batista', 'Bernardo', 'Capacité à superviser')
  INTO COMPETENCEANIMATEUR VALUES('Batista', 'Bernardo', 'Motivateur')
  INTO COMPETENCEANIMATEUR VALUES('Batista', 'Bernardo', 'Stratégique')
  INTO COMPETENCEANIMATEUR VALUES('Batista', 'Bernardo', 'Sociable')
  INTO COMPETENCEANIMATEUR VALUES('Kloss', 'Horst', 'Capacité à superviser')
  INTO COMPETENCEANIMATEUR VALUES('Kloss', 'Horst', 'Sociable')
  INTO COMPETENCEANIMATEUR VALUES('Wilson', 'Paula', 'Capacité à superviser')
  INTO COMPETENCEANIMATEUR VALUES('Limeira', 'Janete', 'Capacité à superviser')
SELECT * FROM dual;

INSERT ALL
  INTO EST_DISPONIBLE VALUES('Phillips', 'Rene', 'Vacances de printemps 2014')
  INTO EST_DISPONIBLE VALUES('Pfalzheim', 'Henriette', 'Vacances de printemps 2014')
  INTO EST_DISPONIBLE VALUES('Bertrand', 'Marie', 'Vacances d''été 2014')
  INTO EST_DISPONIBLE VALUES('Pipps', 'Georg', 'Vacances de Toussaint 2014')
  INTO EST_DISPONIBLE VALUES('Batista', 'Bernardo', 'Vacances de printemps 2014')
  INTO EST_DISPONIBLE VALUES('Kloss', 'Horst', 'Vacances de printemps 2014')
  INTO EST_DISPONIBLE VALUES('Wilson', 'Paula', 'Vacances d''été 2014')
  INTO EST_DISPONIBLE VALUES('Limeira', 'Janete', 'Vacances de Toussaint 2014')
SELECT * FROM dual;

INSERT ALL
  INTO INSCRIPTION VALUES(2, 'Daniel', 'Henriot', 'Tir à l''arc 04/18 - 04/28')
  INTO INSCRIPTION VALUES(2, 'Charlie', 'Henriot', 'Tir à l''arc 04/18 - 04/28')
  INTO INSCRIPTION VALUES(2, 'Jenny', 'Henriot', 'Tir à l''arc 04/18 - 04/28')
SELECT * FROM dual;

commit;

DESC INSCRIPTION;
