# Release 3

## Introduksjon
Dette dokumentet tar for seg hvilke endringer vi har gjort i FinanceVision-prosjektet siden forrige release.

## Brukergrensesnitt og CSS
I denne releasen har vi fokusert på å ferdigstille brukergrensesnittet slik at brukeropplevelsen blir best mulig. Vi har derfor benyttet et stilark (CSS) som hver av xml-filene peker til. I tillegg er applikasjonen nå tredelt med en meny øverst på skjermen, som kan brukes til å navigere mellom de tre hovedsidene: "Transactions", "Budget" og "User Settings".

![transaction.png](../release3/transaction.png)

## Funksjonalitet
I denne releasen har vi utvidet appen med noe ny funksjonalitet. Først og fremst er det nå en meny øverst på skjermen som brukes til å navigere mellom de tre hovedsidene. Vi har altså opprettet en side for redigering av brukerinformasjon. I tillegg har transaksjonssiden og budsjettsiden fått noe ny funksjonalitet. Vedlagt er en oversikt over disse implementasjonene.

### Transaksjonssiden
- Filtrer transaksjons-liste

![filter.png](../release3/filter.png)

### Budsjettsiden
- Legg til og fjern utgiftskategorier

![editBudget.png](../release3/editBudget.png)

### Brukerinstillinger
- Rediger brukerinformasjon
- Slett bruker
- Logg ut

![userSettings.png](../release3/userSettings.png)

## REST-API
Prosjektet inneholder nå en REST API for kjernelogikken i core. Dokumentasjonen av den finner du [her](../../FinanceVision/springboot/readme.md)

## Diagram

### Klassediagram
![classDiagram.png](../release3/classDiagram.png)
Diagrammet over er et klassediagram som viser hvordan klassene i core-modulen relaterer til hverandre.

### Pakkediagram
![architecture.png](../release3/architecture.png)
Dette diagrammet gir en oversikt over avhengighetene mellom pakkene brukt i prosjektet. 
NB: Vi har her latt være å vise spotbugs, checkstyle og jacoco, ettersom de blir brukt i mange av pakkene, og ville dermed ført til et mindre oversiktlig diagram.

### Sekvensdiagram
![sequenceDiagram](../release3/sequenceDiagram.png)
Sekvensdiagrammet over viser et eksempel på hvordan en bruker, core og REST API-en interagerer med hverandre.

## Arbeidsvaner 
- Forholder oss til issues og milestones.
- Vi har forholdt oss til trunk-based branching. Det vil si at hver branch er knyttet til en bestemt issue, og når en issue er fullført merger vi branchen med master-branchen.
- Hver gang vi gjør en endring går vi gjennom checkstyle-warnings før vi merger.
- Benytter pipelines hver gang vi pusher en endring.