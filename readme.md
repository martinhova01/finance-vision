# Finance Vision-prosjekt
[Åpne i Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2312/gr2312?new)

Her følger litt informasjon om Finance Vision-prosjektet. Mer spesifikt om hvordan prosjektet er organisert, hvordan man kan kjøre programmet og hvilke java- og maven- versjoner som benyttes. For informasjon om hvordan programmet fungerer kan du lese [her](FinanceVision/readme.md).

## Oppbygning og innhold
Kodeprosjektet finner man i [FinanceVision](FinanceVision)-mappa.

### Programkode
Prosjektet er delt inn i fire moduler: core, persistence, ui og springboot. Core innholder kjernelogikken i programmet og er ikke avhengig av de andre modulene. Persistence håndterer lagring av data i form av JSON. UI inneholder grafisk brukergrensesnitt laget med JavaFX. Springboot inneholder en springboot applikasjon for å kjøre en restserver og definerer en REST-api for å kommunisere med serveren.

## Kjøring og testing
Prosjektet er satt for for å kjøre ved hjelp av maven kommandoer.

- For å innstallere prosjektet: `mvn install`
Dette kompilerer klassene, kjører testene, genererer Jacoco rapporter, kjører sportbugs og kjører checkstyle for alle modulene. (Kan også kjøre `mvn install -DskipUiTests` for å hoppe over UI testene).


- For å kjøre tester: `mvn test`
Det genereres Jacoco rapporter for hver modul i `FinanceVision/"modulnavn"/target/site/index.html`, hvor "modulnavn" er navnet på tilhørende modul.

- For å kjøre springboot restserveren: `mvn spring-boot:run`(når man står i springboot modulen)


- For å kjøre appen med direkte fillagring: `mvn javafx:run`(når man står i ui modulen)


- For å kjøre appen med fillagring via REST endpoint: `mvn javafx:run -Premoteapp`(når man står i ui modulen og springboot serveren kjører)

- Prosjektet er rigget for å produsere et "shippable" produkt ved hjelp av Jlink og Jpackage.
  - For å kjøre Jlink `mvn javafx:jlink`(i ui modulen)
  - For å kjøre Jpackage `mvn jpackage:jpackage`(i ui modulen)
Etter dette genereres resultatet i `FinanceVision/ui/target/dist`.


## REST api
Prosjektet inneholder en REST api for kjernelogikken i core. Dokumentasjonen av den finner du [her].(FinanceVision/springboot/readme.md)





## Versjoner
Prosjektet bruker følgende versjoner:
- java `17.0.8`
- maven `3.9.4`
- JUnit `5.10.0`
- JavaFX `17.0.8`
- JaCoCo`0.8.11`
- SpotBugs `4.7.2.0`
- CheckStyle `10.12.3`
- gson `2.10.1`
- springboot `2.4.4`
- TestFX `4.0.16-alpha`
- mockito `3.12.4`
- Jpackage `1.4.0`