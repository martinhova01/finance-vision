# FinanceVision-prosjekt
Her følger litt informasjon om FinanceVision-prosjektet. Mer spesifikt om hvordan prosjektet er organisert, hvilke java- og maven-versjoner som benyttes og hvordan man kan kjøre programmet.

## Oppbygning og innhold
Kodeprosjektet finner man i FinanceVision-mappa og selve koden befinner seg inne i src/main/java. Her ligger det tre mapper som inneholder alle klassene. Inne i core-mappa finner man grunnklassene som inneholder den grunnleggende logikken for prosjektet. I fileSaving-mappa ligger det en klasse, [FileSaving](src/main/java/fileSaving/FileSaving.java), som håndterer lesing fra og skriving til fil. I ui-mappa ligger app-klassen, samt tre kontroller-klasser som sørger for at brukergrensesnittet oppdateres og fungerer som det skal.

## Versjoner

## Kjøring
Prosjektet benytter maven for kjøring. For å kjøre programmet må man dermed stå i FinanceVision-mappa og skrive `mvn javafx:run` i terminalen.
