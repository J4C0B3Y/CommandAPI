# CommandAPI

Powerful multi-platform annotation based command api.

Trusted and used by [Refine Development](https://refinedev.xyz/).

## Features

- Asynchronous execution and tab completion
- Annotation based command registration
- Inbuilt command usage and help handler
- Flexible flags system (eg, --silent)
- Small and lightweight (~100kb)
- Works on all major platforms
- Supports spigot 1.8+

## Support

If you need any assistance using or installing my CommandAPI,
feel free to contact me by either adding me on discord (@J4C0B3Y)
or by creating an issue and explaining your problem or question.

## Installation

Prebuilt jars can be found in [releases](https://github.com/J4C0B3Y/CommandAPI/releases).

> **NOTE:** <br>
> It is recommended to relocate the library to prevent
> version mismatches with other plugins that use the api.

### Maven & Gradle

- Replace `PLATFORM` with your desired platform. (eg, bukkit).
- Replace `VERSION` with the latest release version on GitHub.

```kts
repositories {
    maven("https://repo.j4c0b3y.net/public/")
}

dependencies {
    implementation("net.j4c0b3y.CommandAPI:PLATFORM:VERSION")
}
```

```xml
<repositories>
    <repository>
        <id>j4c0b3y-public</id>
        <url>https://repo.j4c0b3y.net/public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>net.j4c0b3y.CommandAPI</groupId>
        <artifactId>PLATFORM</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

### Building

1. Clone this repository and enter its directory.
2. Run the intellij build configuration by clicking the top right icon.
3. Alternatively you can run `gradle classes shadow delete copy install`.
4. The output jar files will be located in the `jars` directory.

## Usage

Coming soon, for now message me on discord for help.

### Want more?

Each and every class in my command api has detailed javadocs explaining what
methods and variables are used for, and functionality of internal methods.

> Made with ❤ // J4C0B3Y 2024