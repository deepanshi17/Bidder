CREATE TABLE items(
   name        VARCHAR(36) NOT NULL PRIMARY KEY
  ,description VARCHAR(353) NOT NULL
  ,id          INTEGER  NOT NULL
  ,timeout     INTEGER  NOT NULL
  ,minPrice    INTEGER  NOT NULL
  ,buyItNow    INTEGER  NOT NULL
  ,category    VARCHAR(18) NOT NULL
);
INSERT INTO items(name,description,id,timeout,minPrice,buyItNow,category) VALUES ('Extra Terrestrials','Rare Atari 2600 game developed in 1983 by a Canadian company called Skill Screen Games.',1,5,1000,90000,'Gaming');
INSERT INTO items(name,description,id,timeout,minPrice,buyItNow,category) VALUES ('Apollo 15 Lunar Surface Chronograph','This precision timepiece, made available here for the first time, is an astounding specimen rife with aeronautical and horological history—a key piece inherent to Apollo 15’s success.',2,3,1000,10000,'Space Collectibles');
INSERT INTO items(name,description,id,timeout,minPrice,buyItNow,category) VALUES ('Peter Pan Original Concept Art','(Walt Disney Studios, 1953) Marvelous original concept painting by Mary Blair of Tinker Bell, Peter Pan, and the Darling children of Wendy, John, and Michael soaring over London en route to the island of Never Land. In fine condition, with tack holes to corners and some soiling to borders.',3,10,500,3000,'Animation');
INSERT INTO items(name,description,id,timeout,minPrice,buyItNow,category) VALUES ('Chris Martin''s First Guitar','Chris''s very first guitar, bought at Bill Greenhalgh''s music shop in Chris''s hometown of Exeter. Chris brought it with him when he moved to London for university, where Coldplay formed, and he wrote many of the band''s earliest songs on it.',4,15,10000,500000,'Popular Culture');
INSERT INTO items(name,description,id,timeout,minPrice,buyItNow,category) VALUES ('Leonardo da Vinci''s ''Salvator Mundi''','This painting is a depiction of Christ as ‘Saviour of the World’ by one of history’s greatest and most renowned artists. This stunning price reflects the extreme rarity of paintings by Leonardo da Vinci — there are fewer than 20 in existence acknowledged as being from the artist’s own hand, and all apart from Salvator Mundi  are in museum collections.',5,1,10000,5000000,'Art and Literature');
