CREATE TABLE IF NOT EXISTS Labels (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(32) NOT NULL
);

CREATE TABLE Writers (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE Posts (
    ID BIGINT AUTO_INCREMENT,
    Content VARCHAR(255) NOT NULL,
    Created DATE NOT NULL,
    Updated DATE NOT NULL,
    PostStatus VARCHAR(255) NOT NULL,
    WriterId BIGINT,
    PRIMARY KEY(ID),
    FOREIGN KEY (WriterId) REFERENCES Writers(ID)
);

CREATE TABLE IF NOT EXISTS Post_Labels (
    PostID BIGINT NOT NULL,
    LabelID INT NOT NULL,
    FOREIGN KEY (PostID) REFERENCES Posts(ID),
    FOREIGN KEY (LabelID) REFERENCES Labels(ID),
    UNIQUE (PostID, LabelID)
);
