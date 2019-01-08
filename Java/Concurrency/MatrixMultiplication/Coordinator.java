import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Coordinator{
    int[][] a;
    int[][] b;
    int[][] c; 
    
    int dimensions;
    int workers;
    int port;
    int count = 0;

    Connection connection;
    Socket clientSocket;
    DataInputStream input;
    DataInputStream[] fromWorkers;
    DataOutputStream[] toWorkers;
    int[] ports;
    String[] ips;

    int div;
    int workerDim;
    int[] xDims;
    int[] yDims;

    public static void main(String[] args) {
        if (args.length != 3){
            System.out.println("Program requires square matrix dimensions (1 int), number of workers, and port number.");
            System.exit(0);
        }
        Coordinator coordinator = new Coordinator(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        coordinator.matrixPrep();
        coordinator.connectToWorkers();
        coordinator.divvyMatrices();
        int[][] mesh = coordinator.calculateMesh();
    }
    
    public Coordinator(int dim, int nodes, int portNum){
        dimensions = dim;
        workers = nodes;
        port = portNum;

        a = new int[dimensions][dimensions];
        b = new int[dimensions][dimensions];
        c = new int[dimensions][dimensions];

        div = (int)Math.sqrt((double)workers);
        workerDim = dimensions/div;
        System.out.println("WorkerDim = " + workerDim);
        System.out.println("Dimensions = " + dimensions);
    }

    // create an n by n random matrix  
	public static int[][] createRandomMatrix(int n) {
		int[][] matrix = new int[n][n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				matrix[row][col] = (int)(Math.random()*1000);
			}
		}
		return matrix; 
    }

    public static int[][] createDisplayMatrix(int n) {
		int[][] matrix = new int[n][n];
		int up = (int)Math.pow(10, (int)Math.log10(n)+1); 
		for (int row = 1; row <= n; row++) {
			for (int col = 1; col <= n; col++) {
				matrix[row - 1][col - 1] = row * up + col;
			}
		}
		return matrix; 
	}
    
    // display n by n "display matrix"; n is limited to 660.
	public static void displayMatrix(int[][] mat) {
		int n = mat.length; 
		int m = mat[0].length; 
		if (n <= 660) {
			int digit = (int) Math.log10(n)*2+3;
			for (int row = 0; row < n; row++) {
				for (int col = 0; col < m; col++) {
					String numStr = String.format("[" + "%"+digit+"d" + "]", mat[row][col]);
					System.out.print(numStr);
				}
				System.out.println();
			}
		} else {
			System.out.println("The matrix is too big to display on screen.");
		}
    }
    
    public void matrixPrep(){
        a = createRandomMatrix(dimensions);
        b = createRandomMatrix(dimensions);
        System.out.println("Matrix A: ");
        displayMatrix(a);
        System.out.println("Matrix B: ");
        displayMatrix(b);
        matrixShift();
        System.out.println("Matrix A Shifted: ");
        displayMatrix(a);
        System.out.println("Matrix B Shifted: ");
        displayMatrix(b);
    }

    public void matrixShift(){
        //shift a left
        for(int row = 0; row < dimensions; row++){
            for(int shifts = 0; shifts <= row; shifts++){
                int temp = a[row][0];
                for (int column = 1; column < dimensions; column++){
                    a[row][column - 1] = a[row][column];
                }
                a[row][dimensions - 1] = temp;
            }
        }
        //shift b up
        for (int column = 0; column < dimensions; column++){
            for(int shifts = 0; shifts <= column; shifts++){
                int temp = b[0][column];
                for(int row = 1; row < dimensions; row++){
                    b[row - 1][column] = b[row][column];
                }
                b[dimensions - 1][column] = temp;
            }
        }
    }

    public void connectToWorkers(){
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(port);
            
        }catch(IOException ioe){
            System.out.println("Error establishing connection!");
        }
        for(int i = 0; i < workers; i++){
            try {
                System.out.println("Waiting for Client...");
                //accept connections from the given port
                clientSocket = serverSocket.accept();
                input = new DataInputStream(clientSocket.getInputStream());
                //Initialize arrays
                fromWorkers = new DataInputStream[workers];
                toWorkers = new DataOutputStream[workers];
                ips = new String[workers];
                ports = new int[workers];
                //grab worker listening port
                ports[i] = input.readInt();
                System.out.println("Received: " + ports[i]);
                //grab worker ip
                String ip = clientSocket.getInetAddress().toString().substring(1);
                ips[i] = ip;
                System.out.println("Received: " + ip);
                //grab worker inputstream
                fromWorkers[i] =  input;
                //grab worker outputstream
                toWorkers[i] = new DataOutputStream(clientSocket.getOutputStream());
                //connection test code
                toWorkers[i].writeUTF("Connected!");
                //assign ID
                toWorkers[i].writeInt(i);
            } catch (IOException ioe) {
                System.out.println("Error Connecting to Workers!");
                System.exit(-1);
            }
        }
        System.out.println("All Connections Established!");
    }

    public int[][] calculateMesh(){
        
        int[][] mesh = new int[workerDim][workerDim];
        int count = 0;

        for (int i = 0; i < workerDim; i++){
            for (int j = 0; j < workerDim; j++){
                mesh[i][j]  = count;
                count++;
            }
        }
        return mesh;
    }

    public void determineNeighbors(int[][] mesh){
        int[][] rows = new int[div][div]; 
        int[][] columns = new int[div][div];

        for (int i = 0; i < workerDim; i++){
            for (int j = 0; j < workerDim; j++){
                rows[i][j] = mesh[i][j];
                columns[i][j] = mesh[i][j];
            }
        }

        for(int i = 0; i < mesh.length; i++){
            for(int j = 0; j < mesh.length; j++){
                rows[i][j] = rows[i][j] / div;
                columns[i][j] = columns[i][j]%div;
            }
        }

        System.out.println("Network Mesh: ");
        displayMatrix(mesh);
        System.out.println("Row Neighbors: ");
        displayMatrix(rows);
        System.out.println("Column Neighbors: ");
        displayMatrix(columns);

        List<List<Integer>> neighborhoodRows = new ArrayList<>();
        List<List<Integer>> neighborhoodCols = new ArrayList<>();
        for(int i = 0; i < div; i++){
            neighborhoodRows.add(new ArrayList<Integer>(div));
            neighborhoodCols.add(new ArrayList<Integer>(div));
        }
        int count = 0;
        for(int i = 0; i < mesh.length; i++){
            for(int j = 0; j < mesh.length; j++){
                if(rows[i][j] == count){
                    neighborhoodRows.get(count).add(mesh[i][j]);
                }                
            }
            count++;
        }
        count = 0;
        for(int i = 0; i < mesh.length; i++){
            for(int j = 0; j < mesh.length; j++){
                if(columns[j][i] == count){
                    neighborhoodCols.get(count).add(mesh[j][i]);
                }                
            }
            count++;
        }

        for (List<Integer> listRows : neighborhoodRows){
            int k;
            for(int j = 0; j < listRows.size(); j++){
                System.out.println(toWorkers[0].toString());
                if(j == 0){
                    k = listRows.size() - 1;
                }else{
                    k = j - 1;
                }
                try{
                    toWorkers[listRows.get(j)].writeUTF("Hello!");
                    toWorkers[listRows.get(j)].writeInt(ports[listRows.get(k)]);
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
        for (List<Integer> listCols : neighborhoodCols){
            int k;
            for(int j = 0; j < listCols.size(); j++){
                if(j == 0){
                    k = listCols.size() - 1;
                }else{
                    k = j - 1;
                }
                try{
                    toWorkers[listCols.get(j)].writeUTF(ips[listCols.get(k)]);
                    toWorkers[listCols.get(j)].writeInt(ports[listCols.get(k)]);
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void divvyMatrices() {
        xDims = new int[workers];
        yDims = new int[workers];
        //set remainder
        int rem = dimensions;
        for(int i = 0; i < workers; i++){
            //halfway through, reset rem for bottom set of matrices
            if(i == workers/2){
                rem = dimensions;
            }
            //calculate xDims
            rem -= workerDim;
            if(rem >= workerDim){
                xDims[i] = workerDim;
            }else if(rem < workerDim){
                xDims[i] = workerDim + rem;
            }
        }
        //reset remainder
        rem = dimensions;
        for(int i = 0; i < workers; i++){
            //halfway through, reset rem for bottom set of matrices
            if(i == workers/2){
                rem = dimensions;
            }
            //calculate yDims
            rem -= workerDim;
            if(rem >= workerDim){
                yDims[i] = workerDim;
            }else if(rem < workerDim){
                yDims[i] = workerDim + rem;
            }
        }
        for (int i = 0; i < workers; i++){
            System.out.println("Worker " + i + " Matrix: " + xDims[i] + "x" + yDims[i]);
        }

        // int[][][] aBlocks = new int[workers][workerDim][workerDim];

        // for(int k = 0; k < workers; k++){
        //     for (int i = 0; i < xDims[k]; i++){
        //         for (int j = 0; j < yDims[k]; j++){
        //             blocks[k][i][j] = a[i][j];
        //         }
        //     }
        // }

        // int[][][] bBlocks = new int[workers][workerDim][workerDim];
    }
}