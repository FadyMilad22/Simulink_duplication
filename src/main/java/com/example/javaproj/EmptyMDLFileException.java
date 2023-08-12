package com.example.javaproj;

public class EmptyMDLFileException extends RuntimeException{
    EmptyMDLFileException(String str)
    {
        super(str);
    }
}