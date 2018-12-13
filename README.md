## Zi – An Intelligent Scrabble Player

Zi is a Scrabble player implemented inside a Scrabble game which combines a
selective move generator, simulations of likely game scenarios, and the search algorithm to produce a
fast and smart Scrabble-playing program.

In practice Zi algorithm uses a large dictionary and the heuristic of selecting the move with the highest score at each turn makes a very fast program that is rarely beaten by humans. The program makes no use of any strategic concepts, but its backtracking search usually is sufficient to overwhelm its opponent.

##### Representation of the Lexicon

A key element of the algorithm is the representation of the lexicon. For the Zi the lexicon is a tree whose edges are labeled by letters. Each word in the lexicon corresponds to a path from the root. When two words begin the same way they share the initial parts of their paths. The node at the end of a word’s path is called a terminal node; these are specially marked.  This data structure is called a letter-tree.
 
##### Backtracking

Zi uses a simple two-part strategy to do move generation. For each anchor, we generate all moves anchored there as follows heuristic:

1.	Find all possible “left parts” of words anchored at the given anchor. (A left part of a word consists of those tiles to the left of the anchor square.)

2.	For each “left part” found above, find all matching “right parts.” (A right part consists of those tiles
including and to the right of the anchor square.)

The left part will contain either tiles from our rack, or tiles already on the board, but not both.


