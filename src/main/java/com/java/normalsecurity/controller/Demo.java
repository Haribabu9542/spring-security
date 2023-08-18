package com.java.normalsecurity.controller;

import java.io.InputStreamReader;

/**
 * Demo
 */
public class Demo {

     public static void main1(String[] args) {
        try(InputStreamReader isr= new InputStreamReader(System.in)) {
            System.out.println("Enter: ");
            int letters= isr.read();
            while (isr.read()){
                System.out.println(letters);
                letters= isr.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try (InputStreamReader isr = new InputStreamReader(System.in)) {
            System.out.println("Enter: ");
            int character =isr.read();
            while (isr.read()==1) {
                System.out.println(character);
                System.out.println((char)character);
                character=isr.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}