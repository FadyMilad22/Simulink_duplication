package com.example.javaproj;

public class Block {

    public String type;
    public String name;
    public String SID;

    public int noOfInputPorts;
    public int noOfOutputPorts;

    private double x;
    private double y;
    private double width;
    private double height;

    private double outputPortsX[];
    private double outputPortsY[];
    private double inputPortsX[];
    private double inputPortsY[];

    public boolean mirrorred;
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public static double getMinX(Block blocks[])
    {
        double x = Double.MAX_VALUE;
        for(Block b : blocks)
        {
            if(b.getX() < x)
            {
                x = b.getX();
            }
        }
        return x;
    } 
    public double[] getOutputPortsY() {
        return outputPortsY;
    }

    public double[] getInputPortsX() {
        return inputPortsX;
    }

    public double[] getInputPortsY() {
        return inputPortsY;
    }

    public double[] getOutputPortsX() {
        return outputPortsX;
    }

    String [][] properties;
    public void print()
    {
        System.out.println(type);
        System.out.println(name);
        System.out.println(SID);

        for (int i = 0; i < properties.length; i++) { //this equals to the row in our matrix.
            for (int j = 0; j < properties[i].length; j++) { //this equals to the column in each row.
                System.out.print(properties[i][j] + " ");
            }
            System.out.println(); //change line on console as row comes to end in the matrix.
        }
    }

    @Override
    public String toString(){
        String data = "Name :" +name+"\n";
    data += "Type :" +type+"\n";
    data += "SID :" +SID+"\n";

        for (int i = 0; i < properties.length; i++) {
          if(i!= 0) {data += "\n";}
            for (int j = 0; j < properties[i].length; j++) {
                if(j==1){data += " : "; }
                data += (properties[i][j]);
            }
            ; //change line on console as row comes to end in the matrix.
    }
    return data;
}

    public void getCoordinates_Ports()
    {
        boolean portsset = false;
        for(int i = 0; i < properties.length; i++)
        {
            if(properties[i][0].equals("Position"))
            {
                String S = properties[i][1].replace("[","");
                S = S.replace("]","");

                String[] arrOfStr = S.split(",",4);
                x = Double.parseDouble(arrOfStr[0]);
                y = Double.parseDouble(arrOfStr[1]);
                double x2 = Double.parseDouble(arrOfStr[2]);
                double y2 = Double.parseDouble(arrOfStr[3]);
                width = (x2-x);
                height = (y2-y);
            }
            if(properties[i][0].equals("BlockMirror") && properties[i][1].equals("on"))
            { 
                mirrorred = true;
            }
                

            if(properties[i][0].equals("Ports"))
            {
                String S = properties[i][1].replace("[","");
                S = S.replace("]","");

                String[] arrOfStr = S.split(",");
                
                noOfInputPorts = Integer.parseInt(arrOfStr[0]);
                if(arrOfStr.length > 1)
                {
                noOfOutputPorts = Integer.parseInt(arrOfStr[1].trim());
                System.out.println("no prblem");
                }
                else 
                noOfOutputPorts = 0;
                portsset = true;
            }
            if(!portsset)
            {
                noOfInputPorts = 1;
                noOfOutputPorts = 1;
            }
        }
                inputPortsX  = new double[noOfInputPorts];
                inputPortsY  = new double[noOfInputPorts];
                outputPortsX  = new double[noOfOutputPorts];
                outputPortsY  = new double[noOfOutputPorts];
        if(mirrorred)
        {
            for(int i = 0; i<noOfInputPorts; i++)
            {
                inputPortsX [i] = this.getX()+this.getWidth();
                inputPortsY [i] = this.getY()+((i+1)*(this.getHeight()/(this.noOfOutputPorts+1)));
            }  
            for(int i = 0; i<noOfOutputPorts; i++)
            {
                outputPortsX [i] = this.getX();
                outputPortsY [i] = this.getY()+((i+1)*(this.getHeight()/(this.noOfOutputPorts+1)));
                                  //      b.getY()+(j+1)*b.getHeight()/b.properties[i][1].length()+1 
            } 
        }
        else
        {
            for(int i = 0; i<noOfInputPorts; i++)
            {
                inputPortsX [i] = this.getX();
                inputPortsY [i] = this.getY()+((i+1)*(this.getHeight()/(this.noOfInputPorts+1)));
            }  
            for(int i = 0; i<noOfOutputPorts; i++)
            {
                outputPortsX [i] = this.getX()+this.getWidth();
                outputPortsY [i] = this.getY()+((i+1)*(this.getHeight()/(this.noOfOutputPorts+1)));
            }  
        }
        
    }    
}