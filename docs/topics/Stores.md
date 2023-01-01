# Stores

ProNouns needs to store players' pronouns somewhere - it does this using a store.
There are different types of store available on different platforms.

Unless otherwise specified, all stores work on all platforms.

NBT
: 
```ini
store = nbt
```
Stores pronouns alongside a player's data in the world file. This is the default value.

    {style="narrow"}
    Platforms
    : Fabric, Paper

Flat file
: 
```ini
store = file
```
Stores pronouns in a dedicated file next to the config.
> This store is **not** suitable for sharing pronouns between multiple servers.
> 
{style="warning"}

MySQL
: 
```ini
store = mysql
```
Stores pronouns in a MySQL database. This has the advantage of allowing pronouns to be shared across servers, 
for example a BungeeCord network.

    {style="narrow"}
    Platforms
    : none lol it doesn't exist yet

In-memory
: 
```ini
store = in_memory
```
Stores pronouns in memory. This, obviously, does not persist between restarts and is only really useful for testing.

