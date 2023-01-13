# Placeholders

<include from="snippets.topic" element-id="grammar"/>

ProNouns supports placeholders to let other plugins use pronouns in messsages.
<p switcher-key="Paper">
Placeholders are available through <a href="https://www.spigotmc.org/resources/placeholderapi.6245/">PlaceholderAPI</a>
by HelpChat.
</p>
 <p switcher-key="Fabric">
Placeholders are available through <a href="https://placeholders.pb4.eu">Placeholder API</a> by Patbox.
It's included in the plugin jar file using a technique called jar-in-jar so you don't need to manually
install it.
</p>

<!-- FIXME: switch this to a deflist, blocked by wrs-1142
    switcher-key is ignored in deflists https://youtrack.jetbrains.com/issue/WRS-1142
    -->

| Paper                          | Fabric                        | Description                                                              | Examples                                     |
|--------------------------------|-------------------------------|--------------------------------------------------------------------------|----------------------------------------------|
| `%\pronouns_pronouns%`         | `%pronouns:pronouns%`         | A player's pronouns in display form, or **Unset** if not set.            | **She/Her**, **Unset**                       |
| `%\pronouns_subjective%`       | `%pronouns:subjective%`       | The subjective pronoun.                                                  | **they**                                     |
| `%\pronouns_objective%`        | `%pronouns:objective%`        | The objective pronoun.                                                   | **them**                                     |
| `%\pronouns_possessiveadj%`    | `%pronouns:possessiveadj%`    | The possessive adjective.                                                | **theirs**                                   |
| `%\pronouns_possessive%`       | `%pronouns:possessive%`       | The possessive pronoun.                                                  | **their**                                    |
| `%\pronouns_reflexive%`        | `%pronouns:reflexive%`        | The reflexive pronoun.                                                   | **themselves**                               |
| `%\pronouns_all%`              | `%pronouns:all%`              | The first pronoun set in full form.                                      | **they/them/theirs/their/themselves:p**      |
| `%\pronouns_verb_<verb>%`      | `%pronouns:verb <verb>%`      | Conjugates \<verb>.                                                      | (he) **is**, (they) **have**, (she) **goes** |
| `%\pronouns_conj_<sing>_<pl>%` | `%pronouns:verb <sing> <pl>%` | If the player's first pronoun set is singular, \<sing>, otherwise \<pl>. |                                              |


## Modifiers

Sometimes you'll want to adjust the text from a placeholder. This is where modifiers come in.
They're put on the end of placeholders and can be chained.


| Modifier    | Description                                                                         | Example                                                   |
|-------------|-------------------------------------------------------------------------------------|-----------------------------------------------------------|
| `uppercase` | MAKES ALL TEXT UPPERCASE.                                                           | `%pronouns_pronouns_uppercase%` -> `SHE/HER`              |
| `lowercase` | makes all text lowercase.                                                           | `%pronouns_pronouns_lowercase%` -> `she/her`              |
| `capital`   | Capitalises text - makes the first letter uppercase, and everything else lowercase. | `%pronouns_pronouns_capital%` -> `She/her`                |
| `nounset`   | If a player does not have pronouns set, use an empty string instead of the default  | (when not set) `%pronouns_pronouns_nounset%` -> `<empty>` |
