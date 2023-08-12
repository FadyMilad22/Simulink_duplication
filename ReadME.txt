Simulink Duplicator

	by Team 23
	Tasneem Salama Abd-Elhamid Abd-Elhady	 2000036 
	Fady Milad Garas 		     	 1900003
	Sarah Sherif Mohamed Abdelhady       	 2000427
	Mariam Mohamed Abdelkader Mohamed        2000973
	Alaa Mohsen Sayed El-Sayed 		 2000195
	
The Simulink Duplicator is a Java application designed to duplicate and visualize Simulink models stored in MDL files. It provides a graphical representation of the Simulink model and allows users to browse and select MDL files for duplication.


Consists of : 
 
1-MDL File Parser
	This Java project provides a utility for parsing MDL (Model Description Language) files. MDL files are XML-based files used in simulation and modeling 	systems.

	Usage
	To use the MDL file parser, follow the steps below:

		1.1-Call the readFile method to read an MDL file and obtain a Document object representing the XML content of the file:

		1.2-Call the assembleBlocks method to extract information about blocks from the MDL file:

		1.3-Call the assembleLines method to extract information about lines from the MDL file:	

		1.4Exceptions
		The following exceptions are defined to handle specific scenarios:

		NotVaildMDLFileException: Thrown when the provided file is not a valid MDL file.
		EmptyMDLFileException: Thrown when the provided MDL file is empty.

2-Block Class
	The Block class represents a block in the MDL file.

Properties
	type (String): The type of the block.
	name (String): The name of the block.
	SID (String): The SID (System Identification) of the block.
	noOfInputPorts (int): The number of input ports of the block.
	noOfOutputPorts (int): The number of output ports of the block.
	x (double): The x-coordinate of the block's position.
	y (double): The y-coordinate of the block's position.
	width (double): The width of the block.
	height (double): The height of the block.
	outputPortsX (double[]): The x-coordinates of the output ports.
	outputPortsY (double[]): The y-coordinates of the output ports.
	inputPortsX (double[]): The x-coordinates of the input ports.
	inputPortsY (double[]): The y-coordinates of the input ports.
	mirrorred (boolean): Indicates if the block is mirrored.
Methods
	getX(): double: Returns the x-coordinate of the block's position.
	getY(): double: Returns the y-coordinate of the block's position.
	getWidth(): double: Returns the width of the block.	
	getHeight(): double: Returns the height of the block.
	getOutputPortsY(): double[]: Returns the y-coordinates of the output ports.
	getInputPortsX(): double[]: Returns the x-coordinates of the input ports.
	getInputPortsY(): double[]: Returns the y-coordinates of the input ports.
	getOutputPortsX(): double[]: Returns the x-coordinates of the output ports.
	print(): void: Prints the block's properties.
	toString(): String: Returns a string representation of the block.
	getCoordinates_Ports(): void: Retrieves the coordinates and ports information for the block.


3-Line Class
	The Line class represents a line in the MDL file.

Properties
	properties (String[][]): The properties of the line.
	BranchesFromLine (Line[]): The branches originating from the line.
	inputPort (int): The input port of the line.
	outputPort (int): The output port of the line.
	srcBlock (String): The source block of the line.
	dstBlock (String): The destination block of the line.
	needsArrowHead (boolean): Indicates if the line needs an arrowhead.
	Coordinates (Double[]): The coordinates of the line.
	hasSrc (boolean): Indicates if the line has a source block.
	hasDst (boolean): Indicates if the line has a destination block.
	src (Block): The source block object.
	dst (Block): The destination block object.
Methods
	getCoordinates(): void: Retrieves the coordinates for the line.
	print(): void: Prints the line's properties.
	getEndPointX(): double: Returns the x-coordinate of the line's end point.
	getEndPointY(): double: Returns the y-coordinate of the line's end point.


4-Branch Class
	The Branch class extends the Line class and represents a branch in the MDL file. It inherits all the properties and methods from the Line class.


5- Gui.java that consist of the following Main Components : 


	5.1-Gui Class
	The Gui class is the main entry point of the application. It extends the Application class from JavaFX and provides the start method for 	initializing the user interface.
	The start method calls the browseCreate method to create the initial browsing scene.

	5.2-browseCreate Method
	The browseCreate method creates the initial scene where users can browse and select an MDL file. It displays a welcome message, a directory label, 	and a "Browse" button.

	When the "Browse" button is clicked, a file chooser dialog is opened for the user to select an MDL file. Once a file is selected, the directory 	label is updated with the file path.
	The method also creates an "Ok" button that allows users to proceed to the main program once a valid MDL file is selected.



	5.3-mainProgramCreate Method
	The mainProgramCreate method is called when a valid MDL file is selected and the user clicks the "Ok" button. It processes the MDL file, assembles 	the blocks and lines, and generates the visual representation of the Simulink model.

	The method creates a BlockPane and a ConnectorPane to display the blocks and lines respectively. It also creates a heading label and "Exit" and "Re-	browse" buttons for navigation.

6-The visual representation of the Simulink model is organized using various layout panes and alignment properties.

	6.1-BlockPane Class
	The BlockPane class extends Pane and is responsible for displaying the blocks of the Simulink model. It creates a rectangle for each block and adds 	it to the pane. Additionally, it adds text labels for the block names and any input ports.

	Each block is represented by a rectangle with a white fill color and a black stroke color. The block's name is displayed below the rectangle, and 	input ports are indicated by a plus sign.

	6.2-ConnectorPane Class
	The ConnectorPane class extends Pane and handles the display of lines connecting the blocks in the Simulink model. It takes an array of lines and 	blocks as input and draws the connections between them.

	The class uses JavaFX's Line and Polyline shapes to draw the connections. It also creates arrowheads at the end of each line to indicate the flow 	direction.

	The ConnectorPane class handles both straight connections and curved connections based on the input lines.
	
	
