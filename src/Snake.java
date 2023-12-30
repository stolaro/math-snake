import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Point> body= new ArrayList<>();
    private char direction;
    private boolean grow;
    public boolean gamerun=true;

    public Snake() {

        body.add(new Point(300, 300));
        direction = 'R';
        grow = false;
    }

    public List<Point> getBody() {
        return body;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char newDirection) {
        if ((newDirection == 'U' && direction != 'D') ||
                (newDirection == 'D' && direction != 'U') ||
                (newDirection == 'L' && direction != 'R') ||
                (newDirection == 'R' && direction != 'L')) {
            direction = newDirection;
        }
    }

    public void move() {
        if(gamerun) {
            Point head = body.get(0);
            Point newHead = new Point(head);

            switch (direction) {
                case 'U':
                    newHead.y -= 30;
                    break;
                case 'D':
                    newHead.y += 30;
                    break;
                case 'L':
                    newHead.x -= 30;
                    break;
                case 'R':
                    newHead.x += 30;
                    break;
            }

            body.add(0, newHead);

            if (!grow) {
                body.remove(body.size() - 1);
            } else {
                grow = false;
            }
        }
    }
    public void grow() {
        grow = true;
    }

    public boolean checkCollisionWithFruit(Point fruit) {

        Point head = body.get(0);
        return head.equals(fruit);
    }
    public boolean checkCollisionWithWrongFruits(List<Point> wrongFruits) {
        Point head = body.get(0);
        for (Point wrongFruit : wrongFruits) {
            if (head.equals(wrongFruit)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision() {
        Point head = body.get(0);

        // Check collision with walls
        if (head.x < 0 || head.x >= 1260 || head.y < 90 || head.y >= 670) {
            return true;
        }

        // Check collision with body
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

}
