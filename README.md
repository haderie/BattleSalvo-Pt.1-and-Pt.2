# BattleSalvo!

The game board consists of two grids, typically marked by letters for columns and numbers for rows. The grids are often represented by 10x10 squares, but other variations exist. Each player's grid is divided into rows and columns, allowing players to strategically place their ships on their board. The types and sizes of ships can vary, but common examples include the carrier, battleship, destroyer, submarine, and patrol boat.

Players take turns calling out coordinates on the opponent's grid, attempting to hit their ships. The opponent responds with "hit" or "miss" based on the accuracy of the guess. If a hit is recorded, the attacking player marks the location on their own tracking grid to keep track of successful hits.

As the game progresses, players use deduction and logic to narrow down the possible locations of the opponent's ships based on the hits and misses they've observed. Once all the squares of a ship have been hit, it is considered sunk. The first player to sink all of the opponent's ships wins the game.

## Program Input, 2 Parts:

1. In order to play against the AI, the program needs to be run with no command line inputs.
2. In order to play your AI against another users, the program needs to be run with a port number.


### Fleet Size

In Battleship, there is one of each boat type. But, in BattleSalvo, there may be several of each boat type.

The total fleet size may not exceed the smaller board dimension, but must have at least one of each boat type. Therefore, for a board of size 8x11, there should be no more than 8 total boats. Fleet size and boat types will be identical between players. 

### Number of Shots

In traditional Battleship, a player launches a missile once per turn. In BattleSalvo, for each turn, each player launches one missile per non-sunk boat remaining in their fleet.  For example, if you currently have 3 remaining ships in your fleet, you would launch 3 missiles. At the same point in time, if your opponent had 5 ships remaining, they would be able to launch 5 missiles.

### Shooting Order

In BattleSalvo, both players select their shots (target locations), and the shots are exchanged simultaneously. Information about hits is then exchanged, surviving ships are updated, and the process is repeated until one (or both) players have no more surviving ships. Importantly, this means some games will end in ties!

## Ship Placement

After the user finishes selecting their fleet, the AI **automatically** generates placements for each ship: both on the user’s board and the AI’s own board. 
This happens in a randomized manner
Once the AI has generated all ship placements, the user should be presented:

1. a view of the AI’s board, showing only the information known to the user (nothing, yet!)
2. the user’s own board, showing all ships in their respective coordinates

<img width="684" alt="Captura de pantalla 2024-03-16 a la(s) 12 18 53 a m" src="https://github.com/haderie/BattleSalvo-Pt.1-and-Pt.2/assets/126923741/320aebd1-5039-49f4-b631-26c4baf37115">

<img width="410" alt="Captura de pantalla 2024-03-16 a la(s) 12 19 05 a m" src="https://github.com/haderie/BattleSalvo-Pt.1-and-Pt.2/assets/126923741/5de4383e-0ff0-4aa8-a52a-07cb18813e0b">

### Repeat

This “Salvo Stage” will continue until either:

1. all of the AI’s ships have been sunk, or
2. all of user’s ships have been sunk

### Game End

When the game ends, your program should display to the user whether they have won, lost, or tied. After displaying that, the program should exit — without requiring any additional input from the user.

This includes several additional tools:
1. Gradle Build Automation
1. JaCoCo for Test Coverage
1. CheckStyle for Code Style Checks (Using the custom [cs3500 check file](./config/checkstyle/cs3500-checkstyle.xml)) 
