package com.example.javaproj;

public class Line {
    public String [][] properties = new String [10][2];
    public Line[] BranchesFromLine;
    int inputPort;
    int outputPort;
    String srcBlock;
    String dstBlock;
    boolean needsArrowHead;
    Double[] Coordinates;
    boolean hasSrc = false;
    boolean hasDst = false;
    Block src;
    Block dst;
    public void getCoordinates() {
        
        for(int i = 0; i < properties.length; i++)
        {
            
            if(properties[i][0] == null)
            break; 
            if(properties[i][0].equals("Src"))
            {
                srcBlock = ("" + properties[i][1].charAt(0));
                //System.out.println((""+properties[i][1].charAt(properties[i][1].length()-1)));
                outputPort = Integer.parseInt((""+properties[i][1].charAt(properties[i][1].length()-1)));
                hasSrc = true;
            }
            if(properties[i][0].equals("Dst"))
            {
                dstBlock = ("" + properties[i][1].charAt(0));
                inputPort = Integer.parseInt((""+properties[i][1].charAt(properties[i][1].length()-1)));
                hasDst = true;
            }
            if(properties[i][0].equals("Points"))
            {
                String S = properties[i][1].replace("[","");
                S = S.replace("]","");
                String[] arrOfStr = S.split(";");
                int noOfPoints = arrOfStr.length;
                Coordinates = new Double[noOfPoints*2];
                int j = 0;
                for(String s : arrOfStr)
                {
                    Coordinates[j++] = Double.parseDouble(s.split(",")[0]);
                    Coordinates[j++] = Double.parseDouble(s.split(",")[1]);
                }
            }
            if(hasDst)
            needsArrowHead = true; 

        }   
    }  

    public void print()
    {
        for (int i = 0; i < properties.length; i++) { //this equals to the row in our matrix.
            for (int j = 0; j < properties[i].length; j++) { //this equals to the column in each row.
                System.out.print(properties[i][j] + " ");
            }
            System.out.println(); //change line on console as row comes to end in the matrix.
        }
    }

    public double getEndPointX()
    {
        return src.getOutputPortsX()[outputPort-1] + (Coordinates[0]);
    }
    public double getEndPointY()
    {
        return src.getOutputPortsY()[outputPort-1] + (Coordinates[1]);
    }
}
