# 6/5
- removed viewer from the AI Player and User Player class
- removed BoardSpec as a parameter in the Player classes in order to decouple
  - this made it so the board isn't initialized before the setup methods
- updated successful shots to utilize the successful shots to modify the 
  - shots to take