## Design of Breakout

###Programmer: Eric Doppelt
Created the entire program from scratch. Beginning by designing a plan for the game (as outlined in the PLAN.md file),
I then created the following classes:
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

Coming into the project, the two features that I wanted to make easiest to implement and add to the game
were Bricks and PowerUps. I did not place much emphasis on having a flexible way to implement new Paddles or Bouncers.
 
In retrospect, I didn't think much about creating flexibility in paddle variations or bouncer variations. This was likely because 
in the PLAN.md document, we were prompted to focus on different types of Bricks and Power Ups that we could implement in our game. However,
if I were to do the project again, I likely would have given more time and attention to this, since creating multiple Paddle and Bouncer classes
that extend an abstract class would have been reasonable in the given time period, and they would have added flexibility to my code.

In terms of Bricks, I wanted to make it easy to add different types of Bricks. The three bricks I have created here are Permanent Bricks, Powered Bricks,
and Normal Bricks. However, since all of these Bricks at least extend an abstract Brick class, it would be possible to create a new class for say, a Boss Brick, that
would extend this same class and therefore easily be implemented in the Main program.

I further wanted it to be easy to add multiple power ups to the game. Unfortunately, I ran out of time before finishing the power up implementation,
so as of now, the only powerups included in the game are: shrink paddle, grow paddle, and speed up ball.

Another feature that I wanted to add to the game was a level of flexibility in the window size. I wanted the size of the Bricks to adapt
if the frame was stretched either horizontally or vertically. Initially, I wanted the bricks to change if a user manually expanded the window. However,
after running into some trouble, I instead decided that the game would configure to the initial shape of the frame during the game's setup. Once the game begins to run,
that is, the size is constant, so expanding the window creates bugs.
 
 ### High-Level Design

To describe the game, it makes sense to describe the individual classes first that represent objects on the breakout screen,
and then describe how the Main class incorporates each object to create the game itself.

The first class to mention is the *Paddle* class, since this is the only object in the gameplay that is impacted by user input. The Paddle represents
a rectangle that appears on the bottom of the screen and is controlled by using the right and left arrow keys on the keypad. The user manipulates
the position of the paddle with these keys to keep the *Bouncer* in play.

The Bouncer is the class that represents the ball used in the game. The Bouncer is simply a Circle that moves around the screen, bouncing off of objects that it hits.
If the Bouncer falls off of the screen, its position is reset to a point that is in the horizontal middle of the screen, and directly above the Paddle. The bouncer has horizontal
speed and vertical speed, which dictate how fast it moves around in the x and y directions. When the bouncer hits an object from the top or bottom, its vertical direction is flipped, and when it hits
the side of an object, its vertical direction is flipped.

The objects that bouncers interact most with are *Bricks*. I mentioned earlier that creating an easy method of adding Brick's to the game was important to me when designing my project.
I aimed at creating this flexibility by creating an abstract Brick class. This simply represents the actual Brick object that is on the screen,
and has coded methods that return information on the Rectangle Shape used to show the Brick. Methods include getHeight() and getX() which return the height
and x location of the top left corner of the Brick, respectively. Each brick shares these functions and is similar in this regard.

The difference between Bricks then comes from the two abstract methods in the Brick class. The first method is hasHitsLeft(), which simply
returns a boolean representing if a brick has hits left. If this boolean is false, this signifies that the brick should be terminated. The second method, handleHit(), encodes
the rules that a brick follows when it is hit by a bouncer. These two methods are coded in the extensions of this abstract class, and these alone dictate the variation in Bricks.

The three classes that extend the Brick class are *NormalBrick*, *PoweredBrick*, and *PermanentBrick*. The *NormalBrick* class is aptly named, as it imply describes the
stereotypical brick used in a breakout game. The Brick has a finite number of hits, and as such, its hasHitsLeft() method returns whether its hits left is equal to zero.
Its handleHit() method reduces its number of hits left, and then recolers the brick depending on how many hits it has left. In this game, a red brick has one hit left, an orange brick
has two hits left, and so forth up to a violet brick (the last color in the rainbow), which has seven hits left. Upon termination, the Brick simply disappears from the screen.

The *PoweredBrick* extends the NormalBrick class and as such has all of the functionality it does. The only difference between these two classes is that a PoweredBrick also contains
a PowerUp object as an instance variable, which appears on screen when the Brick terminates (which occurs when myHitsLeft() == 0). The *PowerUp* class represents a rectangle that appears on screen when a PoweredBrick is broken. When a paddle catches a PowerUp (by intersecting the PowerUp rectangle with the Paddle rectangle),
a change is made to the game. As of now, there are three types of changes that can be made: growing the paddle, shrinking the paddle, and speeding up the bouncer. When a PowerUp is caught or falls off of the screen, it then disappears from gameplay.

The third type of Brick is the *PermanentBrick*, which essentially acts as a wall for the bouncer to go off of. In this way, the PermanentBrick always has hits left, and when
it is hit by a bouncer, nothing happens to the brick itself. This brick is included on the third level, to shield normal bricks from the bouncer.




