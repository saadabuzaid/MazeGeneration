package graphs;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Maze {
    int height, width;
    Graph graph;
    int component[];
    int randstart, randend;



    double random(int max,int min) { return((int)(Math.random() * (max-min + 1)+min)); }

    void maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.graph = new MatrixGraph(width*height, Graph.UNDIRECTED_GRAPH);


      for (int i = 0; i < graph.numVertices ;i++) {

            if(!graph.isEdge(i,i+1)&& graph.isValidVertex(i+1))
                graph.addEdge(i,i+1,((random(10,1))/10));

            if(!graph.isEdge(i,i+width)&& graph.isValidVertex(i+width))
                graph.addEdge(i,i+width,((random(10,1))/10));
       }
    }

    void print(){
        int count=0;
        int randstart= (int)random(height,0)*width;
        int randend= (int)(random(height,0)*width)+width-1;
        this.randstart=randstart;
        this.randend=randend;
//        System.out.println(randstart);
//        System.out.println(randend);
        for (int i = 0; i <graph.numVertices ; i++) {
            if(graph.isValidVertex(i)){
                if (i==randstart || i==randend)
                    System.out.print("*");
                else
                    System.out.print("+");
                count++;

                if(count%width==0 && count<graph.numVertices) {
                    System.out.println();
                    int tenth=count/10;
                    for (int j = tenth; j <tenth+width ; j++) {
                        if (graph.isEdge(j,j+width)&& graph.isValidVertex(j+width))
                            System.out.print("|  ");
                    }
                    System.out.println();

                }
                else if (graph.isEdge(i,i+1)&& graph.isValidVertex(i+1))
                    System.out.print("--");

            }
        }

    }


    void initialiseComponents(){
        component=new int[graph.numVertices];
        for (int i = 0; i <graph.numVertices ; i++) {
             component[i]=i;
        }
    }

    void mergeComponents(int x, int y){ component[x]=component[y];}

    void spanningTree(){
        Graph T = new MatrixGraph(graph.numVertices,Graph.UNDIRECTED_GRAPH);
        initialiseComponents();

        double leastWeight=1;
        for (int i = 0; i < T.numVertices; i++) {
            for (int neigbour:graph.neighbours(i)) {
                if(graph.weight(i,neigbour)<leastWeight){
                    leastWeight = graph.weight(i,neigbour);
                }
                mergeComponents(neigbour,i);
            }
        }
        for (int i = 0; i <T.numVertices ; i++) {
            if(T.isValidEdge(component[i],i))
                T.addEdge(component[i],i);
        }
        //Print
        int count=0;
        for (int i = 0; i <T.numVertices ; i++) {
            if(T.isValidVertex(i)){
                if (i==randstart || i==randend)
                    System.out.print("*");
                else
                    System.out.print("+");
                count++;

                if(count%width==0 && count<T.numVertices) {
                    System.out.println();
                    int tenth=count/10;
                    for (int j = tenth; j <tenth+width ; j++) {
                        if (T.isEdge(j,j+width)&& T.isValidVertex(j+width))
                            System.out.print("|  ");
                    }
                    System.out.println();

                }
                else if (T.isEdge(i,i+1)&& T.isValidVertex(i+1))
                    System.out.print("--");

            }
        }





    }


    public static void main(String[] args) {
        Maze maze = new Maze();
        maze.maze(4,4);
        maze.print();
        System.out.println("\n\n");
        maze.spanningTree();
//        for (int i = 0; i <maze.graph.numVertices ; i++) {
//            for (int j = 0; j <maze.graph.numVertices ; j++) {
//                System.out.print(maze.graph.isEdge(j,j+1));
//            }
//        }
//        System.out.println(Arrays.toString(maze.graph.neighbours(78)));
//        System.out.println(maze.graph.weight(72,73));

    }
}