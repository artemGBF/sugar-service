CREATE TABLE sugars
(
    id                  serial PRIMARY KEY,
    name                text,
    color               text,
    form                text,
    url1                text,
    img1                bytea,
    url2                text,
    img2                bytea,
    sync                boolean,
    active              boolean
);