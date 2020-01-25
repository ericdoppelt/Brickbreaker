## Design of Breakout

### Programmer: Eric Doppelt
I created this entire program from scratch. Beginning by designing a plan for the game (as outlined in the PLAN.md file),
I then created the following classes, which are detailed below:
 - Bouncer
 - Brick
 - LevelReader
 - Main
 - NormalBrick
 - Paddle
 - Permanent Brick
 - PoweredUpBrick
 - PowerUp

### Design Goals


The general design of the game, of course, was to create a unique Brick Breaker game, as defined by the specifications given [here](https://www2.cs.duke.edu/courses/compsci308/current/assign/01_game/part2.php).
In this, I wanted to create a Breakout game with multiple levels, wherein a player uses a Paddle and Bouncer to break bricks, beating a level when all of the bricks are destroyed.

To begin planning, I first created my PLAN.md file found in the doc folder. Based off of that exercise, I decided to specifically focus on the ability to add variations of Power Ups, Levels and Bricks to the game.
This is because the PLAN.md file focused on those three elements, and while I should have given more thought to other aspects (like Paddle and Bouncer) described in the Game specifications, I really did not. 


Since my attention was focused on Bricks, PowerUps, and Levels, the following bullet points describe those three design goals first. They then mention general comments after that.

- In terms of Bricks, I wanted to make it easy to add four different types of Bricks to the game. The first three bricks I envisioned were **Permanent Bricks**, **Powered Bricks**,
and **Normal Bricks**, all of which are described aptly by their name. The Normal Brick is the classic Breakout object with a finite number of hits; the Powered
brick is "Normal" but drops a PowerUp on termination; and the PermanentBrick is essentially a brick acting as a wall. The final brick I envisioned was a **Boss Brick** which
would act like a Normal Brick, but would take vastly more hits to break the brick. Moreover, I envisioned it regenerating health and spawning bricks around it for protection. While these were the first
types of Bricks I wanted to add, I further wanted to create a design that was flexible with adding new types of Bricks (such as a simple regenerating brick, for example).

- In terms of Power Ups, I wanted it to be easy to add five initial Power Ups to the game. These would be embedded in a Powered Brick and drop once the brick was terminated. The
first Power Up was to **Shrink/Grow The Paddle**. The second was to **Speed Up/Down The Ball**. The third was to make the player **Invincible**, such that the ball would bounce off of the bottom wall.
The fourth was to **Add A Life** to the user's  lives left. The fifth was to **Add a Bouncer**. Like the Bricks, while I had these Power Ups in mind, I further
wanted to create a design that would  support adding other types of Power Ups as well.

- In terms of **levels**, my goal was to create a system that could read in levels using a text file and construct a scene based on the file. An example of such
a file can be found in the Resources folder. While I had specific layouts envisioned in my PLAN.md document, my goal for this function was to be able to create any type of
level that could be articulated in the grid-style text format found in the Resources folder.

- Due to the above design goals, the only functionality I envisioned for the **Bouncer** class was the ability to bounce off of walls, change speed, and have multiple normal bouncers within a game. I never envisioned
having mutiple variations of bouncers with different characteristics. Likewise, the only functionality I saw for the **Paddle** class was to grow and shrink in size. I never anticipated having multiple types of Paddles or even multiple Paddles in effect at once.

 ### High-Level Design

To describe the game, it makes sense to describe the individual classes first that represent objects on the breakout screen,
and then describe how the Main class incorporates each object to create the game itself.

The first class to mention is the **Paddle** class, since this is the only object in the gameplay that is impacted by user input. The Paddle represents
a rectangle that appears on the bottom of the screen and is controlled by using the right and left arrow keys on the keypad. The user manipulates
the position of the paddle with these keys to keep the **Bouncer** in play.

The Bouncer then is a class that represents the ball used in the game. The Bouncer is simply a circle that moves around the screen, bouncing off of objects that it hits.
If the Bouncer falls off of the screen, its position is reset to a point that is in the horizontal middle of the screen, and directly above the Paddle. The bouncer has horizontal
and vertical speed, each which dictate how fast it moves around in the x and y directions. When the bouncer hits an object from the top or bottom, its vertical direction is flipped.
When it hits the side of an object, its horizontal direction is flipped.

The objects that bouncers interact most with are **Bricks**. I mentioned earlier that creating an easy method of adding Bricks to the game was important to me
when designing my project. I attempted to create this flexibility by creating an abstract Brick class. This class simply encodes the information for the actual Brick object
that appears on the screen. It contains a Rectangle Shape instance variable that is added to the game to show the Brick. The class further has methods that return information
on the Rectangle, such as getHeight() and getX() which return the height and x location of the top left corner of the Brick, respectively. Each brick then shares these functions
and is similar in this regard.

The difference between types of Bricks comes from two abstract methods in the Brick class. The first method is hasHitsLeft(), which simply
returns a boolean representing if a brick has hits left. If this boolean is false, this signifies that the brick should be terminated. The second method, handleHit(), encodes
the rules that a brick follows when it is hit by a bouncer. These two methods are coded in the extensions of this abstract class, and these alone dictate the variation in Bricks.

The three classes that extend the Brick class are **NormalBrick**, **PoweredBrick**, and **PermanentBrick**. The **NormalBrick** class is aptly named, as it describes the
stereotypical brick used in a breakout game. The Brick has a finite number of hits, and as such, its hasHitsLeft() method returns whether or not its hits left is equal to zero.
Its handleHit() method reduces its number of hits left by 1 and then recolers the brick depending on how many hits it has left. In this game, a red brick has one hit left, an orange brick
has two hits left, and so forth up to a violet brick (the last color in the rainbow), which has seven hits left. Upon termination, the Brick disappears from the screen after having 0 hits left.

The **PoweredBrick** extends the NormalBrick class and as such has all of the functionality aforementioned. The only difference between these two classes is that a PoweredBrick also contains
a PowerUp object as an instance variable, which appears on screen when the Brick terminates (occurs when hits left equals 0). The **PowerUp** class represents a rectangle that appears
on screen when a PoweredBrick is broken. When a paddle catches a PowerUp (by intersecting the PowerUp rectangle with the Paddle rectangle),
a change is made to the game. As of now, there are three types of changes that can be made: growing the paddle, shrinking the paddle, and
speeding up the bouncer. When a PowerUp is caught or falls off of the screen, it then disappears from gameplay.

The third type of Brick is the **PermanentBrick**, which essentially acts as a wall for the bouncer to go off of. In this way, the PermanentBrick always has hits left, and when
it is hit by a bouncer, nothing happens to the brick itself. This brick then never terminates and never has any action occur during collisions.

Before running the game in the Main program, a user must design levels to play. This is done by adding text files to the Resources folder with the notation LevelX.txt, where X is the
integer representation of a level. This notation is crucial, since the **LevelReader** class reads in each level using this exact notation. The LevelReader object is constructed with an integer representing
the level to load, alongside two values representing the length and width of a frame. The constructor than finds the level's text file in the Resources folder and loads the file. If the integer is a number that does not correspond
to a level in the game, a FileNotFoundException is caught. The LevelReader also has one public method, readLevel() which returns an ArrayList of every brick for a given level. 

Lastly, the Main program incorporates all of these classes to run the game. The Main program begins by creating a Scene for the first level using JavaFX. The **LevelReader** class is used to load this scene, as
it reads in the starting level's text file and returns an ArrayList of Bricks to add to the game. The Main program then runs through the returned ArrayList of Bricks, and it adds each brick to one of three static ArrayList instance
variables: allPoweredBricks, allNormalBricks, or allPermanentBricks. These instance variables keep track of the bricks that are left on a given level. The main program also initializes a **Paddle** and **Bouncer** object on the screen. The Paddle
is stored as a Paddle instance variable while the Bouncer is stored to an ArrayList of allBouncers, which contains all the information on the bouncers used in the game. The difference in storage here stems from the fact that the game is set up to 
never have more than one Paddle on the screen, while future variations could have multiple bouncers at work. The Main program further sets the number of lives a user has to beat the game. Every time the bouncer falls of the screen, the Main program deducts a life. If a user runs out of lives, the game is supposed to end; however,
I ran out of time before implementing this functionality.

After adding each object's shape to the JavaFX root (where Bricks and Paddles are represented with Rectangles while Bouncers with Circles), the program then runs a step function for anf
infinite period of time. This function updates the state of the scene displayed for each frame. It first checks to see if the level has been beaten as determined by having terminated all
of the Powered and Normal bricks (checking if their respective arraylists have size zero). If this has happened, the Main program uses a LevelReader to load the next level. If the level has not been beaten,
the Main program then checks to see if any bouncers have collided with bricks, and if they have, it udpates them according to the rules outlined above in each class. It then checks to see if any PoweredBricks have been
broken. If they have, it drops the **PowerUp** object contained in the brick, which appears on screen as a Rectangle shape and descends towards the Paddle to be caught.
The Main program remembers which PowerUp objects are displayed by adding those on screen to an ArrayList called allPowerUps. 

In each step, the Main Program checks to see if any PowerUps have been caught by iterating through the ArrayList of PowerUps. If any have been caught, they are removed from the screen and ArrayList, and
their change is enacted (by growing/Shrinking the Paddle or speeding up the Bouncer).

The main program also has "cheat keys" implemented in the game, wherein if at any time a user types in a code as detailed in the README file, the following change is added to the scene.  
Unfortunately, since I ran out of time, there is no way to beat the game or lose the game, so the step function runs forever.

## Assumptions 

For clarification purposes, I put my assumptions in a bulleted list:
- Size of Window: This program assumes that the user will not change the size of the window once the program starts running. That is, if one
drags the window, the program will remain fixed in its location and not change to update its new size. The program can, however, change dynamically
if the user changes the variables SIZE_X and SIZE_Y prior to running the program.
- This program assumes that whenever a level changes, a ball will not be in a location that a new brick is placed. In other words,
when adding a brick to the screen, the program does not check to see whether the ball is there, and it instead treats it as if it is a collision.
This can create odd scenarios in which a ball can get pushed to a seemingly random location on a level change. Specifically, since the Bouncer hitsBrick() method
checks to see if a Bouncer hits a brick from the right first, if a Bouncer is in the location of a brick on a level change, the Bouncer often gets pushed right until there are no more bricks.
A way to fix this would have been to reset the ball to the center on a level change.
- This program assumes that there will never be more than one paddle in the game. This was a conscience choice I made at the beginning of the game. 
Instead of storing Paddles in an ArrayList, I instead stored one as an instance variable (myPaddle).
- This program assumes that the Bricks will always be formatted into a 8x8 grid centered in the screen, as set up by the LevelReader. If there is a line break in this formatting, also, the program will throw an InvocationTargetException and not run. The last line of the grid
can be left off, however, and there will be an empty row at the base of the grid then.
- This program assumes that each ball only does one damage per hit. This contradicts a cheat key given in the PLAN file, and that is adressed below. Note that substantial changes would need to be
made to fix this assumption.

## New Features

Once again, I have decided to create a bulleted list here for clarity. The following explains how to add various elements that I have already incorporated into the game:

- To add a **Bouncer** to the game, one simply needs to add a new Bouncer object to the ArrayList allBouncers in the Main program. If the bouncer is set prior to the game's start,
it can be added in the setBouncer() method. For clarity purposes, I have added a new line in Main (line 135) that shows this functionality on the commit to Main that says "Added second bouncer". Likewise,
if one wanted to add a bouncer via a PowerUp, then the addPowerUp() method could call a new method that adds a bouncer to this ArrayList.
- To add a **Paddle** to the game, one sets the instance variable myPaddle to their desired Paddle. This game does not support multiple Paddles, since I have never played
a Brick Breaker game wherein two paddles are controlled. With that said, in theory, the code could be changed to have an ArrayList of Paddles (like the ArrayList of Bouncers) wherein
every Paddle in the ArrayList would be updated on a left or right arrow key input.
- To add a **Level** to the game with **Bricks**, one must create a text file in Resources with the name LevelX, where X is the level number. The file must follow the format given in the first 3 levels,
wherein the user inputs an 8x8 grid of bricks. Each brick is represented by two characters. If the characters are "00" in instructs the computer to not create a brick.
If the first character is 'B', it tells the computer to create a NormalBrick with a number of hits left equal to the second integer. If the first letter is P, the computer is told to create a Permanent Brick. The second number
is arbitrary but should be included for formatting purposes. Lastly, if the letter is 'G', 'S, or 'I', the computer creates a PoweredBrick object that either grows the paddle, shrinks the paddle, or speeds up the ball, respectively. The second
number corresponds to the number of hits the brick has. 
- Note that a **Brick** is intended to only be added to the game via the LevelReader. A user should never change the Main code to add a Brick to an ArrayList.
- Likewise, note that a **PowerUp**  is intended to only be added to the game via a PoweredBrick via the LevelReader, as aforementioned.

The following list describes how to add new elements that have not yet been incorporated into the game: 
- To add the **Boss Brick** as described in the PLAN.md file, one would create a new BossBrick class that extends the NormalBrick class. By doing so, the Boss would inherit
the ability to have a finite number of lives. The colorBrick() method would have to be updated to color a Brick with more hits than seven. The Boss Brick would also have to add to the handleHit() method,
since at certain health levels, the Brick would need to create new Bricks (via a LevelReader creating an ArrayList of Bricks to be added, as dictated by a new text file). Moreover, the Boss Brick would need an increaseLife() method, where its number
of hits left would increase if the Brick was not hit in a given time period. This could be updated in the step() function by tracking the number of frames it takes to hit the brick, and after a certain amount of frames, begin calling the
increaseLife() method until the Brick is hit. Lastly, the Boss Brick would need its own ArrayList in the Main method, and the Main method would need to check the Boss Brick ArrayList just as it checks every other Brick ArrayList.
- To add the **Power Ups** described in the PLAN.md file, one would only need to create two changes to the program for each Power Up. First, in the Level Reader, the Power Up needs a corresponding character
to input for a Powered Bricks text representation. A Multi-Ball brick could be represented by an 'M' in the LevelReader, for example. Then, in the Main program, the addPowerUp() method would need a conditional statement for the character, and it would
then call a new method that created the PowerUp's change.
- To add the one cheat key I did not implement, the **Absolute Damage Cheatkey**, significant changes would have to be made. A new class would have to be constructed that extends Bouncer and represents a bouncer with total damage.
The functionality would be similar to a bouncer, except on collisions a bricks myHitsLeft would be set to zero automatically (excluding PermanentBricks). Each Bricks handleHit() method would need to change to consider the type of
Bouncer impacting it, which would require significant revisions. If these changes were implemented though, the Absolute Damage cheatkey could be added to the handleKeyInput method, and upon calling, it could replace every bouncer in
allBouncers with absolute bouncers, adding them back to th allBouncers ArrayList.
- A **Win Screen** could be implemented by having the step() method in Main check the current level (via myLevel instance variable). By setting the number of levels as another instance variable, it could compare myLevel to a
variable representing the number of levels. If myLevel was greater, the step() could call the win() function, which would update the scene to display Text saying "Winner!" The method would then keep the user stuck on this Scene until he quit the program. This could be done
by keeping the program stuck in a while(true) loop on the win() method, such that it never returned back to the step() method.
- A **Loss Screen** could be implemented similarly, by having the handleFallOff() function check the instance variable myLives. If this is equal to zero after being decreased by one, the program can call a lose screen  which similarly creates a Scene showing Text saying "You Lost!". Using  a
similar while (true) loop, this could prevent the game from going back to the step function. Note that for efficiency, this could be called in handleFallOff(), since a loss can only occur after the ball exits the screen.
- A **Splash Screen** could be activated at the end of the start() method, where a method called runSplashScreen could be added after the animation.play() line. Here, the method could add the Splash Screen Text to the root via Text JavaFX Objects. The method
 could loop until a boolean variable endSplashScreen is set to true via a .setOnAction method in a Button on screen. Once this occurs, the screen can be cleared and the step() function can run.
 - A *Status Display for Lives* could be added to the game by adding Text to the screen in the setUpGame() method. By adding Text to the screen, one could add two Text Nodes. The first would say: "Lives Left: " + myLives. In the handleFallOff() method, this Text could be updated if a life is lost. This would
 occur by removing the Text Node and adding a new one with the same expression ("Lives Left: " + myLives) since myLives will have changed.
 - A *Status Display for Score* would take more effort. Since I did not incorporate score, one would need a new private instance variable named myScore. This would start at 0, and would be added to the frame in a similar fashion as the lives display in the setUpGame() method.
 However, a substantial change would need to be implemented, where every time a bouncer terminated a brick, the score variable would need to be changed. This could be implemented by having every Brick have a coded score as an instance variable. When the brick terminates in the tryRemoveBrick method in Main then,
 if the Brick is removed, the score variable could be set to += tempBrick.getScore(). Then, a method could be called to updateScore(), wherein like before, the Text node would be removed and added with the same expression: "Score = " + myScore.
 - Lastly, I only currently have two Paddle variations. The first is the W cheat key which allows a Paddle to wrap around the screen. The second is the Grow/Shrink power up. If I wanted to encode a **third Paddle variation**, I could have the Paddle change the direction of the bouncer depending on where it hits. I could do this my changing the
 handlePaddle() method in the Bouncer class. By testing whether the Cricle intersects a Rectangle that represents the left most third of the Paddle, middle third, or right most third, the bouncer's horizontal direction can change. If it hits the left most third, the bouncers
 horizontal direction (myDirectionX) would be set to -1. If it hits the middle third, it would not change. If it hits the right most third, it would be set to 1. The rectangles representing these thirds of the Paddle can be found by simple math, using the idea that Rectangle has the same height as the Paddle and
 a width equal to 1/3 the original Paddles. Then, the coordinate of the top left corner to set the subset of the new Rectangle in the appropriate location for intersection.
 