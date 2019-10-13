import {Maze} from './bfs';

test('should find the shortest path', () => {
   const maze = new Maze([[1, 1, 1], [0, 1, 1], [1, 1, 1]]);

   expect(maze.getShortestPath([0, 0], [2, 0])).toEqual([[0, 0], [0, 1], [1, 1], [2, 1], [2, 0]]);
});

test('should go through portals', () => {
   const maze = new Maze([
      [1, 1, 0, 1, 1, 1],
      [1, 2, 0, 0, 1, 1],
      [1, 1, 1, 1, 2, 1],
      [1, 1, 1, 1, 1, 1],
      [1, 0, 0, 1, 1, 1],
      [1, 1, 1, 1, 1, 1]
   ]);

   expect(maze.getShortestPath([0, 0], [4, 4])).toEqual([[0, 0], [0, 1], [1, 1], [2, 4], [3, 4], [4, 4]]);
});

test('should not find path in impossible maze', () => {
   const maze = new Maze([[1, 1, 1], [1, 1, 0], [1, 0, 1]]);

   expect(maze.getShortestPath([0, 0], [2, 2])).toEqual([]);
});
