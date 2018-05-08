# Europa Universalis 4 save reader
This project read [Europa Universalis 4's](http://www.europauniversalis4.com/) saves files to extract all kind of stats from it.

## Extracted stats : 
  - Date
  - For registered countries : 
    - Capital
    - Culture
    - Religion
    - Government type
    - Government rank
    - List of continennts the coutry has provinces in
    - Institutions embraced
    - Ideas
    - Policies
    - Development
    - Realm development
    - Number of provinces
    - Treasury
    - Income
    - Inflation
    - Debt
    - Mercantilism
    - Actual manpower
    - Maximum manpower
    - Actual sailors
    - Maximum sailors
    - Land force limit
    - Active regular regiments
    - Active mercenaries
    - Active forces ((Active regular regiments + Active mercenaries) / Land force limit)
    - Professionalism
    - Army tradition
    - Navy tradition
    - Losses
    - Stability
    - Legitimacy
    - Average autonomy
    - Average unrest
    - War exhaustion
    - Religious unity
    - Prestige
    - Power projection
    - Splendor
    - Score
    - Absolutism
    - Tech
    - Innovativeness
    - Golden age started
    - Is part HRE
    - Trade bonus
    - Rivals
    - Allies
    - Dependencies
    - Provinces
    - States
    - Advisors
    - Ancients country name
    - Number of stolen buildings
    
## 
The list can be accessed with a `Eu4Save.toString()`.

To add a player, add him to Utils/Players.java, this file contains the list players for each sessions with username and tag.

To add a Eu4 save file, add the path to the `sessionsFilesPath` in Main.java.

##
The project export part of the stats as a Javascript object in a separed file, the path can be changed (`exportFilePath`) in Main.java.

The exported Javascript object is means to be used with my other project [Europa Universalis 4 Web Viewer](https://github.com/GaetanRAYNAUD/Eu4SaveWebViewer) witch is a small website to display all theses stats.

Here's an example : [Demo](https://gaetanraynaud.github.io/Eu4SaveWebViewer/).

The export is in French you can change this by replacing in General/Game.java in `playerToScript()` all :
- **`Tags.tagsFR.get`** by **`Tags.tags.get`**
- **`Cultures.culturesFR.get`** by **`Cultures.cultures.get`**
- **`Religions.religionsFR.get`** by **`Religions.religions.get`**
- **`Governments.governementTypesFR.get`** by **`Governments.governementTypes.get`**
- **`ProvincesIdFR.provincesId.get`** by **`ProvincesId.provincesId.get`**
