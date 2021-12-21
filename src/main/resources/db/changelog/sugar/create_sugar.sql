CREATE TABLE sugars
(
    id                  serial PRIMARY KEY,
    name                text,
    color               text,
    url1                text,
    img1                bytea,
    url2                text,
    img2                bytea,
    synchronizationFlag bool
);