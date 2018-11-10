# WeddingSeating Challenge


You work for a wedding planner and are in charge of assigning seating for guests. You are given a list of tables (defined by table name - max capacity). You are also given a list of guest parties, along with the number in that party. Also noted is if a party dislikes one or more other parties.
 If possible, you should not seat parties at the same table with a party they dislike. If it is not possible to seat all parties at the same table, the program should return an error.

## Assumptions
- All party members must sit on same table.
- Parties have unique names.

## Approach

- We loop through each guest and see if we can __seatGuest__.
- We call function __canSeat__, which determines if a guest can seat on given table. Checks if there is space or guest they dislike. We also verify
if the sitting guests dislike the incoming guest.
If they can seat, table information is updated accordingly.
- If guest can not seat at given table and at any other tables, we check if we __canMove__ already sitting guests to a different table. If we are able to __move__
some of the sitting guests and make space, both the source and destination table information are updated accordingly.
- If we are unable to seat a guest, we determine sitting arrangement is not possible.

## Complexity
At worst this algorithm is __O(n*m)__, where _n_ is size of table and _m_ is number of guests available (Since _m > n_ its realistically _O(m^2)_. We can improve the running time by using appropriate
data-structures to reduce the iterations of both tables and guests. 

We can use a Stack of possible destination tables and
possible(or better option) of guests to move. We can populate stack of possible tables, in each iteration, by size of spaceAvailable
that is close to the moving guests party size.

### Disclaimer

The input file __input.txt__ must follow a strinct format.

1. First line must start with __tables:__ and follow with table name and size seperated by __-__ .
Every table info is seperated by space. All tables info must be listed on the first line.

        eg: tables: A-8 B-5 C-9 H-99
2. Every following line contains information about a single guest. Each line must start with gusest __name__ followed by __,__ then space
 followed by __party of__ and a __number/size__ of party. If there are dislikes, _space_ write _dislikes_ another _space_ then list of _names_ sperated by __,__

        eg: Owens, party of 6 dislikes Thornton, Taylor

*__EXAMPLE of input.txt__*

     tables: A-8 B-8 C-7 D-7
     Thornton, party of 3
     Garcia, party of 2
     Owens, party of 6 dislikes Thornton, Taylor
     Smit, party of 1 dislikes Garcia
     Taylor, party of 5
     Reese, party of 7


Look at given 4 input files under __resources__ folder.
