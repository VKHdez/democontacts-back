# Entities

## User

Fields:

- `username`
- `email`
- `password`

Rules:

- A user can be related to only one persona.
- A user cannot exist without a persona. A persona, however, does not require a user — not every persona has an associated user account.
- Enabling or disabling a user also enables or disables its related persona, along with all of the persona's related content — addresses and contact numbers.
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
- Enabling or disabling a persona does not require it to have a user — not every persona has one. When the persona does have an associated user, enabling or disabling the persona also enables or disables that user.
- Enabling or disabling a persona also enables or disables all of its addresses and contact numbers.

## Address

Fields:

- associated persona (mandatory) — an address belongs to exactly one persona
- `street` (optional)
- `external number` (mandatory)
- `interior number` (optional)
- `country` (mandatory) — free text, not a fixed catalog
- `state` (mandatory) — free text, not a fixed catalog
- `city` (mandatory)
- `postal code` (mandatory)

Rules:

- A persona can have multiple addresses; an address belongs to exactly one persona.
- An address can be normal, billing, or both — it must be at least one of the two, it cannot be neither.
- A persona can have any number of normal and billing addresses — there is no limit.
- The postal code must be related to the city, state, and country.
- An address can be deleted on its own, without deleting the persona it belongs to.
- An address can only be viewed, edited, or deleted through the persona it belongs to; it is not reachable through a different persona.

## Contact Number

Fields:

- associated persona
- `number type` (personal, home, office/work)
- `number`
- `country code`

Rules:

- `number` and `country code` together must be unique.
- A phone number can only be associated with one persona. For example, `1234567890` can be related to Jon Doe, but that same number cannot also be registered to Jun Quan, since it is already associated.
