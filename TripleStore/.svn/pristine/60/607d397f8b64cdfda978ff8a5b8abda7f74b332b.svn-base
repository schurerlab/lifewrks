package edu.miami.ccs.life;

import java.io.File;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Controller controller;
        if (args.length == 0)
            controller =
                    new Controller(new File("/home/sam/projects/LIFE/LIFE/src/main/bin/run.cfg"));
        else
            controller = new Controller(new File(args[0]));
        controller.init();
        controller.run();

    }
}
