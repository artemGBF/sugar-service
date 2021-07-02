CREATE TABLE sugars
(
    id                  serial PRIMARY KEY,
    name                text,
    color               text,
    url1                text,
    url2                text,
    synchronizationFlag bool,
    description         text
);
