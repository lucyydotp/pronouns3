# Commands

/pronouns help [query]
: Shows help for a command.
    
    **Arguments**

    {style="narrow"}
    query 
    : A specific command to show help for, otherwise lists all commands the player has access to.

/pronouns get \[username]
: Gets your or another player's pronouns.
    
    **Arguments**

    {style="narrow"}
    username 
    : A player to get pronouns for. or the sender if omitted.


/pronouns set \<pronouns> \[--player \<username>]
: Sets your pronouns. See [](Setting-your-pronouns.md).

    {style="medium"}
    Permission
    : <path>pronouns.set</path>

    **Arguments**

    {style="narrow"}
    pronouns 
    : The pronouns to set.

    player
    : A player to set pronouns for. or the sender if omitted. Requires permission <path>pronouns.set.other</path>.

/pronouns clear \[--player \<username>]
: Clears your pronouns.

    {style="medium"}
    Permission
    : <path>pronouns.set</path>

    **Arguments**

    {style="narrow"}
    player
    : A player to clear pronouns for. or the sender if omitted. Requires permission <path>pronouns.set.other</path>.

/pronouns version
: Shows plugin version information.

/pronouns update \[--force]
: Checks for updates.

    {style="medium"}
    Permission
    : <path>pronouns.update</path>

    **Arguments**

    {style="narrow"}
    force
    : Forces an update check, even if the plugin is aware of an available update already.

/pronouns reload
: Reloads the plugin's config.

    This command does **not** reload everything, for example the type of store will remain the same until a restart.

    {style="medium"}
    Permission
    : <path>pronouns.reload</path>

/pronouns dump
: Dumps all pronouns to a file.

    {style="medium"}
    Permission
    : <path>pronouns.dump</path>
