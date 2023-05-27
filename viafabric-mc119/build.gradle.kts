dependencies {
    minecraft("com.mojang:minecraft:23w05a")
    mappings("net.fabricmc:yarn:23w05a+build.7:v2")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.73.4+1.19.4")
    modImplementation("com.terraformersmc:modmenu:6.1.0-alpha.1")
}

tasks.compileJava {
    options.release.set(17)
}
