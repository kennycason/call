Run
===

A command line tool to organize commonly used commands and tasked. Commands are stored in the local json file: `~/.run.library.json`

Currently Run depends on Bash to execute all commands.

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

### Library

Prints a list of all commands and their definitions

```bash
run library
```

### Install

Until a brew formula is added, a helper script has been added to install Run locally. Java and Maven are required.
The install is simply a symlink to the generated jar for now.

```bash
git clone git@github.com:kennycason/run.git
cd run/
bash install.sh
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

## Command Fun

### Infinite Loop via Run!

```bash
> run add run "run run"
command [run] added.
        run run
>run run
````