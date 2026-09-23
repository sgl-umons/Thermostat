# Thermostat App GUI

This project is developed and used as part of a Software Modeling course by the Software Engineering Lab of University of Mons, Belgium.

It provides an example of a statechart-based Thermostat controller in Java. The statechart was created with Yakindu Statechart Tools and Java code was generated
from the statechart. A Swing GUI executes the behaviour of this generated statechart code.

## Build automation 

The project uses the **gradle** build automation tool to automate the compilation, testing and execution of the application. Use
  - `gradle build` to compile, run unit tests and generate the jar file
  - `gradle javadoc` to generate javadoc documentation
  - `gradle run` to execute the application
  - `gradle jar` to generate the jar file
  - `gradle test` to run the unit tests
  - `gradle classes` to compile

## Development Environments

There are multiple ways to set up your development environment: you can use **DevContainer**, **Nix**, or configure it manually.

Due to the graphical nature of the application (Java Swing GUI), running the interface may not be possible within **DevContainer**. However, you can still
use **DevContainer** to run tests and generate the JAR file. If you need to interact with the GUI, consider using **Nix** or a manual setup.

### Using DevContainer (Container based)

[DevContainer](https://containers.dev/) is a feature available for [VSCode](https://code.visualstudio.com/) and
[Jetbrains](https://www.jetbrains.com/) IDEs that allows you to develop seamlessly in a containerised environment. This approach will setup your
development environment using containers.

1. Ensure you have Docker installed on your machine. It might work with Podman    too, but it is not tested yet.
2. Install the DevContainer extension
   1. For VSCode:
      [Dev Containers extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)
      ([documentation](https://code.visualstudio.com/docs/devcontainers/containers))
   2. For Jetbrains IDEs: nothing to do, it is supported by default
      ([documentation](https://www.jetbrains.com/help/idea/connect-to-devcontainer.html))
3. Open the project in your IDE, and you should see a notification to reopen the    project in a container, click on it.
4. Once the project is reopened in the container, you need to wait a bit for the container to build and start.
5. Inside the container you can start using the gradle commands. All the necessary versions of all tools and dependencies are already installed in the container.

### Using Nix

This approach will setup a development environment using the [Nix](https://nixos.org) package manager. Unlike DevContainer, no containerisation is used, and the development environment will be installed on your local machine, in **total isolation** from your current system packages.

1. Install Nix package manager: `curl -L https://nixos.org/nix/install | sh`
2. Go to the project root folder
3. Run `nix develop` to create and enter the isolated development environment
4. Start using the gradle commands to compile, test or execute the application
5. Once you are done, exit the Nix development environment by running `exit`

## Version history

In progress:

- statechart-level unit tests
- JUnit tests in Java

Version 0.7 - 23 September 2026 (Tom Mens

- fix gradle deprecation warning

Version 0.6 - 21 February 2025 (Pol Dell'Aiera)

- support for DevContainer and Nix development environments to achieve **reproducible builds**.
  
Version 0.5 - 24 February 2024 (Tom Mens)

- mainly code improvements

Version 0.4 - 4 September 2020 (Gauvain Devillez)

- added gradle build automation tool.

Version 0.3 - 24 August 2020 (Tom Mens, Gauvain Devillez)

- added operation in statechart for measuring outside temperature, and callback
  in Java code
- improved GUI by adding the current time; and changing some button labels
  dynamically

## Contributors

- Tom Mens (versions 0.1, 0.2, 0.3, 0.5)
- Pol DEll'Aiera (support for Nix and DevContainer development environments in version 0.6)
- Gauvain Devillez (changes to Swing GUI in v0.3, adding Gradle support in version 0.4)
- Mohammed Saidi (version 0.0)
