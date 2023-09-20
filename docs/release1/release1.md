# Dokumentasjon 

## Introduksjon

Dette dokumentet tar for seg hvilke funksjonaliteter som er blitt implementert i FinanceVision-appen i Release 1 -  oppstarten av appen. Det inneholder hva som har blitt gjort, og hvilke funksjonaliteter vi tenker å inkludere i fremtidige releases. 

- - - 

## Featurebeskrivelse

I denne versjonen av appen har vi opprettet en fungerende innloggingsside, hvor det er mulig for brukere å registrere seg og logge inn. I tillegg er det implementert enkel funksjonalitet som gir oversikt over inntekter og utgifter, samt en saldo som oppdateres i henhold til disse transaksjonene. Vedlagt er oversikt over disse implementasjonene

 _Innloggingssider:_

- Opprettet enkelt brukergrensesnitt for innloggingsside.
 - Brukere kan nå registrere konto ved å fylle inn fullt navn, brukernavn, email, passord og startsaldo.
![Login page](login.png)
![Register new user page](registerUser.png)

_Filbehandling:_

- Det er lagt til filbehandling som sørger for å lagre brukerdata i en tekstfil, for å beholde informasjon fra tidligere bruk av appen.

_Hovedside:_

- Opprettet enkelt brukergrensesnitt som gir oversikt over hovedfunksjonene til appen. 
- Mulighet for å loggføre transaksjoner, i form av inntekt og utgifter.
- Transaksjoner kan kategoriseres som f.eks. Mat eller Clothes.

_Testklasser:_

- Opprettet testklasser som sikrer pålitelighet av FinanceVision og dens funksjonaliteter. 
- Testklassene innebærer testing av logikken til klassene Account, Expense, Income, User og Filsaving.

_

## Fremtidige funksjoner:

_1. Opprydding av hovedsiden:_
- En ny og mer oversiktlig hovedside vil sørge for mer 'affordance' og intuitivitet.
- Nye funksjonaliteter flyttes til egne sider for bedre oversikt

_2. Implmentere budsjettsystem_
- Bruker kan sette en budsjettgrense på forskjellige utgiftskategorier, og få en oversikt over hvor nærme man er grensen.

_3. Side for å redigere brukerinfo_
- Bruker kan redigere brukernavn, passord, epost, osv.
- Bruker kan slette brukeren

_4. Mulighet for å slette og rediger transaksjoner_
  