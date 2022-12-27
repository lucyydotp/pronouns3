# ProNouns
This is a work-in-progress rewrite of my plugin [ProNouns](https://github.com/lucypoulton/pronouns).

## THIS IS PRE-RELEASE SOFTWARE
I assume absolutely no responsibility if this plugin breaks anything - use at your own risk.

# Installation

### Paper
Download the Paper jarfile and place it into your `plugins` folder. Restart your server.

### Fabric
*N.B. this is a server-side mod. While it technically will work on the client, using the integrated server, it won't really achieve a lot!*
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
- **NBT** - stores pronouns in the server's world data.
- **Flat file** - stores pronouns in a dedicated file.
- **MySQL** - stores pronouns in a MySQL database.
- **In-memory** - does not save pronouns anywhere. All data is lost on restart. This is used for testing.

Currently platforms can use the following types of store:

- **Bukkit** - NBT (through `PersistentDataContainer`)
- **Fabric** - NBT

This will be configurable in the future.

# Configuration
Not yet implemented.
