# Entities

## User

Fields:

- `username`
- `email`
- `password`

Rules:

- A user can be related to only one persona.
- A user cannot exist without a persona, and a persona cannot exist without its related user — deleting either one deletes both.
- Disabling a user or its related persona disables both, along with all of the persona's related content — addresses and contact numbers.
- The email must be unique.

## Persona

Fields:

- `name` (mandatory)
- `first name` (mandatory)
- `last name` (mandatory)
- `gender` (mandatory) — selected from a fixed catalog, not free text (see `genders` in `docs/architecture/db.md`)
- `birth date` (optional)

Rules:

- A persona can have multiple addresses and numbers.
- A persona can have multiple other personas (already registered) as emergency contacts.
- Disabling a persona (or its related user) disables all of its addresses and contact numbers along with it.

## Address

Fields:

- associated persona
- `street`
- `external number`
- `interior number`
- `country`
- `state`
- `city`
- `postal code`

Rules:

- An address can be normal, billing, or both.
- The postal code must be related to the city, state, and country.

## Contact Number

Fields:

- associated persona
- `number type` (personal, home, office/work)
- `number`
- `country code`

Rules:

- `number` and `country code` together must be unique.
- A phone number can only be associated with one persona. For example, `1234567890` can be related to Jon Doe, but that same number cannot also be registered to Jun Quan, since it is already associated.
