# ProNouns

This is a work-in-progress rewrite of my plugin [ProNouns](https://github.com/lucypoulton/pronouns).
ProNouns is a plugin that lets players set their pronouns, making their experience on the server that little bit more
personal.

## THIS IS PRE-RELEASE SOFTWARE

I assume absolutely no responsibility if this plugin breaks anything - use at your own risk.

### Planned feature list

- Players can set their pronouns individually
- Players can have multiple pronoun sets
- Custom player-defined custom pronouns / neopronouns support
- Support for PlaceholderAPI with tools to properly form sentences including players' pronouns
- Store players' pronouns in multiple places, including MySQL for network compatibility
- Extensible through a developer API
- Support for many platforms - Paper, Sponge, Fabric, BungeeCord, Velocity

# Installation

### Paper

Download the Paper jarfile and place it into your `plugins` folder. Restart your server.

### Fabric

*N.B. this is a server-side mod. While it technically will work on the client, using the integrated server,
it won't achieve a lot!*<br/>
Download the Fabric jarfile and place it into your `mods` folder. Restart your server.

### Sponge, Velocity, BungeeCord

Support for these three platforms is planned but not yet implemented.

# Commands

`/pronouns get [username]` - Gets a player's pronouns, or your own if no username is given.<br/>
`/pronouns set <pronouns>` - Sets your pronouns.<br/>
`/pronouns set <pronouns> --player <username>` - Sets another player's pronouns.<br/>
`/pronouns clear [username` - Clears your pronouns, or your own if no username is given.<br/>

# Pronoun stores

The plugin stores pronouns using a store. There are different types of store:

| Name  | Description                                | Paper             | Fabric            |
|-------|--------------------------------------------|-------------------|-------------------|
| NBT   | Stores pronouns in the server's world data | ✓                 | ✓                 |
| File  | Stores pronouns in a dedicated file        | ✓                 | ✓                 |
| MySQL | Stores pronouns in a MySQL database        | Soon<sup>TM</sup> | Soon<sup>TM</sup> |

This will be configurable in the future.

# Configuration

Not yet implemented.
