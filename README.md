# RogueChess

## Objective:
"I Can’t Believe It’s Not Chess" (Name WIP) is a real-time dungeon crawler heavily inspired by the game of chess. Elements such as combat, items, weapons, and an open, explorative game space are combined with the movement patterns and strategy of chess to create an adventurous and exciting twist on the classic chess formula. 
The object of the game is to traverse through multiple pre-designed boards filled with enemies, peril, and loot from the entrance to the exit engaging in combat and exploration along the way. Throughout the multiple boards, the player will gather items and gear to add challenge and diversity their encounters.


The entirety of the game board is divided into square sections called spaces. These spaces contain either floors or walls. 
Floor spaces are where all the activity of the game takes place. Floors can be empty or occupied by the player, enemies, or environmental objects such as chests and doors.
All objects on the board take up a single Floor space and each Floor space can only be occupied by a single object.
Wall spaces act as borders of the game field. Walls cannot contain any of the aforementioned objects and are not a valid playable space.
The board is built from a matrix that allows for simple, on the fly creation and editing of the board and its contents.


## Player
The player character is the means by which they player can interact with the game board. Any interactions the player has with the board, enemies, and objects is by moving into or around them. The player can move across one floor space on the board in any of the potentially eight directly adjacent and unoccupied floor tiles. 
Movement Timer:
As the player moves on a timer, after a movement action has been completed, there is a slight delay between the previous movement action and performing a new movement action. This action is comprised of a .15 second long movement animation as the player character shifts from one space to the next, and a 1 second long cooldown time after the player moves onto the space, for a total time of 1.15 seconds elapsed between movement actions. 
*This movement should also be accompanied by a satisfying “whoosh” sound as the player slides across the board to play during the animation. Euphoria on every move.™

## Dash
            The player has a dash movement that can be used once after completing a set number (*6?) of standard (1 space) movements.
A dash movement would move the player 3 spaces in any of the cardinal and diagonal directions (unless obstructed by a wall).
If the player “dashes” into a floor space occupied by an enemy, the enemy is “killed” and the dash is unobstructed.
After each use, the player would then have to begin to “charge” the dash again by performing a set number of standard movement actions.
*Excess charge can’t be stored (Ex. If it takes 10 standard movements to “charge” a dash, the player may only have between 0 and 10 charge. Every standard movement after full charge doesn’t affect the gauge, nor can be stored).



N, NE, and E dash movement options in blue. Both enemies (X) would be “killed”.
*Dash Proposal: 
            A dash movement into a wall would rebound the player in another direction.
If the player dashes into a wall head on (shown in yellow for cardinal and green for diagonal), they would simply be obstructed.
If the player dashes into the side of a wall at an angle (shown in purple), they would rebound off the wall at 90 degrees until they use all their dash spaces or are obstructed.




## Controls:
On the keyboard, it is often difficult to press two directional buttons at the exact same time. To make it easier to move in diagonal directions, when any directional key is pressed (*we need to discuss using WASD, the arrow pad, or enabling both), have the game wait .05 seconds before making a check on the currently pressed keys, and then moving the player based on that input, allowing a small buffer for multi-key inputs.
*To allow players to maximize the time allotted to them by the movement cooldown timer, I think we should let the players hold down the keys they want to use for the next move. As soon as the cooldown timer ends, we can make a check for any pressed directional keys. If the movement is valid (matches one of the eight directions and not holding up and down, three keys, etc.) then the player will move them as soon as the timer ends. This way as soon as players know where they want to move next, they can hold that direction and not have to tap the keys as soon as they think the timer has finished up.


## Enemies:
The game enemies are the primary opposition of the player. Each enemy piece is modeled after the corresponding chess piece and moves accordingly. The enemies all move towards the player with the goal of “capturing” the player by occupying the same space. Enemies will routinely attack the player until they are captured by the player piece and removed. 
There are two kinds of attacks enemies can perform, both of which are unique for each piece. 
A movement attack involves the enemy piece sliding across the board like it would traditionally in chess. This kind of attack would be telegraphed by all the spaces the piece will soon travel across or onto fading into a *new color for 1.25 seconds before moving across the spaces at a rate of (set amount of seconds per space moved) and stopping for a cooldown dependent on each enemy type.
 A melee attack targets of the general area surrounding a piece, performed when the player piece is within range of the attack. This kind of attack is telegraphed by all volatile spaces fading into a *new color for 1.5 seconds before the respective spaces are attacked, and remaining immobile for a cooldown dependent on each piece.

### Pawn
The Pawn is the most simple enemy piece. The only moves a Pawn can make are directly forward on any cardinal direction and, when encountered with a wall space, rotate 180° and continue moving forward in the other direction. It takes one movement turn to rotate around.
If the Pawn has an enemy directly in front of it, it cannot move forward until the enemy moves out of the way. If its path is blocked, the turn timer will be set to the beginning and have to count down again before the Pawn can move forward again.
The pawn can attack the three spaces in front of it perpendicular to its movement (An attack directly counts as a movement attack, diagonal a melee attack).
If the pawn, attacks at a diagonal, the pawn will continue moving forward one space to the right or left.
