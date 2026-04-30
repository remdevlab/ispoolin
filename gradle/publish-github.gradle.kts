apply(plugin = "maven-publish")

afterEvaluate {
    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/remdevlab/ispoolin")
                credentials {
                    username = if (project.hasProperty("GITHUB_ACTOR")) project.property("GITHUB_ACTOR") as String else ""
                    password = if (project.hasProperty("GITHUB_TOKEN")) project.property("GITHUB_TOKEN") as String else ""
                }
            }
        }
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = project.group.toString()
                artifactId = project.extra["publishArtifactId"].toString()
                version = project.version.toString()
            }
        }
    }
}
