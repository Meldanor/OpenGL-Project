package main;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class OpenGL {

    private boolean closeProgramm = false;

    private long time = 0L;

    public OpenGL(String[] args) {
        try {
            boolean fullScreen = false;
            if (fullScreen) {
                Display.setFullscreen(fullScreen);
            } else {
                Display.setDisplayMode(new DisplayMode(200, 200));
            }

            Display.setTitle("Test");
            Display.create();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.initOpenGL();

        this.run();

        this.close();
    }

    private void close() {
        Display.destroy();
    }

    public static long delta = 0L;

    private void run() {
        time = System.currentTimeMillis();
        long diff = 0L;
        boolean isTick = false;

        lastFrame = getTime();
        while (!closeProgramm) {
            delta = getDelta();
            this.closeProgramm = Display.isCloseRequested();

            diff = System.currentTimeMillis() - time;

            if (diff > 50L) {
                isTick = true;
                time = System.currentTimeMillis();
            }

            this.fetchKeyboard();

            if (isTick)
                this.holdKey();

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, 1680, 1080, 0, 1000, -1000);
            glMatrixMode(GL_MODELVIEW);

            // clear display
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glPushMatrix();
            glPopMatrix();

            isTick = false;

            Display.update();
            Display.sync(Display.isVisible() ? 60 : 15);
        }

    }

    private static long getTime() {
        return (Sys.getTime() * 1000L) / Sys.getTimerResolution();
    }

    private static long lastFrame = 0L;

    public static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = currentTime;
        return delta;
    }

    private static boolean[] KEY_STATES = new boolean[Short.MAX_VALUE];
    private static HashSet<Integer> PRESSED_KEYS = new HashSet<Integer>(32);

    private void fetchKeyboard() {
        int code = 0;
//        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
//            isRunning = false;
//        }

        while (Keyboard.next()) {
            code = Keyboard.getEventKey();
            if (Keyboard.isKeyDown(code)) {
                KEY_STATES[code] = true;
                PRESSED_KEYS.add(code);
            } else {
                KEY_STATES[code] = false;
                PRESSED_KEYS.remove(code);
            }

        }
    }

    private void holdKey() {
        for (int code : PRESSED_KEYS) {
            if (Keyboard.isKeyDown(code)) {
                // TODO: Knopf ist weiterhin gedrückt
            }
        }

    }

    private void initOpenGL() {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void markAsClosed() {

        this.closeProgramm = true;

    }
}
