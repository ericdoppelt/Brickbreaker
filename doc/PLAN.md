# Game Plan
## Eric Doppelt


### Breakout Variant

There are two variants of Breakout that interest me most.
 
- The first is Centipong. I like this game due to its implementation of moving "creatures" as targets for the bouncer. 
As opposed to lifeless bricks in other games, these creatures make the game feel dynamic and unique by adding lifelike "enemies" to
hunt. Moreover, the implementation of "head shots" further creates a unique style of play, since it entices players to aim for certain
weak spots on the moving creature.

 - The second is Vortex. I like this game simply because it is a refreshing spin on brickbreaker. By allowing the paddle to move entirely
 around the bricks, the user is faced with a greater challenge, since there is more ground to cover when keeping the bouncer in play.
 Moreover, I like the depth of the 3D graphics; however, I know for this assignment the task is based in two dimensions.
 
### General Level Descriptions

I plan on building four different levels for this project. The first three will look like normal brick breaker layouts, with static arrangements 
of bricks. The fourth will be the boss fight, as detailed more below, with one "Boss Brick" near the center of the screen. Here, the first three levels are hyperlinked
to a picture that looks similar to the layout of each level I plan on making. In each level, I plan on having the variety of power ups listed below be available to gain.

- [Level One](https://www.google.com/imgres?imgurl=http%3A%2F%2Fphilippetronis.net%2Fassets%2Fimages%2Frrbbthumb.PNG&imgrefurl=http%3A%2F%2Fphilippetronis.net%2Fwebapps.html&docid=SW8JbWHWls-2GM&tbnid=hsmBQoicvxwxCM%3A&vet=10ahUKEwik3fCvyf_mAhWQq1kKHZLKB6MQMwhJKAQwBA..i&w=1180&h=920&bih=876&biw=707&q=brick%20breaker%20rainbow&ved=0ahUKEwik3fCvyf_mAhWQq1kKHZLKB6MQMwhJKAQwBA&iact=mrc&uact=8):
This will be a generic first level to the breakout game. The arrangement will be a rectangular array of bricks.
The first layer (closest to the paddle) will take one hit to break a brick; the second will take two hits; and the nth layer will take n hits to break each brick.
For the sake of easiness, I plan on having the bricks be located closer towards the top of the screen, so there will be ample time to react.

- [Level Two](https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjOxvXe4f_mAhWSmOAKHR20B0kQjRx6BAgBEAQ&url=https%3A%2F%2Fwww.vectorstock.com%2Froyalty-free-vector%2Fletter-e-made-from-realistic-stone-tiles-vector-4964533&psig=AOvVaw0CaBSl9g-TbsqxjDNznhTt&ust=1578976972843300):
Here, I plan on having the second level be in the shape of an E (since it is my first initial). I plan on having this brick arrangement start closer towards 
the paddle (lower on the screen) to make this level harder than the last. Here, as opposed to before, the bricks will become easier to break as the bouncer moves higher within the window.
That is, the bottom line of the E will be most difficult to break, while the top will be easiest.

- [Level Three](https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.brickbreakerguide.com%2Fwp-content%2Fuploads%2F2010%2F05%2Fbrick-breaker-level-16-diagram.gif&imgrefurl=https%3A%2F%2Fwww.brickbreakerguide.com%2Fbrick-breaker-level-16-walkthrough%2F&docid=9kTrKuieTly6rM&tbnid=BEmwSewhzCyVDM%3A&vet=10ahUKEwigiZGs4v_mAhWrtVkKHXZRA8wQMwhMKAMwAw..i&w=221&h=293&bih=876&biw=1207&q=hard%20black%20berry%20brick%20breaker&ved=0ahUKEwigiZGs4v_mAhWrtVkKHXZRA8wQMwhMKAMwAw&iact=mrc&uact=8)
The last "normal" level will be modelled off of a level from the Blackberry breakout game, which I personally loved to play growing up. Essentially, this level will have
a grouping of breakable bricks that is encased by a protective wall of unbreakable bricks. The only way to access these breakable bricks will be from the top, where there is no protection,
 as seen in the picture.

- Level Four: Here, I have no picture, since this will be the Boss level as described below. This level will have bricks added to it as the level progresses and the Boss loses life.
However, at first, the setup will just be the Boss Brick placed near the center of the screen (and then bricks will be added around it). 

### Bricks Ideas

I have four ideas for brick types, which are detailed below:

- Number of Hits Bricks: These are the standard bricks that take anywhere from 1-7 hits to eliminate.
- Power-Up Bricks: These are bricks that drop power ups when hit. Note that a Power-Up Brick can have multiple hits, and is
in some ways then an extension of the previous type.
- Indestructable Bricks: Bricks that do not break. These can not be cleared and are essentially walls.
- Boss Brick: Found in the final level and detailed further below, this brick has many more hit points than any other, creates shields, and regenerates health.

### Power Up Ideas

I have five ideas for power ups, detailed below: 

- Speed Up/Slow Down Ball: This power up simply increases or decreases the speed of the ball in the game.
- Shrink Paddle/Grow Paddle: This power up will affect the size of the paddle the user has by increasing or decreasing its length.
- Extra Life: Adds a life to the user's total life count.
- Invincibility: The bouncer will bounce off of the bottom wall for a limited period, such that if the player misses the ball, it will not cost him a life.
- Multi-Ball: The bouncer will split into two new balls. 

### Cheat Key Ideas

Below are five cheat keys I plan on implementing:

- L: Allows the user to add lives. After pressing L, the user inputs a number to add to their current number of lives.
- R: Resets the paddle back to its original position.
- 1-4: Typing in one of these numbers will bring the user to the first, second, third, or fourth level respectively.
- D: Makes the ball do absolute damage such that is breaks every brick it hits on the first collision, regardless of the number 
of "hits" it was supposed to take. 
- W: Takes the walls out of the game for the paddle, such that if one goes off the screen to the right they enter it from the left.

### Something Extra

For my something extra, I plan on creating a final boss "fight". That is, after
the first three levels (which are all designed as static layouts with the above bricks), I plan on creating
a fourth and final—level that contains just one "boss brick." This brick will have the following enhanced difficulty
features to make the final level more challenging and dynamic:

- The Boss Brick will have significantly more health than any other regular brick, and as such, to monitor the health of the boss, 
an HP bar will emerge on the final level. This will help the user keep track of his or her progress against the boss.

- The Boss Brick will be able to generate shields during the level once it reaches certain low levels of health. For example, at 50% health (or some other arbitrary number), 
the Boss Brick will be able to create a ring-like shield that forms around itself, created by non-moving blocks that each break upon impact.

- The Boss Brick will regenerate health over time, so its "death" (for lack of a better word) must be swift.

