game
====

This project implements the game of Breakout.

Name: Eric Doppelt

### Timeline

Start Date: January 17th

Finish Date: January 19th 

Hours Spent: 17 (15 coding, 2 reviewing Java)

### Resources Used

https://www.tutorialspoint.com/javafx/javafx_architecture.htm
https://www.w3schools.com/java/java_user_input.asp
https://books.trinket.io/thinkjava2/chapter14.html

A ton of Stack Overflow help. Here are all the posts I remember visiting:
https://stackoverflow.com/questions/20594473/nullpointerexception-using-scanner
https://stackoverflow.com/questions/811851/how-do-i-read-input-character-by-character-in-java
https://stackoverflow.com/questions/2674554/how-do-you-know-a-variable-type-in-java
https://stackoverflow.com/questions/43240007/remove-shape-without-knowing-its-reference-javafx
https://stackoverflow.com/questions/20033512/how-to-make-ball-bounce-off-an-object-in-javafx
https://stackoverflow.com/questions/18418125/java-brickbreaker-paddle-collision-detection
https://stackoverflow.com/questions/15690846/java-collision-detection-between-two-shape-objects
https://stackoverflow.com/questions/20840587/how-to-use-intersect-method-of-node-class-in-javafx
https://stackoverflow.com/questions/41313284/javafx-what-is-getboundsinlocal-getboundsinparent-method
https://stackoverflow.com/questions/30923909/unable-to-run-java-code-with-intellij-idea

### Running the Program

Main class: Main.java

Data files needed: 

In the resources folder:
- Level1.txt
- Level2.txt
- Level3.txt

Key/Mouse inputs: 
- Right Arrow: moves paddle right
- Left Arrow: moves paddle left
- Spacebar: sends ball off if reset at center

Cheat keys:

- R: reset the paddle in the center
- W: allows the paddle to move through walls
- L: adds a life to the user's lives
- 1: toggles level 1
- 2: toggles level 2
- 3-9: toggles level 3

Known Bugs:
- PowerUps do not work/were not implemented, so when the paddle catches a PowerUp, nothing happens.
- Changing the size of the window can move the game off the screen

Extra credit:

Did not have time to implement the Boss Brick, unfortunately.

### Notes/Assumptions

- I tried to have different Brick types be subclasses of a general parent type. While
this made sense logically, since they all represent a similar object, I ran into trouble implementing the 
.handleBrick() and .hasHitsLeft() methods. I have no experience with abstract classes, and this created a lot of
trouble for me. There are useless methods that are needed to have the subclass function properly,
and I do not know how to resolve that at the moment.

I have a sharp deadline at 11:30 that I must make, so I must stop working on the project
and submit what I have. Going forward, I would make the root private in the Main, fix the extensions of the
Brick class, add PowerUps, and add the Start/Win/Lose screens. 

### Impressions

I thought this was a great assignment to get to learn how to use JavaFX. It incorporated a variety of
elements of Java coding (such as I/O, Classes/Subclasses, data protection, and "good" classes/methods).
My only complaint about the project is the time it took, but the responsibility to complete it—regardless
of the time it takes—is on me solely, and I made a mistake by starting so late. If I could have another day at this, I think
I would be able to fix it; however, I know that I do not.

