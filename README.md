# ByteCubed


You work for a wedding planner and are in charge of assigning seating for guests. You are given a list of tables (defined by table name - max capacity). You are also given a list of guest parties, along with the number in that party. Also noted is if a party dislikes one or more other parties.
 If possible, you should not seat parties at the same table with a party they dislike. If it is not possible to seat all parties at the same table, the program should return an error.

## Assumption
All party members must sit on same table.

## Approach

### Disclaimer

The input file __input.txt__ must follow a strinct format.

1. First line must start with __tables:__ and follow with table name and size seperated by __-__ Every other table seperated by space. All the tables must be 
listed on the first line.
   
    eg: __tables: A-8 B-5 C-9 H-99__
2. Each following lines contains information about single guest. Each line must _name_ followed by _,_ the space _party of_ and a _number/size of party._ If there are dislikes after write _dislikes_ then list of names sperated by _,_

    eg: __Owens, party of 6 dislikes Thornton, Taylor__

*__EXAMPLE of input.txt__*

     tables: A-8 B-8 C-7 D-7
     Thornton, party of 3
     Garcia, party of 2
     Owens, party of 6 dislikes Thornton, Taylor
     Smit, party of 1 dislikes Garcia
     Taylor, party of 5
     Reese, party of 7


