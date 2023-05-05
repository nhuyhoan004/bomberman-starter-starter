package uet.oop.bomberman.entities.enemy.ai;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.entities.EntityArr.bricks;
import static uet.oop.bomberman.entities.EntityArr.grasses;

public class AIMedium extends AI {
    Bomber _bomber;
    Enemy _e;
    List<PathFinding> aStarList = new ArrayList<>();

    public AIMedium(Bomber bomber, Enemy enemy) {
        _bomber = bomber;
        _e = enemy;
        aStarList.clear();
    }

    @Override
    public int calculateDirection() {
        if (_bomber == null) return random.nextInt(4);
        int v = returnDirection();
        return v;
    }

    public int returnDirection() {
        int _direction = -1;
        double minF = Double.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            int xx = _e.getX(), yy = _e.getY();
            if (i == 0) {
                yy--;
            }
            if (i == 1) {
                xx++;
            }
            if (i == 2) {
                yy++;
            }
            if (i == 3) {
                xx--;
            }

            Entity a;

            for (Entity grass : grasses) {
                double G = 1;
                double H = Math.pow(xx - _bomber.getX(), 2) + Math.pow(yy - _bomber.getY(), 2);
                aStarList.add(new PathFinding(G, H, G + H, i));
            }


        }
        return 0;
    }
}
