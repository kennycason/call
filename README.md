Run
===

A command line tool to organize commonly used commands and tasked. Commands are stored in the local json file: `~/.run.library.json`

Currently Run depends on Bash to execute all commands.

## Usage

### Add Command

Add a command to your local library.

Example: 

```bash
run add l "ls -l"
run add api-status "curl -I https://api.datarank.com 2>/dev/null | head -n 1 | cut -d$' ' -f2"
run add storm-up "ansible-playbook deploy-storm.yml -i inventory/production -vvvv --private-key=keys/id_deployer -u deployer"
run add cluster-size "curl -s whale01.ttagg.com:9200/topic-*/_search | jq .hits.total"
run add https://raw.githubusercontent.com/kennycason/run/master/src/main/resources/com/kennycason/run/library/.run.library.sample.json
run add ~/bills_run_library.json
```

### Remove Command

Remove a command from your local library.

Example: 

```bash
run remove l
run remove api-status
run remove maelstrom-up
```

### Run Command(s)

Run one or more commands. To run multiple commands simply append each command. e.g. `run command1 command2`

Example with sample output: 

```bash
> run api-status
200
```

```
> run cluster-size
1340411047
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

## Coming Soon

- Import library from local file
- Import library from GitHub/Url
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