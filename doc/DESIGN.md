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

The objects that bouncers interact most with are **Bricks**. I mentioned earlier that creating an easy method of adding Brick's to the game was important to me
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

The *PoweredBrick* extends the NormalBrick class and as such has all of the functionality aforementioned. The only difference between these two classes is that a PoweredBrick also contains
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
it reads in the starting level's text file and returns an ArrayList of Bricks to add to the game. The Main program then runs through the returned ArrayList of Bricks, and it adds each brick to one of three static instance
variables: allPoweredBricks, allNormalBricks, or allPermanentBricks. These instance variables keep track of the bricks that are left on a given level. The main program also initializes a **Paddle** and **Bouncer** object on the screen. The Paddle
is stored as a Paddle instance variable while the Bouncer is stored to an ArrayList of allBouncers, which contains all the information on the bouncers used in the game. The difference in storage here stems from the fact that the game is set up to 
never have more than one Paddle on the screen, while future variations could have multiple bouncers at work. The Main program further sets the number of lives a user has to beat the game. Every time the bouncer falls of the screen, the Main program deducts a life. If a user runs out of lives, the game is supposed to end; however,
I ran out of time before implementing this functionality.

After adding each object's shape to the JavaFX root (where Bricks and Paddles are represented with Rectangles while Bouncers with Circles), the program then runs a step function for an
infinite period of time. This function updates the state of the scene displayed for each frame. It checks first checks to see if the level has been beaten as determined by having terminated all
of the Powered and Normal bricks (checking if their respective arraylists have size zero). If this has happened, the Main program uses a LevelReader to load the next level. If the level has not been beaten,
the Main program then checks to see if any bouncers have collided with bricks, and if they have, it udpates them according to the rules outlined above in each class. It then checks to see if any PoweredBricks have been
broken. If they have, it drops the **PowerUp** object contained in the brick, which appears on screen as a Rectangle shape and descends towards the Paddle to be caught.
The Main program remembers which PowerUp objects are displayed by adding those on screen to an ArrayList called allPowerUps. 

In each step, the Main Program checks to see if any PowerUps have been caught by iterating through the ArrayList of PowerUps. If any have been caught, they are removed from the screen and ArrayList, and
their change is enacted (by growing/Shrinking the Paddle or speeding up the Bouncer).

The main program also has "cheat keys" implemented in the game, wherein if at any time a users types in a code as detailed in the README file, the following change is added to the scene.  
Unfortunately, since I ran out of time, there is no way to beat the game or lose the game, so the step function runs forever.

## Assumptions 

For clarification purposes, I put my assumptions in a bulleted list:
- Size of Window: This program assumes that the user will not change the size of the window once the program starts running. That is, if one
drags the window, the program will remain fixed in its location and not change to update its new size. The program can, however, change dynamically
if the user changes the variables SIZE_X and SIZE_Y prior to running the program.
- This program assumes that whenever a level changes, a ball will not be in a location that a new brick is placed. In other words,
when adding a brick to the screen, the program does not check to see whether the ball is there, and it instead treats it as if it is a collision.
This can create odd scenarios in which a ball can get pushed to a seemingly random location on a level change. 
A way to fix this would have been to reset the ball to the center on a level change.
- This program assumes that there will never be more than one paddle in the game. This was a conscience choice I made at the beginning of the game. 
Instead of storing Paddles in an ArrayList, I instead stored one as an instance variable (myPaddle).

## New Features

Once again, I have decided to create a bulleted list here for clarity. The following explains how to add various elements.
- To add a **Level** to the game, one simply needs to create a new text file in the Resources folder named LevelX, where X is the level number.
