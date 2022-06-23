# STSAutostart

Slay the Spire mod to skip the splash screen and initial menuing.

## Assumptions

- `desktop-1.0.jar` (the core of Slay the Spire) and `ModTheSpire.jar` are located in `../lib/` relative to this
  project.
- You are booting the game using ModTheSpire from this project's parent directory. This means there should be a `mods/`
  directory in the parent.

## Installation

```zsh
git clone git@github.com:spdskatr/StSAutostart.git
mvn package
```

## Execution
```zsh
# From the parent directory of this project
java -jar lib/ModTheSpire.jar
```
