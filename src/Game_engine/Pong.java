/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game_engine;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno1718_2
 */
public class Pong {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.append("You are a character\nYou have to touch a ball to win a point\nYou have 10 move points each turn , if you don't use them all you will have them at the next turn\nYou get 10 move points each turn\nBall speed and position is generated randomly every match\nBalls bounce when they reach a border\nYou have to input your move coordinates in an x(right and left) y(down and up) format\nThe first player to reach 5 points wins\nThe character | means nothing in that position , the character O means ball in that position\n");
        
        Field field = new Field(79, 20,50);
        Game_engine game_engine = new Game_engine(field);
        Player test=new Player('f', field);
        test.move(1, 1);
        test.log();
        System.out.println(field.game_objects[test.location.x][test.location.y][0]);
        try {
            test.close();
        } catch (IOException ex) {
            Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner input = new Scanner(System.in);
        Ball[] balls = {new Ball(field), new Ball(field), new Ball(field), new Ball(field), new Ball(field)};
        System.out.println("Input the character you want to use");
        Player[] players = {new Player(input.nextLine().charAt(0), field), new Player(input.nextLine().charAt(0), field)};
        game_engine.DrawFrame();
        char edfdf = input.nextLine().charAt(0);
        game:
        while (true) {
            game_engine.DeprecatedDrawFrame(field, balls, players);
            for (Ball ball : balls) {
                ball.log();
                ball.UpdateLocation();
            }
            for (Player player : players) {
                System.out.append("Player ");
                System.out.append(player.character);
                System.out.println(" input your move coordinates");
                try {
                    player.move(input.nextInt(), input.nextInt());
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Thats not a coordinate , you loose your turn");
                }
            }
            addPoints(players, balls);
            showScore(players);
            for (Player player : players) {
                if (player.points >= 5) {
                    System.out.append("Player ");
                    System.out.append(player.character);
                    System.out.println(" has won the match");
                    break game;
                }
            }
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

    public static void addPoints(Player[] players, Ball[] balls) {
        for (Player player : players) {
            for (Ball ball : balls) {
                if (Objects.equals(player.location.x, ball.location.x) && Objects.equals(player.location.y, ball.location.y)) {
                    player.points++;
                }
            }
        }
    }
}
