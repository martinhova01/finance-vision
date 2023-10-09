# Finance Vision-prosjekt
[Åpne i Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2312/gr2312?new)

Her følger litt informasjon om Finance Vision-prosjektet. Mer spesifikt om hvordan prosjektet er organisert, hvordan man kan kjøre programmet og hvilke java- og maven-versjoner som benyttes. For informasjon om hvordan programmet fungerer, se [readme.md](FinanceVision/readme.md).

## Oppbygning og innhold
Kodeprosjektet finner man i [FinanceVision](FinanceVision)-mappa.

### Programkode
Selve programkoden befinner seg inne i src/main/java. Her ligger det tre mapper som inneholder alle klassene. Inne i core-mappa finner man grunnklassene som inneholder den grunnleggende logikken for prosjektet. I fileSaving-mappa ligger det en klasse, [FileSaving](FinanceVision/src/main/java/fileSaving/FileSaving.java), som håndterer lesing fra og skriving til fil. I ui-mappa ligger app-klassen, samt tre kontroller-klasser som sørger for at brukergrensesnittet oppdateres og fungerer som det skal.

### Testklasser
I src/test/java ligger det to testklasser. [UserTest](FinanceVision/src/test/java/core/UserTest.java) tester [User](FinanceVision/src/main/java/core/User.java)-klassen og [FileSavingTest](FinanceVision/src/test/java/fileSaving/FileSavingTest.java) sjekker om lesing fra og skriving til fil fungerer som det skal.

## Kjøring og testing
Prosjektet benytter maven for kjøring. Programmet skal altså kjøre ved å stå i FinanceVision-mappa og skrive `mvn javafx:run` i terminalen. Programmet kan testes ved å stå i FinanceVision-mappa og kjøre `mvn test`. Etter å ha kjørt testene vil det genereres en JaCoCo-rapport i filen `index.html` i mappen `target/site/jacoco`.

Når man kjører programmet og kommer til innloggingssiden, kan man benytte følgende brukernavn og passord for å slippe å opprette bruker:
 - Brukernavn: bruker
 - Passord: Passord123

## Versjoner
Prosjektet er testet og kjører for `java 17.0.5` og `maven 3.8.7`.