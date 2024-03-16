# BattleSalvo!

[PA04 Write-Up](https://markefontenot.notion.site/PA-04-BattleSalvo-Part-2-20ff66267da84956b35794bf8452c2fd)

## Program Input, 2 Parts:

1. In order to play against the AI, the program needs to be run with no command line inputs.
2. In order to play againt a server, the program needs to be run with a port number.


## Ship Placement

After the user finishes selecting their fleet, the AI **automatically** generates placements for each ship: both on the user’s board and the AI’s own board. 
This happens in a randomized manner
Once the AI has generated all ship placements, the user should be presented:

1. a view of the AI’s board, showing only the information known to the user (nothing, yet!)
2. the user’s own board, showing all ships in their respective coordinates

<img width="684" alt="Captura de pantalla 2024-03-16 a la(s) 12 18 53 a m" src="https://github.com/haderie/BattleSalvo-Pt.1-and-Pt.2/assets/126923741/320aebd1-5039-49f4-b631-26c4baf37115">

<img width="410" alt="Captura de pantalla 2024-03-16 a la(s) 12 19 05 a m" src="https://github.com/haderie/BattleSalvo-Pt.1-and-Pt.2/assets/126923741/5de4383e-0ff0-4aa8-a52a-07cb18813e0b">

This includes several additional tools:
1. Gradle Build Automation
1. JaCoCo for Test Coverage
1. CheckStyle for Code Style Checks (Using the custom [cs3500 check file](./config/checkstyle/cs3500-checkstyle.xml)) 
