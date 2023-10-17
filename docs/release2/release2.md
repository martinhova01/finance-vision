# Release 2

## Introduksjon
Dette dokumentet tar for seg hvilke funksjonaliteter som er blitt implementert i FinanceVision-appen i release 2. Har skal vi ta for oss hva som har blitt gjort i denne releasen, samt hvilke funksjonaliteter vi tenker å inkludere i fremtidige releases.

## Funksjonalitet
I denne releasen har vi utvidet appen med en rekke nye funksjonaliteter. Først og fremst har vi oprettet en side der brukeren kan sette opp budsjett. I tillegg har vi lagt inn muligheten til å redigere og slette tidligere opprettede transaksjoner. Vedlagt er en oversikt over disse implementasjonene.

### Hovedside
- Fjernet oversikten over transaksjoner fra de siste 30 dagene.
- Lagt til en knapp som tar deg til siden for å sette opp budsjett.
- Flyttet "New transaction"-funksjonen til en egen side.
- Lagt til en knapp som tar deg til siden for å redigere en transaksjon.
- Lagt til en knapp for å slette en valgt transaksjon.

![mainPage.png](/docs/release2/mainPage.png)

### Budsjett
- Brukeren kan sette en grense for hvor mye penger de kan bruke innen hver kategori i løpet av 30 dager.
- Figuren til høyre for hver kategori viser om brukeren har gått over grensen.

![budgetPage.png](/docs/release2/budgetPage.png)

### Legg til og rediger transaksjon
- Brukeren kan legge inn informasjon om en transaksjon.
- Når bukeren trykker "add", lagres transaksjonen og brukeren blir sendt til hovedsiden.
- Siden for redigering av en transaksjon ser nærmest identisk ut, men laster inn informasjon om en valgt transaksjon på forhånd.

![addTransaction.png](/docs/release2/addTransaction.png)

## Arkitektur
Her følger informasjon om den oppdaterte arkitekturen i prosjektet.

### Modularisering
Kodingsprosjektet er nå delt opp i tre moduler; core, persistence og ui. I core-modulen ligger selve kjernelogikken i programmet. Ui-modulen inneholder informasjon om brukergrensesnittet og sørger for at dette oppdateres. Persistence-modulen håndterer lagring av data i JSON-format. Vi har gått for å ha implisitt lagring av brukerdata. Hver gang det skjer en endring i appen blir det automatisk lagret til fil.
Hvordan disse tre modulene henger sammen er illustrert i diagrammet under:

![architecture.png](/docs/architecture.png)

## Kodekvalitet
Her følger informasjon om endringer vi har gjort i forbindelse med kodekvalitet.

### Jacoco
Vi benytter Jacoco for å sjekke hvor mye av koden vår som dekkes av tester. Når prosjektet kompilerer vil det genereres én rapport for hver av de tre modulene; core, persistence og ui, som gir en oversikt over testdekningsgrad.

### SpotBugs
For å sikre god kodekvalitet, benytter vi SpotBugs, som kjører en rekke tester som leter etter feil i koden vår. Vi har opprettet en fil, [exclude.xml](/FinanceVision/config/spotbugs/exclude.xml), som inneholder tester som skal ekskluderes når SpotBugs kjører.

### Checkstyle
For å sikre god standard på koden vår, benytter vi Checkstyle, som går gjennom programmet og leter etter deler av koden som ikke oppfyller visse krav. Disse kravene følger google-standarden og er definert i [google_checks.xml](/FinanceVision/config/checkstyle/google_checks.xml). Vi har imidlertid endret kravet om indentation fra 2 til 4, da vi mener dette er penere og mer oversiktlig.

## Arbeidsvaner 
- Forholder oss til issues og milestones.
- Vi har forholdt oss til trunk-based branching. Det vil si ar hver branch er knyttet til en bestemt issue, og når en issue er fullført merger vi branchen med master-branchen.
- Hver gang vi gjør en endring går vi gjennom checkstyle-warnings før vi merger.

## Mulige fremtidige funksjoner
- Funksjon for sortering og filtrering av transaksjons-liste.
- Mulighet for å legge inn egne transaksjons-kategorier.