# ![Logo](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-client/blob/ReadMe-media/images/logo/war_elephant_purple.png) SoPra FS22 - Hannibal Server ![Logo](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-client/blob/ReadMe-media/images/logo/war_elephant_purple_left.png)
<p align="center">
	<img src="https://img.shields.io/github/issues-raw/sopra-fs22-group-16/sopra-fs22-group-16-server"/>
	<img src="https://img.shields.io/github/milestones/progress/sopra-fs22-group-16/sopra-fs22-group-16-server/1"/>
	<img src="https://img.shields.io/github/milestones/progress/sopra-fs22-group-16/sopra-fs22-group-16-server/2"/>
	<img src="https://img.shields.io/github/milestones/progress/sopra-fs22-group-16/sopra-fs22-group-16-server/3"/>
	<img src="https://sonarcloud.io/api/project_badges/measure?project=sopra-fs22-group-16_sopra-fs22-group-16-server&metric=coverage"/>
	<img src="https://sonarcloud.io/api/project_badges/measure?project=sopra-fs22-group-16_sopra-fs22-group-16-server&metric=bugs"/>
	<img src="https://sonarcloud.io/api/project_badges/measure?project=sopra-fs22-group-16_sopra-fs22-group-16-server&metric=vulnerabilities"/>
	<img src="https://sonarcloud.io/api/project_badges/measure?project=sopra-fs22-group-16_sopra-fs22-group-16-server&metric=code_smells"/>
	<img src="https://img.shields.io/github/license/sopra-fs22-group-16/sopra-fs22-group-16-server"/>
</p>
Hannibal is a turn-based strategy game in which players can play against each other over the web. Users can create 1vs1 public or private games without registration. However, it is possible to register to keep records of the games played and compete with other users to be at the top of the leaderboard. Because of the quick nature of the game (you can finish a game in roughly 20 turns). Hannibal is best suited to be played on mobile phones, but can also be played on the Desktop version.  

### Motivation

We held multiple brainstorming sessions as a group and came up with different project ideas. We decided we preferred to create a game as we were keen on playing a game we envisioned and created together. From the beginning, we envisioned the game to be something which is fun to play without learning too many rules, and something people would enjoy playing, even outside the SoPra requirements. Another major point for us was that the game should be collaborative and thus we decided on a turn-based strategy game as this would be appropriate for the scope of the SoPra. While searching for a name, one of our team members proposed Hannibal, as he was a brilliant strategist who lived between 247 and 183 BC. Because Hannibal moved over the Alps with elephants, we also choose the pixel art icon of the war elephant as our logo.


## Table of content

