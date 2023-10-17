# Finance Vision-prosjekt
[Åpne i Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2312/gr2312?new)

Her følger litt informasjon om Finance Vision-prosjektet. Mer spesifikt om hvordan prosjektet er organisert, hvordan man kan kjøre programmet og hvilke java- og maven-versjoner som benyttes. For informasjon om hvordan programmet fungerer, se [readme.md](FinanceVision/readme.md).

## Oppbygning og innhold
Kodeprosjektet finner man i [FinanceVision](FinanceVision)-mappa.

### Programkode
Prosjektet er delt inn i tre moduler: core, persistence og ui. Core innholder kjernelogikken i programmet, persistence håndterer lagring av data og ui inneholder grafisk brukergrensesnitt. 

## Kjøring og testing
Prosjektet er satt for for å kjøre ved hjelp av maven kommandoer. Det første man gjør er å stå i FinanceVision-mappa å kjøre `mvn install`. Dette vil kompilere prosjektet, kjøre testene, generere Jacoco rapporter, kjøre Spotbugs og kjøre Checkstyle for alle modulene. Det genereres en Jacoco rapporter for hver modul i `FinanceVision/"modulnavn"/target/site/index.html`, hvor "modulnavn" er core, persistence eller ui.

For å kjøre appen, stå i `FinanceVision/ui` og kjør `mvn javafx:run`



## Versjoner
Prosjektet er testet og kjører for `java 17.0.8` og `maven 3.9.4`. Dette er også det EclipseChe er satt opp i.