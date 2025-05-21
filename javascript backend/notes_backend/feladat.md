# Feladat: Egyszerű Jegyzetkezelő API készítése Express használatával

Készíts egy egyszerű Jegyzetkezelő API-t Node.js és Express segítségével, amelyet később a React frontenddel lehet összekötni.

## Követelmények:

1. Készíts egy Express szervert, amely egy jegyzetkezelő REST API-t hoz létre.
2. Implementáld a CRUD műveleteket (Create, Read, Update, Delete) a jegyzetekre.
3. Használj in-memory adatbázist (JavaScript tömb), hogy a jegyzetek ne vesszenek el a szerver újraindítása után.
4. A felhasználók tudjanak jegyzeteket hozzáadni, lekérdezni, frissíteni és törölni az API-n keresztül.
5. A jegyzeteknek legyen egy **cím**, **tartalom**, valamint egy **dátum**, amikor létrehozták.

## API végpontok:

- **GET /notes**: Az összes jegyzet lekérése, dátum szerint csökkenő
- **GET /notes/:title** : Egy jegyzet lekérése title szerint, az URL-ben érkezik.
- **GET /notes/search/:txt**: Kulcsszavas keresés: olyan jegyzetek keresése amelyek címe vagy leírása tartalmazza az url-ben érkező szöveget/szövegrészt
- **POST /notes**: Új jegyzet hozzáadása (adatokat a request body-ban küldjük), ellenőrzés szerver oldalon is!
- **PUT /notes/:id**: Egy meglévő jegyzet frissítése ID alapján (cím és/vagy tartalom módosítása)
- **PATCH /notes/:id**: Egy meglévő jegyzet frissítése ID alapján (cím vagy tartalom )
- **DELETE /notes/:id**: Egy jegyzet törlése ID alapján
- **GET /notes/search**: Kulcsszavas keresés( keresünk a title-ben vagy a descr-ben), a body-ban érkezik a keresett szöveg(kis nagy betű érzéketlen)
