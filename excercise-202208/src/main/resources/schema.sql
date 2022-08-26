CREATE
    TABLE inquiry(
        id INT NOT NULL AUTO_INCREMENT
        ,name VARCHAR(100) NOT NULL
        ,email VARCHAR(100) NOT NULL
        ,contents VARCHAR(500) NOT NULL
        ,created DATETIME NOT NULL
        ,PRIMARY KEY (id)
    );
CREATE
    TABLE survey(
        id INT NOT NULL AUTO_INCREMENT
        ,age INT NOT NULL
        ,satisfaction INT NOT NULL
        ,COMMENT VARCHAR(100)
        ,created DATETIME NOT NULL
        ,PRIMARY KEY (id)
    );
CREATE
    TABLE  item(
        id BIGINT NOT NULL AUTO_INCREMENT
        ,name VARCHAR(255)
        ,price REAL
        ,vendor VARCHAR(255)
        ,PRIMARY KEY (id)
    );