- [Technologies](#technologies)
- [High-level components](#high-level-components)
- [External Dependencies](#external-dependencies)
- [Getting started](#getting-started)
    - [Prerequisites and Installation](#prerequisites-and-installation)
    - [Build](#build)
    - [Run](#run)
    - [Test](#test)
    - [Development Mode](#development-mode)
    - [Debugging](#debugging)
    - [Deployment](#deployment)

- [Illustrations](#illustrations)
- [Roadmap](#roadmap)
- [Authors](#authors)
- [License](#license)
- [Acknowledgments](#acknowledgments)
- [Links](#links)

## Technologies

<img src="https://user-images.githubusercontent.com/91155454/170885686-bd14da8d-5070-49ac-b88d-baa2e20729bf.svg" width="16" height="16" /> [**Gradle**](https://gradle.org/)
                                                                                                 
<img src="https://user-images.githubusercontent.com/91155454/170843203-151000ab-db93-4750-b4f4-ba4060a23d53.png" width="16" height="16" /> [**Java**](https://java.com/)		

<img src="https://user-images.githubusercontent.com/91155454/170842503-3a531289-1afc-4b9c-87c1-cc120d9229ce.svg" style='visibility:hidden;' width="16" height="16" /> [**REST**](https://en.wikipedia.org/wiki/Representational_state_transfer) 	

<img src="https://user-images.githubusercontent.com/91155454/170843632-39007803-3026-4e48-bb78-93836a3ea771.png" style='visibility:hidden;' width="16" height="16" /> [**WebSocket**](https://en.wikipedia.org/wiki/WebSocket) 	

<img src="https://github.com/get-icon/geticon/blob/master/icons/heroku-icon.svg" width="16" height="16" /> [**Heroku**](https://www.heroku.com/)		

<img src="https://github.com/get-icon/geticon/blob/master/icons/github-icon.svg" width="16" height="16" /> [**GitHub**](https://github.com/)	

## High-level components

The high-level architecture follows the [Repository-Service Pattern](https://tom-collings.medium.com/controller-service-repository-16e29a4684e5). It promotes the separation of concerns with the introduction of three entities: Controller, Service, and Repository. It allows the developer to reuse or create reusable POJOs as models.
It facilitates testing of a particular layer since it allows the developer to mock any layer below or above it.

According to the above mentioned pattern a lot of work is executed in the [LobbyService](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-server/blob/readme/src/main/java/ch/uzh/ifi/hase/soprafs22/service/LobbyService.java) and therefore, it depicts a main component. A lobby represents the meeting point for players, the possibility to change the player's name, and the starting point for a game. Whenever all users are set, the game will start. So, the LobbyService can be seen as the interface between the actual game and all necessary administration logic.

Unsurprisingly for a game, another major part of the logic is located in the [GameService](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-server/blob/readme/src/main/java/ch/uzh/ifi/hase/soprafs22/service/GameService.java). After all user requests are digested by the respective controllers, game related actions are forwarded to the GameService which takes care of the main options for a play unit: attack, move, and surrender. It triggers the underlying models correspondingly, handles and responds to requests by the controller. Besides that, it also triggers the creation of statistics as soon as a game is finished.

And last but not least, the [Game](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-server/blob/readme/src/main/java/ch/uzh/ifi/hase/soprafs22/game/Game.java) class also bundles multiple functionalities. The Game combines multiple elements to the actual game. It consists of the game map, the game units, the players that control the units, and makes adjustments according to settings like the game mode (casual or ranked) or the game type (1vs1 or - in the future - 2vs2). It also calculates the health points after an attack taking the counter attack into account, and informs about the ending of a game.


## External Dependencies	

<img src="https://user-images.githubusercontent.com/91155454/170843438-4e721d42-5d97-4126-9739-ce049d0d8701.png" style='visibility:hidden;' width="16" height="16" /> [**Spring**](https://spring.io/) 	

<img src="https://user-images.githubusercontent.com/91155454/170887616-39d92726-e081-45a4-8192-11f41297c98c.png" style='visibility:hidden;' width="16" height="16" /> [**JUnit**](https://junit.org/) 	

<img src="https://user-images.githubusercontent.com/91155454/170843395-534f90bd-793d-477d-8626-4d8015c6041a.png" style='visibility:hidden;' width="16" height="16" /> [**Hibernate**](https://hibernate.org/) 		

## Getting started
<p>
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.
</p>

### Prerequisites and Installation
Follow the instruction guide for the [Hannibal server](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-server) and [Hannibal client](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-client).

Get the server

```bash
git clone https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-server.git
```
and open the project with an IDE of your choice.

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

A test coverage over 85% with JUnit 5 was achieved with unit tests for the POJOs, the controller layer, and the service layer, additional integration tests for the service layer, and integration tests for the repository layer.

### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

```bash
./gradlew build --continuous
```

and in the other one:

```bash
./gradlew bootRun
```

### Debugging

To configure a debugger for SpringBoot's Tomcat servlet (i.e. the process you start with `./gradlew bootRun` command),
do the following:

1. Open Tab: **Run**/Edit Configurations
2. Add a new Remote Configuration and name it properly
3. Start the Server in Debug mode: `./gradlew bootRun --debug-jvm`
4. Use **Run**/Debug"Name of your task"
5. Set breakpoints in the application where you need it
6. Step through the process one step at a time

This launches the test runner in an interactive watch mode.
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### Deployment
After each commit to the master branch, automatic Github Actions get executed which deploy our application to [Heroku](https://www.heroku.com/).

## Roadmap
<p align="center">
<img src="https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-client/blob/ReadMe-media/images/Illustrations/Hannibalroadmap.png" width="50%"/>
</p>

## Authors

* **Luis Torrejón Machado**  - [luis-tm](https://github.com/luis-tm)
* **Paul Luley**  - [paolovic](https://github.com/paolovic)
* **Maria Korobeynikova** - [mkorob](https://github.com/mkorob)
* **Hilal Çomak** - [hilalcomak](https://github.com/hilalcomak)
* **Alessio Brazerol** - [apple00juice](https://github.com/apple00juice)

## License
This project is licensed under [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html), which guarantees end users the freedoms to run, study, share and modify the software.

## Acknowledgments
* This project is based on the [SoPra FS22 - Server Template](https://github.com/HASEL-UZH/sopra-fs22-template-server)
* Thanks to **Melih Catal** - [melihcatal](https://github.com/melihcatal) who supported us as a Tutor and Scrum Master during this project.

## Links
* [Hannibal Client Website](https://sopra-fs22-group-16-client.herokuapp.com/)
* [Hannibal Server Website](https://sopra-fs22-group-16-server.herokuapp.com/)
* [SonarCloud](https://sonarcloud.io/organizations/sopra-fs22-group-16/projects)
* [Issue tracker](https://github.com/sopra-fs22-group-16/sopra-fs22-group-16-client/issues)
