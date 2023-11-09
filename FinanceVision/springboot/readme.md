# Dokumentasjon av REST api

## isRunning
Returnerer true dersom dersom serveren kjører som den skal.
- type: `GET`
- path: `/fv/`
- response:
  - content-type: `JSON`
  - body: `Boolean`

## getUser
Returnerer brukeren med tilhørende brukernavn og passord. Returnerer null dersom ingen bruker har dette brukernavnet eller passordet til brukeren er feil.
- type `Get`
- path `/fv/user/{username}?password={password}`
- response:
  - content-type: `JSON`
  - body: `core.User`


## putUser
Legger til brukeren eller oppdaterer brukeren med tilhørende brukernavn dersom den allerede finnes.
- type `PUT`
- path `/fv/user/{username}`
- request:
  - content-type: `JSON`
  - body: `core.User`
- response: `void`

## removeUser
Fjerner brukeren med tilhørende brukernavn.
- type `DELETE`
- path `/fv/user/{username}`
- response: `void`

## containsUser
Sjekker om et brukernavn finnes. Returnerer true dersom den finnes og false ellers.
- type: `GET`
- path: `/fv/user/{username}/exists`
- response:
  - content-type: `JSON`
  - body: `Boolean`