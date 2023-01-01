# Config

ProNouns can be configured by editing the configuration file.
<tabs group="platform">
<tab title="Paper" group-key="paper">
For Paper, the config file is at <path>plugins/ProNouns/pronouns.cfg</path>.
</tab>
<tab title="Fabric" group-key="fabric">
For Fabric, the config file is at <path>config/pronouns.cfg</path>.
</tab>
</tabs>

## Formatting

> ProNouns makes frequent use of MiniMessage for formatting configuration. 
> See <https://docs.adventure.kyori.net/minimessage/format.html>.

accent
: A MiniMessage tag that prefixes accent text. Defaults to `<gradient:#fa9efa:#9dacfa>`.

## Update checking

checkForUpdates
: Whether to check for updates. Defaults to `true`.

updateChannel
: Which channel to check for updates on. Acceptable values are:
<table>
<tr><td>Name</td><td>Description</td></tr>
<tr>
    <td><code>release</code></td>
    <td>Production-ready releases. This is the default value.</td>
</tr>
<tr>
    <td><code>beta</code></td>
    <td>Pre-release beta builds, which will get new features early but are not as thoroughly tested.</td>
</tr>
<tr>
    <td><code>alpha</code></td>
    <td>Early development builds with bleeding-edge features. Not recommended for production use!</td>
</tr>
</table>


