plugins {
    id("com.modrinth.minotaur")
}

val v = version.toString()

val channel = when {
    v.contains("alpha") || v.endsWith("SNAPSHOT") -> "alpha"
    v.contains("beta") -> "beta"
    else -> "release"
}

modrinth {
    projectId.set("pronouns")
    versionType.set(channel)
    uploadFile.set(tasks.getByName("shadowJar"))
    token.set(System.getenv("MODRINTH_TOKEN"))
}
