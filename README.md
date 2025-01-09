# SimpleDifficulty for Underdog

Fork of SimpleDifficulty.

- Fixed the wrong type for PurifiedWater in the API.
- Added salt water and regular water. Vanilla water is now treated as spring water. (The water itself must be generated using another mod. Cave Generator is recommended).
- Added compatibility for Biomes O' Plenty and Greenery aquatic plants.
- Added Fluidlogged API compatibility.
- Added biome water colors to waters.
- Added a new config option to make waters darker/brighter.
- Added compatibility to the Streams mod.
- Added salt/freshwater mixing logic with an optimized fill algorithm that doesn't affect performance.
- Improved water fog visuals. The fog colour is more bright now and changes depending on the biome.
- Hypothermia and Hyperthermia now reduce the max health and add negative potion effects instead of dealing damage.
  - Spice of Life's health modifier is ignored in the calculation.
- Removed particle effect from fluids & slightly improved performance for fluids.
- Added regular and salt water ice! 🧊 
  - (Plus a world generator for it, and implemented melting, freezing and Serene Seasons support.)
- Added Dynamic Trees compatibility for fluid blocks.
