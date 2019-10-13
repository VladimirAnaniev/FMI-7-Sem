export class Maze {
   private teleports: Array<[number, number]>;

   constructor(private readonly maze: Array<Array<0 | 1 | 2>>) {
      this.teleports = this.extractTeleports(maze);
   }

   getShortestPath(start: [number, number], end: [number, number]): Array<[number, number]> {
      const visited: Array<[number, number]> = [start];
      const queue: Array<Array<[number, number]>> = [[start]];

      while (queue.length) {
         const path = queue.shift();
         if (!path) {
            continue;
         }
         const position = path[path.length - 1];

         if (this.areEqual(position, end)) {
            return path;
         }

         this.getNextPositions(position).forEach(nextPosition => {
            if (this.isAvailable(nextPosition) && !this.isVisited(nextPosition, visited)) {
               visited.push(nextPosition);

               // console.log(nextPosition);

               if (this.isTeleport(nextPosition)) {
                  const destination = this.getTeleportDestination(nextPosition);
                  queue.push([...path, nextPosition, destination]);
               } else {
                  queue.push([...path, nextPosition]);
               }
            }
         });
      }

      return [];
   }

   private getNextPositions(position: [number, number]): Array<[number, number]> {
      return [
         [position[0] - 1, position[1]] as [number, number],
         [position[0], position[1] + 1] as [number, number],
         [position[0] + 1, position[1]] as [number, number],
         [position[0], position[1] - 1] as [number, number],
      ].filter(
         (newPosition: [number, number]) =>
            newPosition[0] >= 0 &&
            newPosition[0] < this.maze.length &&
            newPosition[1] >= 0 &&
            newPosition[1] <= this.maze[0].length
      );
   }

   private isAvailable(position: [number, number]): boolean {
      return this.maze[position[0]][position[1]] > 0;
   }

   private isTeleport(position: [number, number]): boolean {
      return this.teleports.some(teleport => this.areEqual(position, teleport));
   }

   private getTeleportDestination(position: [number, number]): [number, number] {
      const index = this.teleports.findIndex(teleport => this.areEqual(position, teleport));
      const destinationIndex = (index + 1) % 2;
      return this.teleports[destinationIndex];
   }

   extractTeleports(maze: Array<Array<0 | 1 | 2>>): Array<[number, number]> {
      return maze
         .map((row, rowIndex) =>
            row
               .map((col, colIndex) => {
                  return {position: [rowIndex, colIndex] as [number, number], value: col};
               })
               .filter(col => col.value === 2)
               .map(col => col.position)
         )
         .reduce((acc, curr) => acc.concat(curr), []);
   }

   private areEqual(position1: [number, number], position2: [number, number]): boolean {
      return position1[0] === position2[0] && position1[1] === position2[1];
   }

   private isVisited(position: [number, number], visited: Array<[number, number]>): boolean {
      return visited.some(pos => this.areEqual(pos, position));
   }
}
