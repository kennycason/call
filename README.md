Run
===

Aliases made simple. A command line tool to organize commonly used commands/tasks. Written in Kotlin.

## Usage

### Add Command

Add a command to your local library.

Example: 

```bash
# add single commands
run add l "ls -l"
run add api-status "curl -I https://api.datarank.com 2>/dev/null | head -n 1 | cut -d$' ' -f2"
run add storm-up "ansible-playbook deploy-storm.yml -i inventory/production -vvvv --private-key=keys/id_deployer -u deployer"
run add cluster-size "curl -s whale01.ttagg.com:9200/topic-*/_search | jq .hits.total"

# import library from url
run add https://raw.githubusercontent.com/kennycason/run/master/src/main/resources/com/kennycason/run/library/.run.library.sample.json

# import library from file
run add ~/.run.library.backup.json
```

Add a command with parameter placeholders via the @{} syntax
```bash
# add command with parameter placeholder 
run add l "ls -l @{1}"

# run command
run l /tmp/
```

### Remove Command

Remove a command from your local library.

Example: 

```bash
run remove api-status
run remove maelstrom-up
```

### Run Command

Run one command with none or more arguments To run multiple commands simply append each command. e.g. `run command1 [arg1 args2 ...]`

Example with sample output: 

```bash
> run api-status
200
```

```
> run cluster-size
1340411047
```

Example of passing in a single parameter
```bash
> run add api-status "curl -I @{1} 2>/dev/null | head -n 1 | cut -d$' ' -f2"
> run api-status http://api.datarank.com
200
```

### Help

Prints a usage message

```bash
run help
```

### List

Prints a list of all commands and their definitions

```bash
run list
```

### Install

#### Install from Bash Script.

A helper script has been added to install Run. Java is required to run Run.
The install downloads a jar from Maven Central.

```bash
bash script/install.sh
```

#### Brew Install

(Coming soon)

#### Maven Install (To include in other code)

```xml
<dependency>
    <groupId>com.kennycason</groupId>
    <artifactId>run</artifactId>
    <version>1.0</version>
</dependency>
```

After installation the command name is `run`

## Coming Soon

- Sorting by frequency used
- Recording stats: usage count, responses, etc
- Better handling of library add/remove features. e.g. force-overwrite, etc


## Library File Format [~/.run.library.json]

Currently there is only a bare-bone format defined.

```json
{
    "version": 1.0,
    "commands": [
        {
            "command": "l",
            "definition": "ls -l",
        }
    ]
}
```

## Notes

Currently Run depends on Bash to execute all commands.
Commands are stored in the local json file: `~/.run.library.json`

## Command Fun

### Infinite Loop via Run!

```bash
> run add run "run run"
command [run] added.
        run run
>run run
````
