package main;

public class Main {

    public static OpenGL openGL;

    public static void main(String[] args) {
        if (openGL != null)
            openGL.markAsClosed();
        openGL = new OpenGL(args);
    }
}
