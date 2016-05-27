Call
====

Aliases made simple. A command line tool to organize commonly used commands/tasks. Written in Kotlin.

```bash
brew install https://raw.githubusercontent.com/kennycason/call/master/script/brew/call.rb
```

## Usage

### Add Command

Add a command to your local library.

Example: 

```bash
# add single commands
call add l "ls -l"
call add api-status "curl -I https://api.datarank.com 2>/dev/null | head -n 1 | cut -d$' ' -f2"
call add storm-up "ansible-playbook deploy-storm.yml -i inventory/production -vvvv --private-key=keys/id_deployer -u deployer"
call add cluster-size "curl -s whale01.ttagg.com:9200/topic-*/_search | jq .hits.total"

# import library from url
call add https://raw.githubusercontent.com/kennycason/call/master/src/main/resources/com/kennycason/call/library/.call.library.sample.json

# import library from file
call add ~/.call.library.backup.json
```

Add a command with parameter placeholders via the @{} syntax
```bash
# add command with parameter placeholder 
call add l "ls -l @{1}"

# run command
call l /tmp/
```

### Remove Command

Remove a command from your local library.

Example: 

```bash
call remove api-status
call remove maelstrom-up
```

### Run Command

Run one command with none or more arguments To run multiple commands simply append each command. e.g. 

`call command1 [arg1 args2 ...]`

Example with sample output: 

```bash
> call api-status
200
```

```
> call cluster-size
1340411047
```

Example of passing in a single parameter
```bash
> call add api-status "curl -I @{1} 2>/dev/null | head -n 1 | cut -d$' ' -f2"
> call api-status http://api.datarank.com
200
```

### Help

Prints a usage message. Help welcomed :)

```bash
call help
```

### List

Prints a list of all commands and their definitions

```bash
call list
```

### Install

#### Brew Install

```bash
brew install https://raw.githubusercontent.com/kennycason/call/master/script/brew/call.rb
```

#### Install (via Bash Script)

A helper script has been added to install Run. Java is required to run.
The install is a single a jar from Maven Central.

```bash
bash <(curl -s https://raw.githubusercontent.com/kennycason/call/master/script/install.sh)
```

#### Maven Install (To include in other code)

```xml
<dependency>
    <groupId>com.kennycason</groupId>
    <artifactId>call</artifactId>
    <version>1.0</version>
</dependency>
```

After installation the command name is `call`

## Coming Soon

- Sorting by frequency used
- Recording stats: usage count, responses, etc
- Better handling of library add/remove features. e.g. force-overwrite, etc


## Library File Format [~/.call.library.json]

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
Commands are stored in the local json file: `~/.call.library.json`

## Command Fun

### Infinite Loop via Run!

```bash
> call add call "call call"
command [call] added.
        call call
>call call
````
