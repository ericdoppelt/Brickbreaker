game
====

This project implements the game of Breakout.

Name: Eric Doppelt

### Timeline

Start Date: January 17th

Finish Date: January 19th 

Hours Spent: 20 (18 coding, 2 reviewing Java)

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
https://stackoverflow.com/questions/10643443/passing-arraylistsubclass-to-method-declared-with-listsuperclass

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
- Changing the size of the window can move the game off the screen
- Changing the level can put the ball "inside" the brick and as such send it to an extreme location on the edge of the new layout.
- Hitting a corner a certain way can make the ball move seemingly randomly. This is rare though.

Extra credit:

Did not have time to implement the Boss Brick, unfortunately.

### Notes/Assumptions

- I decided to have different Brick types be subclasses of a general Brick parent type. Overall, this added
flexibility to my code. However, one problem that emerged is that the Brick object must have a method .handleHit() which runs
when the Brick is collided by the Bouncer. For PermanentBricks, though, this method is empty, since nothing
happens to the Brick when a Bouncer collides with it.

- When a bouncer collides with a brick, the bouncer is set to be one pixel away from the side of the
Brick that the collision took place in. This is not noticeable to the human eye, but technically does break the idea
of continuous movement in brick breaker. I did this to smooth out the gameplay and avoid problems with weird intersections. 

### Impressions

I thought this was a great assignment to get to learn how to use JavaFX. It incorporated a variety of
elements of Java coding (such as I/O, Classes/Subclasses, data protection, and "good" classes/methods).
My only complaint about the project is the time it took, but the responsibility to complete it—regardless
of the time it takes—is on me solely, and I made a mistake by starting so late. Clearly, I am not finished. Many classes
are missing JavaDoc comments, and I did not implement the Splash Screen, Win Screen, or Lose Screen (although I included empty win and loss functions as a shell).

Going forward, I will certainly start these assignments earlier. If I could have another day at this one, I think
I would be able to finish the project in time; however, I know that I do not, so I must learn from this mistake.

