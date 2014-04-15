/* Table: COMPETENCEENUM*/
create table COMPETENCEENUM  (
   COMPETENCE           VARCHAR2(40)                    primary key,
);

/* Table: ETATENUM*/
create table ETATENUM  (
   ETAT                 VARCHAR2(10)                    primary key
);

/* Table: ROLEENUM*/
create table ROLEENUM  (
   ROLE                 VARCHAR2(10)                    primary key,
);

/* Table: THEMEENUM*/
create table THEMEENUM  (
   NOMTHEME             VARCHAR2(20)                    primary key
);

/* Table: CENTRELOISIRS*/
create table CENTRELOISIRS  (
   NOMCENTRE            VARCHAR2(20)                    primary key,
);

/* Table: THEME*/
create table THEME  (
   NOMTHEME             VARCHAR2(20)					not null,
   NOMCENTRE            VARCHAR2(20)                    not null,
   primary key (NOMCENTRE, NOMTHEME),
   foreign key (NOMCENTRE) references CENTRELOISIRS (NOMCENTRE),
   foreign key (NOMTHEME) references THEMEENUM (NOMTHEME)
);

/* Table: ACTIVITE*/
create table ACTIVITE  (
   IDACTIVITE           INTEGER                         primary key,
   NOMCENTRE            VARCHAR2(20)					not null,
   NOMTHEME             VARCHAR2(20)                    not null,
   NOM                  VARCHAR2(20)                    not null,
   DESCRIPTIF           VARCHAR2(40)                    not null,
   NBMAXANIM            SMALLINT                        not null,
   foreign key (NOMCENTRE, NOMTHEME) references THEME (NOMCENTRE, NOMTHEME)
);

/* Table: COMPETENCEACTIVITE*/
create table COMPETENCEACTIVITE  (
   IDACTIVITE           INTEGER                         not null,
   COMPETENCE           VARCHAR2(40)                    not null,
   primary key (IDACTIVITE, COMPETENCE),
   foreign key (COMPETENCE) references COMPETENCEENUM (COMPETENCE),
   foreign key (IDACTIVITE) references ACTIVITE (IDACTIVITE)
);

/* Table: ANIMATEUR*/
create table ANIMATEUR  (
   NOMANIMATEUR         VARCHAR2(20)                    not null,
   PRENOMANIMATEUR      VARCHAR2(20)                    not null,
   EMAIL                VARCHAR2(30)                    not null,
   ESTINTERNE           SMALLINT                        not null,
   primary key (NOMANIMATEUR, PRENOMANIMATEUR)
);

/* Table: COMPETENCEANIMATEUR*/
create table COMPETENCEANIMATEUR  (
   NOMANIMATEUR         VARCHAR2(20)                    not null,
   PRENOMANIMATEUR      VARCHAR2(20)                    not null,
   COMPETENCE           VARCHAR2(40)                    not null,
   primary key (NOMANIMATEUR, PRENOMANIMATEUR, COMPETENCE),
   foreign key (NOMANIMATEUR, PRENOMANIMATEUR) references ANIMATEUR (NOMANIMATEUR, PRENOMANIMATEUR),
   foreign key (COMPETENCE) references COMPETENCEENUM (COMPETENCE)
);

/* Table: PERIODE*/
create table PERIODE  (
   PERIODE              VARCHAR2(30)                    primary key,
   NOMANIMATEUR         VARCHAR2(20)					not null,
   PRENOMANIMATEUR      VARCHAR2(20)					not null,
   SUPERPERIODE          VARCHAR2(30)					not null,
   DATEDEBUT            DATE                            not null,
   DATEFIN              DATE                            not null,
   foreign key (NOMANIMATEUR, PRENOMANIMATEUR) references ANIMATEUR (NOMANIMATEUR, PRENOMANIMATEUR)
);

alter table PERIODE add constraint FK___PERIODE foreign key (SUPERPERIODE) references PERIODE (PERIODE);

