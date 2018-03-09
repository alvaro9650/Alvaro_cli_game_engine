/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RunnerBalls;

import GameEngine.Camera;
import GameEngine.Field;
import GameEngine.GameEngine;
import GameEngine.ImpossibleLocationAddException;
import GameEngine.ImpossibleLocationRemoveException;
import GameEngine.Speed;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author alvaro9650
 */
public class RunerBalls {

    /**
     * @param args the command line arguments
     * @author alvaro9650
     */
    public static void main(String[] args) {
        System.out.println("You are a character\nYou have to touch a ball to win a point\nYou have 10 move points each turn , if you don't use them all you will have them at the next turn\nYou get 10 move points each turn\nBall speed and position is generated randomly every match\nBalls bounce when they reach a border\nYou have to input your move coordinates in an x(right and left) y(down and up) format\nThe first player to reach 5 points wins\nThe character | means nothing in that position , the character O means ball in that position");
        Field field = new Field(79, 20, 50);
        GameEngine gameengine = new GameEngine();
        Scanner input = new Scanner(System.in);
        System.out.println("Input the ball number");
        Integer ballnumber = input.nextInt();
        input.nextLine();
        for (int i = 0; i < ballnumber; new Ball(field), i++);
        System.out.println("Input the character you want to use");
        Player[] players = {new Player(input.nextLine().charAt(0), field), new Player(input.nextLine().charAt(0), field)};
        StringBuilder inputmvmsg = new StringBuilder("Player   input your move coordinates");
        Speed pmovement;
        Camera cam = new Camera(field, 0, 0, 79, 20);
        cam.blank = '|';
        game:
        while (true) {
            cam.updateFrame();
            cam.drawFrame();
            GameEngine.updateLocations(field);
            for (Player player : players) {
                inputmvmsg.replace(7, 8, String.valueOf(player.character));
                System.out.println(inputmvmsg.toString());
                try {
                    pmovement = new Speed(input.nextInt(), input.nextInt());
                } catch (InputMismatchException e) {
                    System.out.println("Thats not a coordinate , you loose your turn");
                    pmovement = new Speed(0, 0);
                }
                try {
                    player.move(pmovement);
                    input.nextLine();
                } catch (ImpossibleLocationRemoveException ex) {
                    System.out.println("You can't move away from here");
                } catch (ImpossibleLocationAddException ex) {
                    System.out.println("You can't move there");
                } catch (PlayerHasWonException ex) {
                    System.out.println(ex.getMessage());
                    break game;
                }
                cam.updateFrame();
                cam.drawFrame();
            }
            showScore(players);
        }
    }

    public static void showScore(Player[] players) {
        for (Player player : players) {
            System.out.append("Player ");
            System.out.append(player.character);
            System.out.append(" has ");
            System.out.append(player.points.toString());
            System.out.println(" points");
        }
    }

}
