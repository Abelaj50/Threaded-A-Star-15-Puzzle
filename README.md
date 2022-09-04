# Threaded-A-Star-15-Puzzle
Classic game of 15 Puzzle with threaded AI path finding using the A-Star search algorithm. CS 342 Project 4, UIC Spring 2021.

## Table of contents
* [General Info](#general-info)
* [Getting Started](#getting-started)
* [Technologies](#technologies)
* [Credits](#credits)
* [Visual Demonstration](#visual-demonstration)

## General info
This project is the fourth project for CS 342 (Software Design) at the University of Illinois at Chicago, Spring 2021. Our task was to develop a JavaFX program that allows the user to attempt to solve different 15 puzzles (more on that below). Our program was required to have a minimum of 10 unique (and solveable) 15 puzzles to solve. If the user can not solve it and wants to see the solution, animated move by move, they can choose to have the AI puzzle solver figure it out with one of two heuristics. Out program was required to use the A\* search algorithm with the ability to use two different heuristics.

## Getting Started
To begin with, you will want to make sure you have all technologies needed. You can use your IDE of choice but Eclipse is **strongly** suggested for this project.
* Java 8: https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html
* Maven: https://maven.apache.org/install.html
* JavaFX: https://gluonhq.com/products/javafx/

Once you are ready, import the project into your IDE and run as a Java Application. You will be presented with a landing window which allows you to select between running the server or playing as a client. You can run the application again to bring about another landing screen to simultaneously run the server or client applets, and even load more clients. 
The server is able to set the port connection, see how many clients are currently playing (based on their client number), and which clients have either connected or disconnected. The client plays the game and is able to make choose a category to guess from and make guesses. This is the basis of the game, and further details are documented in [General Info](#general-info).

***NOTE:*** If you are having issues with installation in Eclipse, the two most common fixes are:
* Run fix: https://stackoverflow.com/a/57747229
* JavaFX fix: https://youtu.be/LOaUsrjJ_rs?t=210
    
## Technologies
Project is created with:
* Eclipse version 2022-06 (4.24.0)
* Apache Maven version 3.8.4
* JavaFX version 12.0.1
* Java SE 8

## Credits
All credits for the project idea go to Professor Mark Hallenbeck. All credits to technologies used are given to their owners and all items specified in their respective licenses are adhered to throughout this project.  

## Visual Demonstration
The following link leads to a visual demonstration of the project.
* https://youtu.be/X_FTpaL8Tw4