/* Table: ASIGNATION*/
create table ASIGNATION  (
   NOMANIMATEUR         VARCHAR2(20)                    not null,
   PRENOMANIMATEUR      VARCHAR2(20)                    not null,
   PERIODE              VARCHAR2(30)                    not null,
   IDACTIVITE           INTEGER                         not null,
   primary key (NOMANIMATEUR, PRENOMANIMATEUR, PERIODE, IDACTIVITE),
   foreign key (NOMANIMATEUR, PRENOMANIMATEUR) references ANIMATEUR (NOMANIMATEUR, PRENOMANIMATEUR),
   foreign key (PERIODE) references PERIODE (PERIODE),
   foreign key (IDACTIVITE) references ACTIVITE (IDACTIVITE)
);

/* Table: ETAT*/
create table ETAT  (
   IDACTIVITE           INTEGER                         not null,
   PERIODE              VARCHAR2(30)                    not null,
   ETAT                 VARCHAR2(10)                    not null,
   primary key (IDACTIVITE, PERIODE, ETAT),
   foreign key (IDACTIVITE) references ACTIVITE (IDACTIVITE),
   foreign key (PERIODE) references PERIODE (PERIODE),
   foreign key (ETAT) references ETATENUM (ETAT)
);

/* Table: COMPTE*/
create table COMPTE  (
   NOMUTILISATEUR       VARCHAR2(30)                    primary key,
   MOTDEPASS            VARCHAR2(20)                    not null,
   ACTIF                SMALLINT						not null
);

/* Table: UTILISATEUR*/
create table UTILISATEUR  (
   NOMFAMILLE           VARCHAR2(20)                    not null,
   PRENOM               VARCHAR2(20)                    not null,
   NOMUTILISATEUR       VARCHAR2(30)                    not null,
   MAIL                 VARCHAR2(30)                    not null,
   primary key (NOMFAMILLE, PRENOM),
   foreign key (NOMUTILISATEUR) references COMPTE (NOMUTILISATEUR)
);

/* Table: RESPONSABLE*/
create table RESPONSABLE  (
   NOMFAMILLE           VARCHAR2(20)                    not null,
   PRENOM               VARCHAR2(20)                    not null,
   NOMCENTRE            VARCHAR2(20)					not null,
   ROLE                 VARCHAR2(10)                    not null,
   NOMUTILISATEUR       VARCHAR2(30)					not null,
   MAIL                 VARCHAR2(30)                    not null,
   primary key (NOMFAMILLE, PRENOM),
   foreign key (NOMCENTRE) references CENTRELOISIRS (NOMCENTRE),
   foreign key (NOMFAMILLE, PRENOM) references UTILISATEUR (NOMFAMILLE, PRENOM),
   foreign key (ROLE) references ROLEENUM (ROLE)
);

/* Table: RFAMILLE*/
create table RFAMILLE  (
   NOMFAMILLE           VARCHAR2(20)                    not null,
   PRENOM               VARCHAR2(20)                    not null,
   NOMUTILISATEUR       VARCHAR2(30)					not null,
   MAIL                 VARCHAR2(30)                    not null,
   RESSOURCES           FLOAT                           not null,
   primary key (NOMFAMILLE, PRENOM),
   foreign key (NOMFAMILLE, PRENOM) references UTILISATEUR (NOMFAMILLE, PRENOM)
);

/* Table: ENFANT*/
create table ENFANT  (
   PRENOMENFANT         VARCHAR2(20)                    not null,
   NOMFAMILLENFANT      VARCHAR2(20)                    not null,
   NOMFAMILLE           VARCHAR2(20)                    not null,
   PRENOM               VARCHAR2(20)                    not null,
   AGE                  SMALLINT                        not null,
   primary key(NOMFAMILLENFANT, PRENOMENFANT),
   foreign key (NOMFAMILLE, PRENOM) references RFAMILLE (NOMFAMILLE, PRENOM)
);

/* Table: INSCRIPTION*/
create table INSCRIPTION  (
   IDACTIVITE           INTEGER                         not null,
   PRENOMENFANT         VARCHAR2(20)                    not null,
   PERIODE              VARCHAR2(30)                    not null,
   primary key (IDACTIVITE, PRENOMENFANT, PERIODE),
   foreign key (IDACTIVITE) references ACTIVITE (IDACTIVITE),
   foreign key (PRENOMENFANT) references ENFANT (PRENOMENFANT),
   foreign key (PERIODE) references PERIODE (PERIODE)
);